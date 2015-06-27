package org.awi.jlcdproc.commands.keys;

import static org.awi.jlcdproc.commands.CommandParameters.params;

import java.util.ArrayList;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.KeyEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

/**
 * This class allows to register event handler for remote control keys.
 */
public class Key extends Command implements EventListener {

	private static final String CLIENT_ADD_KEY = "client_add_key";

	private static final String CLIENT_DEL_KEY = "client_del_key";

	private final KeyName keyName;

	private final KeyMode keyMode;

	private final ArrayList<KeyEventListener> eventListeners = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param keyName
	 *            Name of the key for which event handlers may be registered
	 * @throws Exception
	 */
	public Key(LcdProcInternal lcdProc, KeyName keyName) throws Exception {

		this(lcdProc, keyName, null);
	}

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param keyName
	 *            Name of the key for which event handlers may be registered
	 * @param keyMode
	 *            {@link KeyMode} for the key
	 * @throws Exception
	 */
	public Key(LcdProcInternal lcdProc, KeyName keyName, KeyMode keyMode) throws Exception {
		super(lcdProc);

		this.keyName = keyName;
		this.keyMode = keyMode;

		if (keyMode == null) {

			send(CLIENT_ADD_KEY, params(keyName.getKeyName()));
		} else {

			send(CLIENT_ADD_KEY, params(keyMode == KeyMode.SHARED ? "-shared" : "-exclusively", keyName.getKeyName()));
		}
		lcdProc.addEventListener(this);
	}

	/**
	 * Getter
	 * 
	 * @return Name or the key
	 */
	public KeyName getKeyName() {
		return keyName;
	}

	/**
	 * Stops listening for events fired for this key
	 * 
	 * @throws Exception
	 */
	public void delete() throws Exception {

		send(CLIENT_DEL_KEY, params(keyName.getKeyName()));
		lcdProc.removeEventListener(this);
	}

	/**
	 * Add a {@link KeyEventListener} for this key
	 * 
	 * @param eventListener
	 *            {@link KeyEventListener}
	 */
	public void addEventListener(KeyEventListener eventListener) {

		eventListeners.add(eventListener);
	}

	/**
	 * Remove a {@link KeyEventListener} for this key
	 * 
	 * @param eventListener
	 *            {@link KeyEventListener}
	 */
	public void removeEventListener(KeyEventListener eventListener) {

		eventListeners.remove(eventListener);
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.events.EventListener#onEvent(org.awi.jlcdproc.events.Event)
	 */
	@Override
	public void onEvent(Event event) {

		// If the passed event is a KeyEvent for the actual key,
		// all registered KeyEventListeners will be called.
		if (event instanceof KeyEvent && ((KeyEvent) event).getKey() == keyName) {

			for (KeyEventListener keyEventListener : eventListeners) {

				keyEventListener.onEvent((KeyEvent) event);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.Command#toString()
	 */
	@Override
	public String toString() {
		return "Key [keyName=" + keyName + ", keyMode=" + keyMode + "]";
	}
}
