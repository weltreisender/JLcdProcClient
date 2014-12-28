package org.awi.jlcdproc;

import java.io.IOException;

import org.awi.jlcdproc.io.Connection;
import org.awi.jlcdproc.widgets.Direction;
import org.awi.jlcdproc.widgets.Heartbeat;
import org.awi.jlcdproc.widgets.Icon;
import org.awi.jlcdproc.widgets.ScrollerWidget;

public class LcdProc implements AutoCloseable {

	private Connection connection;

	private int currentScreenId = 0;

	public void init() throws Exception {

		init("localhost", 13666);
	}

	public void init(String host, int port) throws Exception {

		connection = new Connection(host, port);
		connection.connect();
	}

	public Screen createScreen() throws Exception {

		return new Screen(connection, currentScreenId++);
	}

	public Screen createScreen(String screenId) throws Exception {

		return new Screen(connection, screenId);
	}

	public static void main(String[] args) throws Exception {

		try (LcdProc lcdProc = new LcdProc()) {

			lcdProc.init();

			Screen screen = lcdProc.createScreen("myScreen");

			screen.setHeartbeat(Heartbeat.OFF);
			
			ScrollerWidget w = screen.scrollerWidget("myWidget");
			w.set(1, 1, 16, 2, Direction.MARQUEE, 3, "Dies ist ein sehr langer Text, bei dem Scrollen notwendig ist ");
		
			System.in.read();
		}
	}

	public void close() throws IOException {

		connection.close();
	}
}
