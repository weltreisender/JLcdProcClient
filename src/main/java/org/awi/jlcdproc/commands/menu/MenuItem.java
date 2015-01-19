package org.awi.jlcdproc.commands.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.io.Connection;

public abstract class MenuItem extends Command implements EventListener {

	private final static String MENU_ADD_ITEM = "menu_add_item";

	private final static String MENU_SET_ITEM = "menu_set_item";

	private final static String MENU_DEL_ITEM = "menu_del_item";

	private final static Map<String, MenuItem> menuItems = new HashMap<>();

	protected Menu menu;

	protected final String itemId;

	protected final String name;

	private MenuItem next;

	private MenuItem prev;

	private Boolean hide = null;

	private final ArrayList<MenuEventListener> eventListeners = new ArrayList<>();

	MenuItem(Connection connection, Menu menu, String itemId, String name) {

		super(connection);
		
		if (menuItems.containsKey(itemId)) {

			throw new IllegalArgumentException("Item ID <" + itemId + "> already in use.");
		}

		this.menu = menu;
		this.itemId = itemId.replace(" ", "_");
		this.name = name;

		connection.getLcdProc().addEventListener(this);

		menuItems.put(itemId, this);
	}

	protected abstract String getType();

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

	protected void menuAddItem(String menuId) throws Exception {

		MenuOptions options = new MenuOptions();

		collectMenuItemOptions(options);

		send(MENU_ADD_ITEM, quote(menuId), quote(itemId), getType(), "-text", quote(name), options.optionsAsArray());
	}

	protected void menuSetItem() throws Exception {

		if (prev == null && next == null && hide == null) {

			return;
		}

		send(MENU_SET_ITEM,
				quote(menu.getItemId()),
				quote(itemId),
				option("-prev", prev),
				option("-next", next),
				option("-is_hidden", hide));
	}

	public void delete() throws Exception {

		send(MENU_DEL_ITEM, quote(menu.getItemId()), quote(itemId));
		menu.delete(this);
	}

	void activate() throws Exception {
	}

	public void update() throws Exception {

		MenuOptions options = new MenuOptions();

		collectMenuItemOptions(options);

		send(MENU_SET_ITEM,
				quote(menu.getItemId()),
				quote(itemId),
				option("-prev", prev),
				option("-next", next),
				option("-is_hidden", hide),
				option("-text", quote(name)),
				options.optionsAsArray());

	}

	void collectMenuItemOptions(MenuOptions options) throws Exception {
	}

	void onEvent(MenuEvent event) {
	}

	public void addEventListener(MenuEventListener eventListener) {

		eventListeners.add(eventListener);
	}

	public void removeEventListener(MenuEventListener eventListener) {

		eventListeners.remove(eventListener);
	}

	@Override
	public void onEvent(Event event) {

		if (!(event instanceof MenuEvent)) {

			return;
		}

		MenuEvent menuEvent = (MenuEvent) event;

		String eventItemId = menuEvent.getItemId();
		if (!eventItemId.equals(itemId)) {

			return;
		}

		onEvent(menuEvent);

		for (MenuEventListener menuEventListener : eventListeners) {

			menuEventListener.onEvent(new MenuEvent(this, menuEvent));
		}
	}

	protected static Object[] option(String option, Object value) {

		if (value != null) {

			if (value instanceof MenuItem) {

				return new Object[] { option, ((MenuItem) value).getItemId() };
			} else {

				return new Object[] { option, value };
			}
		}

		return null;
	}

	protected static String quote(String s) {

		if (s == null) {
			
			return null;
		}
		
		String quotedString = s.replace("\"", "\"\"");

		return String.format("\"%s\"", quotedString);
	}

}
