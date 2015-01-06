package org.awi.jlcdproc;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.events.MenuEvent.Type;
import org.awi.jlcdproc.io.Connection;
import org.awi.jlcdproc.menu.Action;
import org.awi.jlcdproc.menu.Alpha;
import org.awi.jlcdproc.menu.Alpha.Allowed;
import org.awi.jlcdproc.menu.Checkbox;
import org.awi.jlcdproc.menu.MainMenu;
import org.awi.jlcdproc.menu.MenuResult;
import org.awi.jlcdproc.menu.Numeric;
import org.awi.jlcdproc.menu.Ring;
import org.awi.jlcdproc.menu.Slider;
import org.awi.jlcdproc.widgets.Backlight;
import org.awi.jlcdproc.widgets.Heartbeat;
import org.awi.jlcdproc.widgets.Screen;

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

	public void close() throws IOException {
	
		connection.close();
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

	public String info() {
		
		return connection.info();
	}
	
	public void backlight(Backlight backlight) throws Exception {
		
		connection.send("backlight", backlight);
	}
	
	public void removeEventListener(EventListener eventListener) {

		connection.removeEventListener(eventListener);
	}

	public static void main(String[] args) throws Exception {

		try (LcdProc lcdProc = new LcdProc("mediapc", 13666)) {

			Screen s = lcdProc.screen();
			s.setHeartbeat(Heartbeat.OFF);

			// MainMenu mainMenu = lcdProc.mainMenu("Main menu");
			//
			// lcdProc.addEventListener((Event event) -> {
			// if (!(event instanceof SuccessEvent)) {
			// System.out.println(event);
			// }
			// });
			//
			// Menu menu1 = mainMenu.addMenu("m1", "Menu 1");
			AtomicBoolean exit = new AtomicBoolean(false);

			MainMenu mainMenu = lcdProc.mainMenu("Main menu");
			
			
			Action a11 = mainMenu.addAction("exit").menuResult(MenuResult.QUIT);
			a11.addEventListener((MenuEvent event) -> {

				exit.set(true);
			});

			Checkbox c12 = mainMenu.addCheckbox("Checkbox", Checkbox.Value.GRAY).allowGray(true);
			c12.addEventListener((MenuEvent event) -> {
				System.out.println(((Checkbox) event.getMenuItem()).getSelectedValue());
			});

			Action a13 = mainMenu.addAction("del CB");
			a13.addEventListener((MenuEvent e) -> {
				
				try {
					c12.delete();
				} catch (Exception e1) {
				
					e1.printStackTrace();
				}
			});
			
			Ring r13 = mainMenu.addRing("Ring", 1, "11111", "22222", "33333");
			r13.addEventListener((MenuEvent event) -> {
				System.out.println(((Ring) event.getMenuItem()).getSelectedValue());
			});

			Slider s14 = mainMenu.addSlider("Slider")
					.minValue(-50)
					.maxValue(50)
					.minText("min")
					.maxText("max")
					.stepSize(5)
					.value(0);
			s14.addEventListener((MenuEvent event) -> {
				System.out.println(((Slider) event.getMenuItem()).getValue());
			});
			
			Numeric n15 = mainMenu.addNumeric("Numeric").minValue(-100).maxValue(99999999).value(10000);
			n15.addEventListener((MenuEvent event) -> {
				System.out.println(((Numeric) event.getMenuItem()).getValue());
			});

			Alpha a16 = mainMenu.addAlpha("Alpha").allow(Allowed.NONE).allow("abc#+");
			a16.addEventListener((MenuEvent event) -> {
				
				if (event.getType() == Type.ENTER) {
					
					try {
						a16.value("").update();
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
				System.out.println(((Alpha) event.getMenuItem()).getValue());
			});
			
			mainMenu.activate();
			mainMenu.show();

			System.out.println(lcdProc.info());
			
			// a11.next(c12);
			// c12.prev(a11).next(r13);
			// r13.prev(c12);
			//
			//
			// for (int b = 0; b < 32; b++) {
			//
			// StringBuilder sb = new StringBuilder();
			// sb.append(b).append(": <");
			// switch(b) {
			//
			// case 0:
			// sb.append("nul");
			// break;
			// case 10:
			// sb.append("lf");
			// break;
			// case 13:
			// sb.append("cr");
			// break;
			// case 34:
			// sb.append("\\\"");
			// break;
			// case 92:
			// sb.append("\\\\");
			// break;
			// default:
			// sb.append(new String(new byte[] {(byte)(b & (byte)0xFF)}));
			// break;
			// }
			//
			// sb.append("> ");
			//
			// mainMenu.addAction(String.format("a%d", b), sb.toString());
			//
			// }

			// ScrollerWidget widget = s.scrollerWidget();
			// widget.set(1, 1, 16, 1, Direction.MARQUEE, 1, sb.toString());

			while (!exit.get()) {

				Thread.sleep(50);
			}
		}
	}
}
