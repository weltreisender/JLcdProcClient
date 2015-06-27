package org.awi.jlcdproc.commands.menu;

import java.util.ArrayList;

import org.awi.jlcdproc.commands.CommandUtils;
import org.awi.jlcdproc.impl.LcdProcInternal;

import static org.awi.jlcdproc.commands.CommandParameters.*;

/**
 * This class represents a menu that offers methods to add menu items (actions,
 * check boxes, sliders, etc.) or other (sub-) menus.
 */
public class Menu extends MenuItem {

	private static final String MENU_GOTO = "menu_goto";

	private static int currentId = 1;

	private ArrayList<MenuItem> menuItems = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param menu
	 *            Parent menu to which this menu instance belongs (
	 *            <code>null</code>, if it is the {@link MainMenu})
	 * @param itemId
	 *            ID of the menu
	 * @param name
	 *            Name, that will be displayed
	 */
	Menu(LcdProcInternal lcdProc, Menu menu, String itemId, String name) {

		super(lcdProc, menu, itemId, name);
	}

	/**
	 * Adds a new menu with the given name and an automatically generated ID
	 * 
	 * @param name
	 *            Menu name, that will be displayed
	 * @return Instance of the new {@link Menu}
	 */
	public Menu addMenu(String name) {

		return addMenu(String.format("m%d", currentId++), name);
	}

	/**
	 * Adds a new menu with the given name and ID
	 * 
	 * @param menuId
	 *            ID to use for the new {@link Menu}
	 * @param name
	 *            Menu name, that will be displayed
	 * @return Instance of the new {@link Menu}
	 */
	public Menu addMenu(String menuId, String name) {

		Menu menu = new Menu(lcdProc, this, menuId, name);
		menuItems.add(menu);

		return menu;
	}

	/**
	 * Adds a new action with the given name and an automatically generated ID.
	 * 
	 * @param name
	 *            Action name, that will be displayed
	 * @return Instance of the new {@link Action}
	 */
	public Action addAction(String name) {

		return addAction(String.format("a%d", currentId++), name);
	}

	/**
	 * Adds a new action with the given name and ID.
	 * 
	 * @param itemId
	 *            ID to use for the new {@link Action}
	 * @param name
	 *            Action name, that will be displayed
	 * @return Instance of the new {@link Action}
	 */
	public Action addAction(String itemId, String name) {

		Action action = new Action(lcdProc, this, itemId, name);
		menuItems.add(action);

		return action;
	}

	/**
	 * Adds a new checkbox with the given name and an automatically generated ID
	 * 
	 * @param name
	 *            Checkbox name, that will be displayed
	 * @param initialValue
	 *            Initial value of the checkbox
	 * @return Instance of thenew {@link Checkbox}
	 */
	public Checkbox addCheckbox(String name, CheckboxValue initialValue) {

		return addCheckbox(String.format("cb%d", currentId++), name, initialValue);
	}

	/**
	 * Adds a new checkbox with the given name and ID
	 * 
	 * @param itemId
	 *            ID to use for the new {@link Checkbox}
	 * @param name
	 *            Checkbox name, that will be displayed
	 * @param initialValue
	 *            Initial value of the checkbox
	 * @return Instance of the new {@link Checkbox}
	 */
	public Checkbox addCheckbox(String itemId, String name, CheckboxValue initialValue) {

		Checkbox checkbox = new Checkbox(lcdProc, this, itemId, name, initialValue);
		menuItems.add(checkbox);

		return checkbox;
	}

	/**
	 * Adds a new ring with the given name and an automatically generated ID
	 * 
	 * @param name
	 *            Ring name, that will be displayed
	 * @param initialValue
	 *            Initial value of the ring
	 * @param values
	 *            List of strings, through which the ring iterates.
	 * @return Instance of the new {@link Ring}
	 */
	public Ring addRing(String name, int initialValue, String... values) {

		return addRing(String.format("r%d", currentId++), name, initialValue, values);
	}

	/**
	 * Adds a new ring with the given name and ID
	 * 
	 * @param itemId
	 *            ID to use for the new {@link Ring}
	 * @param name
	 *            Ring name, that will be displayed
	 * @param initialValue
	 *            Initial value of the ring
	 * @param values
	 *            List of strings, through which the ring iterates.
	 * @return Instance of the new {@link Ring}
	 */
	public Ring addRing(String itemId, String name, int initialValue, String... values) {

		Ring ring = new Ring(lcdProc, this, itemId, name, initialValue, values);
		menuItems.add(ring);

		return ring;
	}

	/**
	 * Adds a new slider with the given name and an automatically generated ID.
	 * 
	 * @param name
	 *            Slider name, that will be displayed
	 * @return Isntance of the new {@link Slider}
	 */
	public Slider addSlider(String name) {

		return addSlider(String.format("s%d", currentId++), name);
	}

	/**
	 * Adds a new slider with the given name and ID.
	 * 
	 * @param itemId
	 *            ID to use for the new {@link Slider}
	 * @param name
	 *            Slider name, that will be displayed
	 * @return Isntance of the new {@link Slider}
	 */
	public Slider addSlider(String itemId, String name) {

		Slider slider = new Slider(lcdProc, this, itemId, name);
		menuItems.add(slider);

		return slider;
	}

	/**
	 * Adds a new numeric with the given name and an automatically generated ID
	 * 
	 * @param name
	 *            Numeric name, that will be displayed
	 * @return Instance of the new {@link Numeric}
	 */
	public Numeric addNumeric(String name) {

		return addNumeric(String.format("n%d", currentId++), name);
	}

	/**
	 * Adds a new numeric with the given name and ID
	 * 
	 * @param itemId
	 *            ID to use for the new {@link Numeric}
	 * @param name
	 *            Numeric name, that will be displayed
	 * @return Instance of the new {@link Numeric}
	 */
	public Numeric addNumeric(String itemId, String name) {

		Numeric numeric = new Numeric(lcdProc, this, itemId, name);
		menuItems.add(numeric);

		return numeric;
	}

	/**
	 * Adds a new alpha with the given name and an automatically generated ID
	 * 
	 * @param name
	 *            Alpha name, that will be displayed
	 * @return Instance of the new {@link Alpha}
	 */
	public Alpha addAlpha(String name) {

		return addAlpha(String.format("alpha%d", currentId++), name);
	}

	/**
	 * Adds a new alpha with the given name and ID
	 * 
	 * @param itemId
	 *            ID to use for the new {@link Alpha}
	 * @param name
	 *            Alpha name, that will be displayed
	 * @return Instance of the new {@link Alpha}
	 */
	public Alpha addAlpha(String itemId, String name) {

		Alpha alpha = new Alpha(lcdProc, this, itemId, name);
		menuItems.add(alpha);

		return alpha;
	}

	/**
	 * Removes a menu item from the internal list of menu items. It does not
	 * remove it in the display.
	 * 
	 * @param menuItem
	 *            Menu item to remove.
	 */
	void remove(MenuItem menuItem) {

		menuItems.remove(menuItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#activate()
	 */
	@Override
	void activate() throws Exception {

		// in a first step iterate over all menu items, add them to the display
		// and activate them
		for (MenuItem menuItem : menuItems) {

			menuItem.menuAddItem(itemId);

			menuItem.activate();
		}

		// in a second step, set the options of the menu items
		// this is necessary as potential references in -next and -prev might
		// not be known by the LCDproc server in the first run
		for (MenuItem menuItem : menuItems) {

			menuItem.menuSetItem();
		}
	}

	/**
	 * Show the menu
	 * 
	 * @throws Exception
	 */
	public void show() throws Exception {

		show(null);
	}

	/**
	 * Show the menu and set a new parent menu that will be shown, when the menu
	 * is left
	 * 
	 * @param parent
	 *            Parent menu, that wil be shown after this menu
	 * @throws Exception
	 */
	public void show(Menu parent) throws Exception {

		if (parent == null) {

			send(MENU_GOTO, params(CommandUtils.quote(itemId)));
		} else {

			send(MENU_GOTO, params(CommandUtils.quote(itemId), CommandUtils.quote(parent.getItemId())));
		}
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.menu.MenuItem#getType()
	 */
	@Override
	protected String getType() {

		return "menu";
	}
}
