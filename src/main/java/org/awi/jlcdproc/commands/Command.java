package org.awi.jlcdproc.commands;

import java.util.concurrent.atomic.AtomicReference;

import org.awi.jlcdproc.events.CommandResultEvent;
import org.awi.jlcdproc.io.Connection;

public abstract class Command {

	protected final Connection connection;
	
	private AtomicReference<CommandResultEvent> event = new AtomicReference<>();

	private Thread blockedThread = null;

	private String commandString;

	public Command(Connection connection) {
		
		this.connection = connection;
	}

	public String getCommand() {
		
		return commandString;
	};

	public boolean processCommandResultEvent(CommandResultEvent event) {

		return true;
	}

	public void send(Object ... args) throws Exception {
		StringBuilder commandBuilder = new StringBuilder();

		for (Object arg : args) {

			if (arg instanceof Object[]) {

				Object[] objects = (Object[]) arg;
				for (Object object : objects) {

					if (object != null) {

						commandBuilder.append(object).append(" ");
					}
				}
			} else {

				if (arg != null) {

					commandBuilder.append(arg).append(" ");
				}
			}
		}

		commandString = commandBuilder.toString().trim();

		connection.send(this);
	}
	
	public void setBlockedThread(Thread blockedThread) {

		this.blockedThread = blockedThread;
	}

	public boolean onCommandResultEvent(CommandResultEvent event) {

		this.event.compareAndSet(null, event);

		boolean commandTerminated = processCommandResultEvent(event);
		if (commandTerminated) {

			blockedThread.interrupt();
		}

		return commandTerminated;
	}

	public boolean isComplete() {

		return event.get() != null;
	}

	public boolean isSuccess() {

		return event.get().isSuccess();
	}

	public CommandResultEvent getEvent() {

		return event.get();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "=[" + getCommand() + "]";
	}
}
