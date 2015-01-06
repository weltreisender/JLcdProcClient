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
import org.awi.jlcdproc.events.DriverInfoEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.FunctionEvent;

public class Connection implements Closeable {

	private String host = "localhost";

	private int port = 13666;

	private Channel channel;

	private ConcurrentLinkedQueue<EventListener> eventListeners = new ConcurrentLinkedQueue<>();

	private Bootstrap bootstrap;

	private EventLoopGroup group;

	private ExecutorService executorService = Executors.newCachedThreadPool();

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

		ConnectEvent connectEvent = (ConnectEvent) execute(command, ConnectEvent.class);
		if (connectEvent == null) {

			throw new LcdProcConnectException();
		}
		System.out.println(connectEvent);
	}

	@Override
	public void close() throws IOException {
	
		if (channel != null) {
	
			channel.close();
			channel = null;
		}
		executorService.shutdown();
		group.shutdownGracefully();
	
		System.out.println("shutdown");
	}

	public void addEventListener(EventListener eventListener) {

		eventListeners.add(eventListener);
	}

	public void removeEventListener(EventListener eventListener) {

		eventListeners.remove(eventListener);
	}

	public void fireEvent(Event event) {

		for (EventListener eventListener : eventListeners) {
			
			executorService.submit(() -> {

				eventListener.onEvent(event);
			});
		}

	}

	public void send(Object... args) throws Exception {

		StringBuilder sb = new StringBuilder();

		for (Object arg : args) {

			if (arg instanceof Object[]) {

				Object[] objects = (Object[]) arg;
				for (Object object : objects) {

					if (object != null) {

						sb.append(object).append(" ");
					}
				}
			} else {

				if (arg != null) {

					sb.append(arg).append(" ");
				}
			}
		}

		sb.append("\n");

		FunctionEvent event = (FunctionEvent) execute(sb.toString(), FunctionEvent.class);

		if (event == null) {

			throw new CommandExecutionException("Command not executed in time");
		}

		if (!event.isSuccess()) {

			throw new CommandExecutionException(event.toString());
		}
	}

	public String info() {
		
		DriverInfoEvent event = (DriverInfoEvent) execute("info\n", DriverInfoEvent.class);
		
		return event.getDriverInfo();
	}
	
	private Event execute(String command, Class<? extends Event> expectedEvent) {
	
		CommandCompletedListener listener = new CommandCompletedListener(expectedEvent);
		addEventListener(listener);
	
		channel.writeAndFlush(command);
	
		Future<Event> future = executorService.submit(listener);
	
		try {
	
			return future.get(500, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
	
			return null;
		} finally {
	
			removeEventListener(listener);
		}
	}
}
