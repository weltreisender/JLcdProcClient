package org.awi.jlcdproc;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.awi.jlcdproc.commands.Backlight;
import org.awi.jlcdproc.commands.BacklightCommand;
import org.awi.jlcdproc.commands.Info;
import org.awi.jlcdproc.commands.keys.Key;
import org.awi.jlcdproc.commands.keys.KeyMode;
import org.awi.jlcdproc.commands.keys.KeyName;
import org.awi.jlcdproc.commands.menu.MainMenu;
import org.awi.jlcdproc.commands.widget.Screen;
import org.awi.jlcdproc.events.DriverInfoEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.io.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LcdProc implements AutoCloseable {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Connection connection;

	private ConcurrentLinkedQueue<EventListener> eventListeners = new ConcurrentLinkedQueue<>();

	private final ExecutorService executorService;

	private int currentScreenId = 0;

	public LcdProc() throws Exception {

		this("localhost", 13666);
	}

	public LcdProc(String host, int port) throws Exception {

		connection = new Connection(this, host, port);
		connection.connect();

		ThreadFactory executorServiceFactory = new DefaultThreadFactory("cmd");
		executorService = Executors.newCachedThreadPool(executorServiceFactory);
	}

	public void close() throws IOException {

		connection.close();
		executorService.shutdown();
	}

	// public void clientName(String name) throws Exception {
	//
	// connection.send(null, "client_set", "-name", name);
	// }

	public Screen screen() throws Exception {

		return new Screen(connection, currentScreenId++);
	}

	public Screen screen(String screenId) throws Exception {

		return new Screen(connection, screenId);
	}

	public MainMenu mainMenu(String name) {

		return new MainMenu(connection, name);
	}

	public Key addKey(KeyName key) throws Exception {

		return new Key(connection, key);
	}

	public Key addKey(KeyName key, KeyMode keyMode) throws Exception {

		return new Key(connection, key, keyMode);
	}

	public String info() throws Exception {

		Info info = new Info(connection);

		info.send(info);

		return ((DriverInfoEvent) info.getEvent()).getDriverInfo();

	}

	public void fireEvent(Event event) {

		if (executorService.isShutdown() || executorService.isTerminated()) {

			return;
		}

		executorService.submit(() -> {
			for (EventListener eventListener : eventListeners) {

				eventListener.onEvent(event);
			}
		});
	}

	public void backlight(Backlight backlight) throws Exception {

		BacklightCommand backlightCmd = new BacklightCommand(connection, backlight);
		backlightCmd.send();
	}

	public void addEventListener(EventListener eventListener) {

		eventListeners.add(eventListener);
	}

	public void removeEventListener(EventListener eventListener) {

		eventListeners.remove(eventListener);
	}

	public static void main(String[] args) throws Exception {

		try (LcdProc lcdProc1 = new LcdProc("localhost", 13666)) {

			Thread currentThread = Thread.currentThread();

//			Screen screen = lcdProc1.screen();
//			screen.setHeartbeat(Heartbeat.OFF);
//
//			lcdProc1.addKey(KeyName.ENTER).addEventListener((KeyEvent e) -> currentThread.interrupt());
//
//			screen.stringWidget().set(1, 1, "test");
//
//			lcdProc1.info();
//			lcdProc1.backlight(Backlight.TOGGLE);

			// for (KeyName key : new KeyName[] {KeyName.UP, KeyName.DOWN,
			// KeyName.LEFT, KeyName.RIGHT}) {
			//
			// try {
			// lcdProc1.addKey(key,
			// KeyMode.EXCLUSIVE).addEventListener((KeyEvent e) ->
			// lcdProc1.logger.debug(e.toString()));
			// } catch (CommandExecutionException e) {
			//
			// lcdProc1.logger.error(e.getMessage());
			// }
			// }

			MainMenu mainMenu = lcdProc1.mainMenu("main");
			mainMenu.addAction("Exit").addEventListener((MenuEvent e) -> currentThread.interrupt());
			mainMenu.addRing("Ring", 0, "first", "second", "third")
					.addEventListener((MenuEvent e) -> lcdProc1.logger.debug(e.toString()));

			mainMenu.activate();
			mainMenu.show();

			currentThread.join();
		} catch (InterruptedException e) {

		}
	}
}
