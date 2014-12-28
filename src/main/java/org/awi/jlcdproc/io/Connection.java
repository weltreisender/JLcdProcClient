package org.awi.jlcdproc.io;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.awi.jlcdproc.events.ConnectEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.FunctionEvent;

public class Connection implements Closeable {

	private String host = "localhost";

	private int port = 13666;

	private Channel channel;

	private ConcurrentLinkedQueue<EventListener<? extends Event>> eventListeners = new ConcurrentLinkedQueue<>();

	private Bootstrap bootstrap;

	private EventLoopGroup group;

	public Connection() {

	}

	public Connection(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public void connect() throws Exception {

		bootstrap = new Bootstrap();

		final Connection connection = this;

		group = new NioEventLoopGroup(2);

		channel = bootstrap.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.AUTO_READ, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
								.addLast(new DelimiterBasedFrameDecoder(256, Delimiters.lineDelimiter()))
								.addLast(new StringDecoder())
								.addLast(new StringEncoder())
								.addLast(new LcdProcHandler(connection));
					};
				})
				.connect(new InetSocketAddress(host, port))
				.sync()
				.channel();

		String command = "hello\n";

		ConnectEvent connectEvent = execute(command, ConnectEvent.class);
		if (connectEvent == null) {
			
			throw new LcdProcConnectException();
		}
		System.out.println(connectEvent);
	}

	private <T extends Event> T execute(String command, Class<? extends T> expectedEvent) {

		CommandCompletedListener<T> listener = new CommandCompletedListener<>(expectedEvent);
		addEventListener(listener);

		channel.writeAndFlush(command);

		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<T> future = executorService.submit(listener);

		try {

			return future.get(500, TimeUnit.MILLISECONDS);
		} catch (Exception e) {

			return null;
		} finally {

			removeEventListener(listener);
			executorService.shutdown();
		}
	}

	public void addEventListener(EventListener<? extends Event> eventListener) {

		eventListeners.add(eventListener);
	}

	public void removeEventListener(EventListener<? extends Event> eventListener) {

		eventListeners.remove(eventListener);
	}

	public void fireEvent(Event event) {

		for (EventListener<? extends Event> eventListener : eventListeners) {

			eventListener.onCheckedEvent(event);
		}
	}

	public void send(Object ... args) throws Exception {

		StringBuilder sb = new StringBuilder();
		
		for (Object arg : args) {
			
			if (arg instanceof Object[]) {

				Object[] objects = (Object[])arg;
				for (Object object : objects) {
					
					sb.append(object).append(" ");
				}
			} else {
				
				sb.append(arg).append(" ");
			}
		}
		
		System.out.println(sb.toString());
		
		sb.append("\n");

		FunctionEvent event = execute(sb.toString(), FunctionEvent.class);

		if (event == null) {
			
			throw new CommandExecutionException("Command not executed in time");
		}

		if (!event.isSuccess()) {
			
			throw new CommandExecutionException(event.toString());
		}
		
		System.out.println(event);
	}

	@Override
	public void close() throws IOException {

		if (channel != null) {

			channel.close();
			channel = null;
		}
		System.out.println("shutdown");
		group.shutdownGracefully();
	}
}