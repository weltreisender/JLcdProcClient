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
import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.commands.Hello;
import org.awi.jlcdproc.impl.LcdProcInternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionImpl implements AutoCloseable, Connection {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private String host = "localhost";

	private int port = 13666;

	private LcdProcInternal lcdProc;

	private Channel channel;

	private EventLoopGroup group;

	private LcdProcHandler lcdProcHandler;

	private CommandHandler commandHandler;

	public ConnectionImpl(LcdProcInternal lcdProc, String host, int port) {

		super();
		this.host = host;
		this.port = port;
		this.lcdProc = lcdProc;
	}

//	public LcdProc getLcdProc() {
//		return lcdProc;
//	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.io.Connection#connect()
	 */
	@Override
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
						
						@Override
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

			new Hello(lcdProc).send();
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

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.io.Connection#send(org.awi.jlcdproc.commands.Command)
	 */
	@Override
	public void send(Command command) throws Exception {

		channel.writeAndFlush(command).addListener(command);
		
//		Thread currentThread = Thread.currentThread();
//
//		command.setBlockedThread(currentThread);
//
//		channel.writeAndFlush(command).addListener(f -> {
//
//			if (!f.isSuccess()) {
//				currentThread.interrupt();
//			}
//		});
//
//		try {
//
//			currentThread.join(500);
//			logger.error("Timeout for " + command.toString());
//			throw new CommandExecutionTimeoutException(command);
//		} catch (InterruptedException e) {
//		}
//
//		if (!command.isSuccess()) {
//			throw new CommandExecutionException(command);
//		}
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
