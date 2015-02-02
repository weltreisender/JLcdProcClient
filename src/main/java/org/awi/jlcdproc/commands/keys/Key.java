package org.awi.jlcdproc.commands.keys;

import java.util.ArrayList;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.events.CommandResultEvent;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.KeyEvent;
import org.awi.jlcdproc.events.SuccessEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

public class Key extends Command implements EventListener {

	private static final String CLIENT_DEL_KEY = "client_del_key";

	private static final String CLIENT_ADD_KEY = "client_add_key";

	private final KeyName keyName;

	private final KeyMode keyMode;

	private final ArrayList<KeyEventListener> eventListeners = new ArrayList<>();

	public Key(LcdProcInternal lcdProc, KeyName keyName) throws Exception {

		this(lcdProc, keyName, null);
	}

	public Key(LcdProcInternal lcdProc, KeyName keyName, KeyMode keyMode) throws Exception {
		super(lcdProc);

		this.keyName = keyName;
		this.keyMode = keyMode;

		if (keyMode == null) {
			
			send(CLIENT_ADD_KEY, keyName.getKeyName());
		} else {

			send(CLIENT_ADD_KEY, keyMode == KeyMode.SHARED ? "-shared" : "-exclusively", keyName.getKeyName());
		}
		lcdProc.addEventListener(this);
	}

	public KeyName getKeyName() {
		return keyName;
	}

	public KeyMode getKeyMode() {
		return keyMode;
	}

	public void delete() throws Exception {

		send(CLIENT_DEL_KEY, keyName.getKeyName());
		lcdProc.removeEventListener(this);
	}

	public void addEventListener(KeyEventListener eventListener) {

		eventListeners.add(eventListener);
	}

	public void removeEventListener(KeyEventListener eventListener) {

		eventListeners.remove(eventListener);
	}

	@Override
	public void onEvent(Event event) {

		if (event instanceof KeyEvent && ((KeyEvent) event).getKey() == keyName) {

			for (KeyEventListener keyEventListener : eventListeners) {

				keyEventListener.onEvent((KeyEvent) event);
			}
		}
	}

	@Override
	public boolean isCommandCompleted(CommandResultEvent event) {

		return event instanceof SuccessEvent;
	}
}
