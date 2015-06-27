package org.awi.jlcdproc.io;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

import org.awi.jlcdproc.events.ConnectEvent;
import org.awi.jlcdproc.events.DriverInfoEvent;
import org.awi.jlcdproc.events.ErrorEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.IgnoreEvent;
import org.awi.jlcdproc.events.KeyEvent;
import org.awi.jlcdproc.events.ListenEvent;
import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.events.SuccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This handler analyzes the events emitted by the LCDProc server and converts them
 * to {@link Event} objects.
 */
public class LcdProcEventHandler extends ChannelDuplexHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		String eventString = (String) msg;

		String parameters = null;
		Event event = null;
		if ((parameters = parseEventString(eventString, "connect")) != null) {

			event = new ConnectEvent(parameters);
		} else if ((parameters = parseEventString(eventString, "success")) != null) {

			event = new SuccessEvent();
		} else if ((parameters = parseEventString(eventString, "huh?")) != null) {

			event = new ErrorEvent(parameters);
		} else if ((parameters = parseEventString(eventString, "listen")) != null) {

			event = new ListenEvent(parameters);
		} else if ((parameters = parseEventString(eventString, "ignore")) != null) {

			event = new IgnoreEvent(parameters);
		} else if ((parameters = parseEventString(eventString, "menuevent")) != null) {

			event = new MenuEvent(parameters);
		} else if ((parameters = parseEventString(eventString, "key")) != null) {

			event = new KeyEvent(parameters);
		} else if (eventString.endsWith("driver")) {

			event = new DriverInfoEvent(eventString);
		} else {

			logger.error("Unknown event: " + eventString);
		}

		if (event != null) {

			super.channelRead(ctx, event);
		}
	}

	private String parseEventString(String eventString, String command) {

		if (eventString.startsWith(command)) {

			if (eventString.length() > command.length()) {

				return eventString.substring(command.length() + 1);
			}

			return new String();
		}

		return null;
	}
}
