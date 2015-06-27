package org.awi.jlcdproc.events;

import org.awi.jlcdproc.commands.keys.KeyName;

/**
 * Events of this type are sent by the LCDproc server, if a key of a remote
 * control was pressed.
 */
public class KeyEvent implements Event {

	private final KeyName key;

	/**
	 * Constructor
	 * 
	 * @param keyName Name of the key that was pressed.
	 */
	public KeyEvent(String keyName) {
		super();

		this.key = KeyName.ofKeyName(keyName);
	}

	/**
	 * Getter
	 * 
	 * @return Name of the key that was pressed.
	 */
	public KeyName getKey() {

		return key;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KeyEvent [key=" + key.getKeyName() + "]";
	}
}
