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

/**
 * This channel handler is responsible to convert {@link Command Commands} to
 * strings that are sent to the LCDProc server. It synchronizes concurrent
 * attempts to send commands by placing them into a queue.
 */
public class CommandHandler extends ChannelDuplexHandler {

	private final LcdProcInternal lcdProc;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ConcurrentLinkedQueue<CommandHolder> pendingCommandQueue = new ConcurrentLinkedQueue<>();

	public static final AttributeKey<CommandHolder> CURRENT_COMMAND = AttributeKey.valueOf("current-command");

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 */
	public CommandHandler(LcdProcInternal lcdProc) {

		this.lcdProc = lcdProc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.netty.channel.ChannelDuplexHandler#write(io.netty.channel.
	 * ChannelHandlerContext, java.lang.Object, io.netty.channel.ChannelPromise)
	 */
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

		CommandHolder commandHolder = (CommandHolder) msg;

		// Try to set the current command attribute and retrieve the possibly
		// pending command.
		CommandHolder pendingCommandHolder = ctx.channel().attr(CURRENT_COMMAND).setIfAbsent(commandHolder);

		Command command = commandHolder.getCommand();
		if (logger.isDebugEnabled()) {

			logger.debug(String.format("Process %scommand %s",
					pendingCommandHolder == commandHolder ? "pending " : "",
					command.toString()));
		}

		// Send the command to the LCDProc server, if there is no pending
		// command or the pendig
		// command is the current command. The latter case occurs, if a command
		// was previously taken
		// in the pending queue.
		if (pendingCommandHolder == null || pendingCommandHolder == commandHolder) {

			super.write(ctx, command.getCommand() + "\n", promise);

		} else {

			// Place the command in the pending queue.
			if (logger.isDebugEnabled()) {

				logger.debug("Placing command in pending queue.");
			}
			pendingCommandQueue.add(commandHolder);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel
	 * .ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		Event event = (Event) msg;

		// CommandResultEvents are treated differently from other events as
		// these events are sent to the corresponding command while all other
		// events are sent to the lcdProc instance
		if (event instanceof CommandResultEvent) {

			// Get the current command. It should not be null
			CommandHolder commandHolder = ctx.channel().attr(CURRENT_COMMAND).get();
			if (commandHolder == null) {

				logger.warn("Unexpected CommandResultEvent: " + event.toString());
				return;
			}

			Command command = commandHolder.getCommand();
			if (logger.isDebugEnabled()) {

				logger.debug(String.format("%s received for %s", event, command));
			}

			// As one command might receive more than one result event, we
			// always update the event in the command holder.
			commandHolder.updateEvent(event);

			// Now check, if the command is completed
			if (command.isCommandCompleted((CommandResultEvent) event)) {

				// If so, interrupt the currently blocked thread ...
				commandHolder.getBlockedThread().interrupt();

				// .. get the next command from the pending queue (might be
				// null, if no command is pending) and update the current
				// command attribute
				CommandHolder pendingCommandHolder = pendingCommandQueue.poll();
				ctx.channel().attr(CURRENT_COMMAND).set(pendingCommandHolder);

				// Send pending command, if it exists
				if (pendingCommandHolder != null) {

					logger.debug("Send pending command " + pendingCommandHolder.toString());
					ctx.channel().writeAndFlush(pendingCommandHolder);
				}
			}
		} else {

			logger.debug("Fire event " + event.toString());
			lcdProc.fireEvent(event);
		}
	}

}
