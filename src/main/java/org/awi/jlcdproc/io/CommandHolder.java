package org.awi.jlcdproc.io;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.events.Event;

public class CommandHolder {

	private final Command command;
	
	private final Thread blockedThread;
	
	private Event event;

	public CommandHolder(Command command, Thread blockedThread) {
		super();
		this.blockedThread = blockedThread;
		this.command= command;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Thread getBlockedThread() {
		return blockedThread;
	}

	public Command getCommand() {
		return command;
	}
}
