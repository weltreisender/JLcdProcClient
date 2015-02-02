package org.awi.jlcdproc.impl;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.awi.jlcdproc.LcdProc;
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
import org.awi.jlcdproc.io.Connection;
import org.awi.jlcdproc.io.ConnectionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link LcdProc} client for Java
 * <p>	
 * This class acts as an interface to the Linux LCDproc server.
 */
public class LcdProcImpl implements LcdProc, LcdProcInternal {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ConnectionImpl connection;

	private ConcurrentLinkedQueue<EventListener> eventListeners = new ConcurrentLinkedQueue<>();

	private final ExecutorService executorService;

	private int currentScreenId = 0;

	/**
	 * Default constructor that tries to connec to a LCDproc server running on
	 * localhost using the default port (13666).
	 * 
	 * @throws Exception
	 *             if connecting to the default server fails for some reason
	 */
	public LcdProcImpl() throws Exception {

		this("localhost", 13666);
	}

	/**
	 * Constructor that tries to connect to the LCDproc server defined by the
	 * given host and port.
	 * 
	 * @param host
	 *            Host where the server is running
	 * @param port
	 *            Port of the server
	 * @throws Exception
	 *             if connecting to the given server fails for some reason
	 */
	public LcdProcImpl(String host, int port) throws Exception {

		connection = new ConnectionImpl(this, host, port);
		connection.connect();

		logger.debug(String.format("LcdProc successfully connected to %s:%d", host, port));

		ThreadFactory executorServiceFactory = new DefaultThreadFactory("cmd");
		executorService = Executors.newCachedThreadPool(executorServiceFactory);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#close()
	 */
	@Override
	public void close() throws IOException {

		logger.debug("close connection to LcdProc");
		connection.close();
		executorService.shutdown();
	}

	// public void clientName(String name) throws Exception {
	//
	// connection.send(null, "client_set", "-name", name);
	// }

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#screen()
	 */
	@Override
	public Screen screen() throws Exception {

		return new Screen(this, currentScreenId++);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#screen(java.lang.String)
	 */
	@Override
	public Screen screen(String screenId) throws Exception {

		return new Screen(this, screenId);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#mainMenu(java.lang.String)
	 */
	@Override
	public MainMenu mainMenu(String name) {

		return new MainMenu(this, connection, name);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#addKey(org.awi.jlcdproc.commands.keys.KeyName)
	 */
	@Override
	public Key addKey(KeyName keyName) throws Exception {

		return new Key(this, keyName);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#addKey(org.awi.jlcdproc.commands.keys.KeyName, org.awi.jlcdproc.commands.keys.KeyMode)
	 */
	@Override
	public Key addKey(KeyName key, KeyMode keyMode) throws Exception {

		return new Key(this, key, keyMode);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#info()
	 */
	@Override
	public String info() throws Exception {

		Info info = new Info(this);

		info.send(info);

		return ((DriverInfoEvent) info.getEvent()).getDriverInfo();
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#fireEvent(org.awi.jlcdproc.events.Event)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#backlight(org.awi.jlcdproc.commands.Backlight)
	 */
	@Override
	public void backlight(Backlight backlight) throws Exception {

		BacklightCommand backlightCmd = new BacklightCommand(this, backlight);
		backlightCmd.send();
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#addEventListener(org.awi.jlcdproc.events.EventListener)
	 */
	@Override
	public void addEventListener(EventListener eventListener) {

		eventListeners.add(eventListener);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.impl.LcdProcIf#removeEventListener(org.awi.jlcdproc.events.EventListener)
	 */
	@Override
	public void removeEventListener(EventListener eventListener) {

		eventListeners.remove(eventListener);
	}

	@Override
	public Connection getConnection() {

		return connection;
	}
}
