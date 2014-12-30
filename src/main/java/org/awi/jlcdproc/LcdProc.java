package org.awi.jlcdproc;

import java.io.IOException;
import java.util.ArrayList;

import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.SuccessEvent;
import org.awi.jlcdproc.io.Connection;
import org.awi.jlcdproc.menu.Action;
import org.awi.jlcdproc.menu.MainMenu;
import org.awi.jlcdproc.menu.Menu;
import org.awi.jlcdproc.widgets.Direction;
import org.awi.jlcdproc.widgets.Heartbeat;
import org.awi.jlcdproc.widgets.ScrollerWidget;

public class LcdProc implements AutoCloseable {

	private Connection connection;

	private int currentScreenId = 0;

	public LcdProc() throws Exception {

		this("localhost", 13666);
	}

	public LcdProc(String host, int port) throws Exception {

		connection = new Connection(host, port);
		connection.connect();
	}

	public void clientName(String name) throws Exception {

		connection.send("client_set", "-name", name);
	}

	public Screen screen() throws Exception {

		return new Screen(connection, currentScreenId++);
	}

	public Screen screen(String screenId) throws Exception {

		return new Screen(connection, screenId);
	}

	public MainMenu mainMenu(String name) {

		return new MainMenu(connection, name);
	}

	public void addEventListener(EventListener eventListener) {

		connection.addEventListener(eventListener);
	}

	public void removeEventListener(EventListener eventListener) {

		connection.removeEventListener(eventListener);
	}

	public static void main(String[] args) throws Exception {

		try (LcdProc lcdProc = new LcdProc()) {

			Screen s = lcdProc.screen();
			s.setHeartbeat(Heartbeat.OFF);
			
			MainMenu mainMenu = lcdProc.mainMenu("Main menu");
			
			lcdProc.addEventListener((Event event) -> { if (!(event instanceof SuccessEvent)) {System.out.println(event); } });

			Menu menu1 = mainMenu.addMenu("m1", "Menu 1");
			Action a11 = menu1.addAction("a11", "Action 1.1");
			Action a12 = menu1.addAction("a12", "Action 1.2");
			Action a13 = menu1.addAction("a13", "Action 1.3");

			a11.next(a12);
			a12.prev(a11).next(a13);
			a13.prev(a12);
			
			Menu menu2 = mainMenu.addMenu("m2", "Menu 2");
			menu2.addAction("a21", "Action 2.1");
			menu2.addAction("a22", "Action 2.2");
			menu2.addAction("a23", "Action 2.3");

			
//		
//			for (int b = 0; b < 32; b++) {
//
//				StringBuilder sb = new StringBuilder();
//				sb.append(b).append(": <");
//				switch(b) {
//				
//				case 0:
//					sb.append("nul");
//					break;
//				case 10:
//					sb.append("lf");
//					break;
//				case 13:
//					sb.append("cr");
//					break;
//				case 34:
//					sb.append("\\\"");
//					break;
//				case 92:
//					sb.append("\\\\");
//					break;
//				default:	
//					sb.append(new String(new byte[] {(byte)(b & (byte)0xFF)}));
//					break;
//				}
//				
//				sb.append("> ");
//				
//				mainMenu.addAction(String.format("a%d", b), sb.toString());
//				
//			}
			
			// ScrollerWidget widget = s.scrollerWidget();
			// widget.set(1, 1, 16, 1, Direction.MARQUEE, 1, sb.toString());
			mainMenu.activate();
			
			System.in.read();
		}
	}

	public void close() throws IOException {

		connection.close();
	}
}
