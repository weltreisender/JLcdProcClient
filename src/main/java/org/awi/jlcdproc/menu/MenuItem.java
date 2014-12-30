package org.awi.jlcdproc.menu;

import org.awi.jlcdproc.io.Connection;

public abstract class MenuItem {

	private final static String MENU_SET_ITEM = "menu_set_item";

	protected final Connection connection;

	protected Menu menu;
	
	protected final String itemId;

	protected final String name;

	private MenuItem next;

	private MenuItem prev;

	private Boolean hide = null;

	MenuItem(Connection connection, Menu menu, String itemId, String name) {

		this.menu = menu;
		this.connection = connection;
		this.itemId = itemId;
		this.name = name;
	}

	public MenuItem prev(MenuItem prev) {

		this.prev = prev;
		return this;
	}

	public MenuItem next(MenuItem next) {

		this.next = next;
		return this;
	}

	public MenuItem hide(boolean hide) {

		this.hide = hide;
		return this;
	}

	public String getItemId() {
		return itemId;
	}

	public String getName() {

		return name;
	}

	public MenuItem getNext() {
		return next;
	}

	public MenuItem getPrev() {
		return prev;
	}

	public boolean isHide() {
		return hide;
	}

	protected void menuSet() throws Exception {

		if (prev == null && next == null && hide == null) {

			return;
		}
		
		connection.send(MENU_SET_ITEM,
				quote(menu.getItemId()),
				quote(itemId),
				option("-prev", prev),
				option("-next", next),
				option("-is_hidden", hide));
	}

	protected static Object[] option(String option, Object value) {

		if (value != null) {

			if (value instanceof MenuItem) {
				
				return new Object[] { option, ((MenuItem)value).getItemId() };
			} else {
				
				return new Object[] { option, value };
			}
		}

		return null;
	}

	protected static String quote(String s) {

		return String.format("\"%s\"", s);
	}

	void activate() throws Exception {
	}
	
	Object[] getMenuItemArguments() throws Exception {

		return null;
	}
	
	protected abstract String getType();
}
