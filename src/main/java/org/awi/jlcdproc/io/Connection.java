package org.awi.jlcdproc.io;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
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
import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;

import org.awi.jlcdproc.LcdProc;
import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.commands.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connection implements AutoCloseable {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String host = "localhost";

	private int port = 13666;

	private LcdProc lcdProc;

	private Channel channel;

	private EventLoopGroup group;

	private LcdProcHandler lcdProcHandler;

	private CommandHandler commandHandler;

	public Connection(LcdProc lcdProc, String host, int port) {

		super();
		this.host = host;
		this.port = port;
		this.lcdProc = lcdProc;
	}

	public LcdProc getLcdProc() {
		return lcdProc;
	}

	public void connect() throws Exception {

		Bootstrap bootstrap = new Bootstrap();

		commandHandler = new CommandHandler();
		lcdProcHandler = new LcdProcHandler(lcdProc);

		ThreadFactory factory = new DefaultThreadFactory("netty");
		group = new NioEventLoopGroup(2, factory);

		try {

			channel = bootstrap.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.AUTO_READ, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						protected void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline()
									.addLast(new DelimiterBasedFrameDecoder(256, Delimiters.lineDelimiter()))
									.addLast(new StringDecoder())
									.addLast(new StringEncoder())
									.addLast(lcdProcHandler)
									.addLast(commandHandler);
						};
					})
					.connect(new InetSocketAddress(host, port))
					.sync()
					.channel();
		} catch (Exception e) {
			close();
			throw new LcdProcConnectException(host, port);
		}

		try {

			send(new Hello(this));
		} catch (Exception e) {

			close();
			throw e;
		}
	}

	@Override
	public void close() throws IOException {

		if (channel != null) {

			channel.close();
			channel = null;
		}
		group.shutdownGracefully();

		logger.debug("shutdown");
	}

	public void send(Command command) throws Exception {

		AtomicReference<Boolean> commandSuccessfullySent = new AtomicReference<>();
		Thread currentThread = Thread.currentThread();

		command.setBlockedThread(currentThread);

		channel.writeAndFlush(command).addListener((ChannelFuture f) -> {

			commandSuccessfullySent.set(f.isSuccess());
			if (!f.isSuccess()) {
				currentThread.interrupt();
			}
		});

		try {

			currentThread.join(500);
			logger.error("Timeout for " + command.toString());
			throw new CommandExecutionTimeoutException(command);
		} catch (InterruptedException e) {
		}

		if (!command.isSuccess()) {
			throw new CommandExecutionException(command);
		}
	}

	// private Event execute(String command, Class<? extends Event>
	// expectedEvent) {
	//
	// CommandCompletedListener listener = new
	// CommandCompletedListener(expectedEvent);
	// addEventListener(listener);
	// Future<Event> future = executorService.submit(listener);
	//
	// channel.writeAndFlush(command);
	//
	// try {
	//
	// return future.get(500, TimeUnit.MILLISECONDS);
	// } catch (Exception e) {
	//
	// logger.error(e.getMessage());
	// return null;
	// } finally {
	//
	// removeEventListener(listener);
	// }
	// }
	//
	// public void release() {
	//
	// channel.attr(CURRENT_COMMAND).set(null);
	// }
}
