package org.awi.jlcdproc.events;

import org.awi.jlcdproc.commands.menu.Action;
import org.awi.jlcdproc.commands.menu.Alpha;
import org.awi.jlcdproc.commands.menu.Checkbox;
import org.awi.jlcdproc.commands.menu.MenuItem;
import org.awi.jlcdproc.commands.menu.Numeric;
import org.awi.jlcdproc.commands.menu.Ring;
import org.awi.jlcdproc.commands.menu.Slider;

/**
 * Events of this type are sent upon menu actions.
 */
public class MenuEvent implements Event {

	/**
	 * Type of the menu event.
	 * 
	 * Depending on the type of the menu item, there are different possibilities
	 * for resulting events.
	 */
	public enum Type {

		/**
		 * The menu item has been entered, which means it is currently active on
		 * the screen. The client could now for example update the value of the
		 * item. If it is a menu, it may be needed to update the values of the
		 * items in it too, because they may be visible too.
		 */
		ENTER(false),

		/**
		 * The menu item has been left, so it is currently not the active item
		 * anymore.
		 */
		LEAVE(false),

		/**
		 * The menu item was activated.
		 * 
		 * Only available for {@link Action Actions}
		 */
		SELECT(false),

		/**
		 * The menu item was modified by the user, so LCDd sends an updated
		 * value.
		 * 
		 * Only available for {@link Checkbox}, {@link Ring}, {@link Numeric}
		 * and {@link Alpha}.
		 */
		UPDATE(true),

		/**
		 * The slider was moved to right (plus), so LCDd sends an updated value.
		 * 
		 * Only available for {@link Slider}
		 */
		PLUS(true),

		/**
		 * The slider was moved to left (minus), so LCDd sends an updated value.
		 * 
		 * Only available for {@link Slider}
		 */
		MINUS(true);

		private final boolean hasValue;

		private Type(boolean hasValue) {

			this.hasValue = hasValue;
		}

		public boolean hasValue() {
			return hasValue;
		}
	}

	private MenuItem menuItem;

	private final String itemId;

	private final Type type;

	private final String value;

	/**
	 * Constructor
	 * 
	 * @param paramString
	 *            Parameters of the event (sent by the LCDproc server)
	 */
	public MenuEvent(String paramString) {

		String[] params = paramString.split(" ");

		this.menuItem = null;
		this.type = Type.valueOf(params[0].toUpperCase());
		this.itemId = params[1];

		value = type.hasValue() ? (params.length == 3 ? params[2] : "") : null;
	}

	/**
	 * Getter
	 * 
	 * @return {@link MenuItem} that caused with the event
	 */
	public MenuItem getMenuItem() {
		return menuItem;
	}

	/**
	 * Setter
	 * 
	 * @param menuItem
	 *            {@link MenuItem} that caused the event
	 */
	public void setMenuItem(MenuItem menuItem) {

		this.menuItem = menuItem;
	}

	/**
	 * Getter
	 * 
	 * @return Event type
	 */
	public Type getType() {

		return type;
	}

	/**
	 * Getter
	 * 
	 * @return ID of the menu item
	 */
	public String getItemId() {

		return itemId;
	}

	/**
	 * Gettter
	 * 
	 * @return the value of the menu item event. Not all event types have
	 *         values. In this case <code>null</code> is returned.
	 */
	public String getValue() {

		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MenuEvent [itemId=" + itemId + ", type=" + type + ", value=" + value + "]";
	}

}
