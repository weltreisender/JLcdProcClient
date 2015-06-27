package org.awi.jlcdproc.commands.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.awi.jlcdproc.commands.Command;
import org.awi.jlcdproc.commands.CommandUtils;
import org.awi.jlcdproc.events.Event;
import org.awi.jlcdproc.events.EventListener;
import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.LcdProcInternal;

import static org.awi.jlcdproc.commands.CommandParameters.*;

/**
 * Abstract base class for all menu items.
 * 
 * It holds common attributes, assures that item IDs are unique, allows to
 * register event handlers for menu events and handles the calls to the LCDproc
 * server
 */
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

	/**
	 * Construcor
	 * 
	 * @param lcdProc
	 * @param menu
	 *            Menu to which this menu item will belong. Might only be
	 *            <code>null</code> for the main menu.
	 * @param itemId
	 *            ID of the menu item
	 * @param name
	 *            Name that will be displayed
	 */
	MenuItem(LcdProcInternal lcdProc, Menu menu, String itemId, String name) {

		super(lcdProc);

		if (menuItems.containsKey(itemId)) {

			throw new IllegalArgumentException("Item ID <" + itemId + "> already in use.");
		}

		this.menu = menu;
		this.itemId = itemId.replace(" ", "_");
		this.name = name;

		lcdProc.addEventListener(this);

		menuItems.put(itemId, this);
	}

	/**
	 * Return the type of the menu item (action, ring, slider, ...)
	 * 
	 * @return Type of the menu item
	 */
	protected abstract String getType();

	/**
	 * Getter
	 * 
	 * @return Item ID
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * Getter
	 * 
	 * @return Name of the menu item.
	 */
	public String getName() {

		return name;
	}

	/**
	 * Getter
	 * 
	 * @return Next menu item
	 */
	public MenuItem getNext() {

		return next;
	}

	/**
	 * Getter
	 * 
	 * @return Previous menu item
	 */
	public MenuItem getPrev() {

		return prev;
	}

	/**
	 * Getter
	 * 
	 * @return <code>true</code> if the menu item is hidden. <code>false</code>
	 *         otherwise.
	 */
	public boolean isHidden() {

		return hide;
	}

	/**
	 * Set the previous menu item
	 * 
	 * @param prev
	 *            Previous menu item
	 * @return this
	 */
	public MenuItem prev(MenuItem prev) {

		this.prev = prev;
		return this;
	}

	/**
	 * Set the next menu item
	 * 
	 * @param next
	 *            Next menu item
	 * 
	 * @return this
	 */
	public MenuItem next(MenuItem next) {

		this.next = next;
		return this;
	}

	/**
	 * Set the hidden state
	 * 
	 * @param hide
	 *            <code>true</code>, if the menu item shall be hidden
	 * 
	 * @return this
	 */
	public MenuItem hide(boolean hide) {

		this.hide = hide;
		return this;
	}

	/**
	 * Send a command to the LCDproc server to add the menu item
	 * 
	 * @param menuItemId
	 *            ID of the menu item
	 * @throws Exception
	 */
	protected void menuAddItem(String menuItemId) throws Exception {

		MenuOptions options = new MenuOptions();

		collectMenuItemOptionsInternal(options);

		send(MENU_ADD_ITEM, params(CommandUtils.quote(menuItemId), CommandUtils.quote(itemId), getType()), options.optionsAsArray());
	}

	/**
	 * Send a command to the LCDproc server to set menu item options
	 * 
	 * @throws Exception
	 */
	protected void menuSetItem() throws Exception {

		if (prev == null && next == null && hide == null) {

			return;
		}

		MenuOptions options = new MenuOptions();

		collectMenuItemOptionsInternal(options);

		send(MENU_SET_ITEM, params(CommandUtils.quote(menu.getItemId()), CommandUtils.quote(itemId)), options.optionsAsArray());
	}

	/**
	 * Send a command to the LCDproc server to delete the menu item.
	 * 
	 * @throws Exception
	 */
	public void delete() throws Exception {

		send(MENU_DEL_ITEM, params(CommandUtils.quote(menu.getItemId()), CommandUtils.quote(itemId)));
		menu.remove(this);

	}

	/**
	 * Actions to be done when a menu item is activated. By default, no actions
	 * have to be performed. Derived classes may overwite this method.
	 * 
	 * @throws Exception
	 */
	void activate() throws Exception {
	}

	/**
	 * Send a command to the LCDproc server to update the menu item.
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception {

		MenuOptions options = new MenuOptions();

		collectMenuItemOptionsInternal(options);

		send(MENU_SET_ITEM, params(CommandUtils.quote(menu.getItemId()), CommandUtils.quote(itemId)), options.optionsAsArray());

	}

	/**
	 * Method that adds options to the command that will be sent to the LCDproc
	 * server. By default empty. Derived classes may overwrite this method.
	 * 
	 * @param options
	 *            Object to which additional menu options can be added.
	 * 
	 * @throws Exception
	 */
	protected void collectMenuItemOptions(MenuOptions options) throws Exception {
	};

	private void collectMenuItemOptionsInternal(MenuOptions options) throws Exception {

		options.add("-prev", prev);
		options.add("-next", next);
		options.add("-is_hidden", hide);
		options.add("-text", CommandUtils.quote(name));

		collectMenuItemOptions(options);
	}

	/**
	 * This method is called, if an event for this menu item was received. In
	 * general does nothing, but might be overwritten by derived classes.
	 * 
	 * @param event
	 */
	protected void onEvent(MenuEvent event) {
	}

	/**
	 * Add an event listener to the menu item. The event listener wil be called,
	 * if an event for the menu item was received.
	 * 
	 * @param eventListener
	 *            {@link EventListener}
	 */
	public void addEventListener(MenuEventListener eventListener) {

		eventListeners.add(eventListener);
	}

	/**
	 * Remove an event listener from the menu item.
	 * 
	 * @param eventListener
	 *            {@link EventListener}
	 */
	public void removeEventListener(MenuEventListener eventListener) {

		eventListeners.remove(eventListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.awi.jlcdproc.events.EventListener#onEvent(org.awi.jlcdproc.events
	 * .Event)
	 */
	@Override
	public void onEvent(Event event) {

		// Only handle events of type MenuEvent
		if (!(event instanceof MenuEvent)) {

			return;
		}

		MenuEvent menuEvent = (MenuEvent) event;

		// Check,that the event is for this menu item (ID in the event is the
		// same as ours)
		String eventItemId = menuEvent.getItemId();
		if (!eventItemId.equals(itemId)) {

			return;
		}

		// Update the menu item in the event
		menuEvent.setMenuItem(this);

		// handle the event in derived classes
		onEvent(menuEvent);

		// call the event listeners
		for (MenuEventListener menuEventListener : eventListeners) {

			menuEventListener.onEvent(menuEvent);
		}
	}
}
