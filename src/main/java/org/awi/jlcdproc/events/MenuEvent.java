package org.awi.jlcdproc.events;

public class MenuEvent implements Event {

	public enum Type {
		
		ENTER,
		LEAVE,
		SELECT;
	}
	
	private final Type type;
	
	private final String itemId;
	
	public MenuEvent(String paramString) {
		
		String[] params = paramString.split(" ");
		
		this.type = Type.valueOf(params[0].toUpperCase());
		this.itemId = params[1];
	}
	
	public Type getType() {
		
		return type;
	}
	
	public String getItemId() {
		
		return itemId;
	}

	@Override
	public String toString() {
		return "MenuEvent [type=" + type + ", itemId=" + itemId + "]";
	}
	
	
}

