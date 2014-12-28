package org.awi.jlcdproc.io;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import org.awi.jlcdproc.events.ConnectEvent;
import org.awi.jlcdproc.events.ErrorEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.IgnoreEvent;
import org.awi.jlcdproc.events.ListenEvent;
import org.awi.jlcdproc.events.SuccessEvent;

public class LcdProcHandler extends ChannelDuplexHandler implements
		ChannelHandler {

	private Connection connection;
	
	public LcdProcHandler(Connection connection) {
		
		this.connection = connection;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	
		System.out.println("channel Active");

		super.channelActive(ctx);
	}
	
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
		}  else if ((parameters = parseEventString(eventString, "ignore")) != null) {
			
			event = new IgnoreEvent(parameters);
		} else {
			
			throw new RuntimeException("Unknown event: " + eventString);
		}

		connection.fireEvent(event);
		
		super.channelRead(ctx, msg);
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
