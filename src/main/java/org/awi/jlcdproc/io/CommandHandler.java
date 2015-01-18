package org.awi.jlcdproc.io;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.AttributeKey;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.events.CommandResultEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler extends ChannelDuplexHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final AttributeKey<Command> CURRENT_COMMAND = AttributeKey.valueOf("current-command");

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

		Command command = (Command) msg;

		Command pendingCommand = ctx.channel().attr(CURRENT_COMMAND).setIfAbsent(command);
		if (pendingCommand == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("Sending " + command.toString());
			}
			
			super.write(ctx, command.getCommand() + "\n", promise);
		} else {
			
			String errorMessage = String.format("Pending command %s while trying to execute %s", pendingCommand, command);
			logger.warn(errorMessage);
			throw new IllegalStateException(errorMessage);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		CommandResultEvent event = (CommandResultEvent) msg;

		Command command = ctx.channel().attr(CURRENT_COMMAND).get();

		if (logger.isDebugEnabled()) {
			
			logger.debug(String.format("%s received for %s", event, command));
		}

		if (command != null) {

			if (command.onCommandResultEvent(event)) {

				ctx.channel().attr(CURRENT_COMMAND).set(null);
			}
		}
	}
}
