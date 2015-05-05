package org.awi.jlcdproc.io;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.util.AttributeKey;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.events.CommandResultEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.impl.LcdProcInternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler extends ChannelDuplexHandler {

	private final LcdProcInternal lcdProc;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ConcurrentLinkedQueue<CommandHolder> pendingCommandQueue = new ConcurrentLinkedQueue<>();

	public static final AttributeKey<CommandHolder> CURRENT_COMMAND = AttributeKey.valueOf("current-command");

	public CommandHandler(LcdProcInternal lcdProc) {

		this.lcdProc = lcdProc;
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

		CommandHolder commandHolder = (CommandHolder) msg;
		CommandHolder pendingCommandHolder = ctx.channel().attr(CURRENT_COMMAND).setIfAbsent(commandHolder);

		Command command = commandHolder.getCommand();
		if (logger.isDebugEnabled()) {

			logger.debug(String.format("Process %scommand %s",
					pendingCommandHolder == commandHolder ? "pending " : "",
					command.toString()));
		}

		if (pendingCommandHolder == null || pendingCommandHolder == commandHolder) {

			super.write(ctx, command.getCommand() + "\n", promise);

		} else {

			pendingCommandQueue.add(commandHolder);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		Event event = (Event) msg;

		if (event instanceof CommandResultEvent) {

			CommandHolder commandHolder = ctx.channel().attr(CURRENT_COMMAND).get();
			if (commandHolder != null) {

				Command command = commandHolder.getCommand();
				if (command.onCommandResultEvent(commandHolder, (CommandResultEvent) event)) {

					CommandHolder pendingCommandHolder = pendingCommandQueue.poll();
					ctx.channel().attr(CURRENT_COMMAND).set(pendingCommandHolder);

					//
					if (pendingCommandHolder != null) {

						logger.debug("Send pending command " + pendingCommandHolder.toString());
						ctx.channel().writeAndFlush(pendingCommandHolder);
					}
				}
			}
		} else {

			logger.debug("Fire event " + event.toString());
			lcdProc.fireEvent(event);
		}
	}

}
