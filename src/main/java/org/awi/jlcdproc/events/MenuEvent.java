package org.awi.jlcdproc.events;

import org.awi.jlcdproc.menu.MenuItem;

public class MenuEvent implements Event {

	public enum Type {
		
		ENTER(false),
		LEAVE(false),
		SELECT(false),
		UPDATE(true),
		PLUS(true),
		MINUS(true);
		
		private final boolean hasValue;
		
		private Type(boolean hasValue) {
		
			this.hasValue = hasValue;
		}
		
		public boolean hasValue() {
			return hasValue;
		}
	}
	
	private final MenuItem menuItem;
	
	private final String itemId;
	
	private final Type type;
	
	private final String value;
	
	public MenuEvent(String paramString) {
		
		String[] params = paramString.split(" ");
		
		this.menuItem = null;
		this.type = Type.valueOf(params[0].toUpperCase());
		this.itemId = params[1];
		
		value = type.hasValue() ? (params.length == 3 ? params[2] : "") : null;
	}
	
	public MenuEvent(MenuItem menuItem, MenuEvent menuEvent) {
		
		this.menuItem = menuItem;
		this.type = menuEvent.type;
		this.itemId = menuEvent.itemId;
		this.value = menuEvent.value;
	}
	
	public MenuItem getMenuItem() {
		return menuItem;
	}
	
	public Type getType() {
		
		return type;
	}
	
	public String getItemId() {
		
		return itemId;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "MenuEvent [itemId=" + itemId + ", type=" + type + ", value=" + value + "]";
	}

}

