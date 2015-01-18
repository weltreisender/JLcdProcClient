package org.awi.jlcdproc.events;

import org.awi.jlcdproc.keys.KeyName;

public class KeyEvent implements Event {

	private final KeyName key;

	public KeyEvent(String keyName) {
		super();
		
		this.key = KeyName.ofKeyName(keyName);
	}
	
	public KeyName getKey() {
		
		return key;
	}

	@Override
	public String toString() {
		return "KeyEvent [key=" + key.getKeyName() + "]";
	}
}
