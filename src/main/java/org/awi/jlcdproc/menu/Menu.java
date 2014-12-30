package org.awi.jlcdproc.menu;

import java.util.ArrayList;

import org.awi.jlcdproc.io.Connection;

public class Menu extends MenuItem {

	private final static String MENU_ADD_ITEM = "menu_add_item";

	private ArrayList<MenuItem> menuItems = new ArrayList<>();

	Menu(Connection connection, Menu menu, String itemId, String name) {

		super(connection, menu, itemId, name);
	}

	public Menu addMenu(String menuId, String name) {

		Menu menu = new Menu(connection, this, menuId, name);
		menuItems.add(menu);

		return menu;
	}

	public Action addAction(String itemId, String name) {

		Action action = new Action(connection, this, itemId, name);
		menuItems.add(action);
		return action;
	}

	void activate() throws Exception {

		for (MenuItem menuItem : menuItems) {

			connection.send(MENU_ADD_ITEM,
					quote(itemId),
					quote(menuItem.getItemId()),
					menuItem.getType(),
					"-text",
					quote(menuItem.getName()),
					menuItem.getMenuItemArguments());

			menuItem.activate();
		}

		for (MenuItem menuItem : menuItems) {

			menuItem.menuSet();
		}
	}

	@Override
	protected String getType() {

		return "menu";
	}
}
