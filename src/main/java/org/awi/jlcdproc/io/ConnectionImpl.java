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

import org.awi.jlcdproc.commands.Hello;
import org.awi.jlcdproc.impl.LcdProcInternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the {@link Connection} interface.
 * 
 * It uses Netty to establish the connection.
 */
public class ConnectionImpl implements Connection {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final String host;

	private final int port;

	private final LcdProcInternal lcdProc;

	private Channel channel;

	private EventLoopGroup group;

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param host
	 * @param port
	 */
	public ConnectionImpl(LcdProcInternal lcdProc, String host, int port) {

		super();
		this.host = host;
		this.port = port;
		this.lcdProc = lcdProc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.awi.jlcdproc.io.Connection#connect()
	 */
	@Override
	public void connect() throws Exception {

		group = new NioEventLoopGroup(2, new DefaultThreadFactory("netty"));

		try {

			// Setup netty client channel
			Bootstrap bootstrap = new Bootstrap();
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
									.addLast(new LcdProcEventHandler())
									.addLast(new CommandHandler(lcdProc));
						};
					})
					.connect(new InetSocketAddress(host, port))
					.sync()
					.channel();
		} catch (Exception e) {

			close();
			throw new LcdProcConnectException(host, port);
		}

		// Send LCDProc's "hello" command
		try {

			new Hello(lcdProc).send();
		} catch (Exception e) {

			close();
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws IOException {

		if (channel != null) {

			channel.close();
			channel = null;
		}
		group.shutdownGracefully();

		logger.debug("shutdown");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.awi.jlcdproc.io.Connection#send(org.awi.jlcdproc.commands.Command)
	 */
	@Override
	public void send(CommandHolder commandHolder) throws Exception {

		channel.writeAndFlush(commandHolder).addListener(commandHolder);
	}
}
