package org.awi.jlcdproc.menu;

import java.util.ArrayList;

import org.awi.jlcdproc.io.Connection;

public class Menu extends MenuItem {

	private static int currentId = 1;
	
	private ArrayList<MenuItem> menuItems = new ArrayList<>();

	Menu(Connection connection, Menu menu, String itemId, String name) {

		super(connection, menu, itemId, name);
	}

	public Menu addMenu(String name) {

		return addMenu(String.format("m%d", currentId++), name);
	}

	public Menu addMenu(String menuId, String name) {

		Menu menu = new Menu(connection, this, menuId, name);
		menuItems.add(menu);

		return menu;
	}

	public Action addAction(String name) {
		
		return addAction(String.format("a%d", currentId++), name);
	}

	public Action addAction(String itemId, String name) {

		Action action = new Action(connection, this, itemId, name);
		menuItems.add(action);
		return action;
	}

	public Checkbox addCheckbox(String name, Checkbox.Value initialValue) {
		
		return addCheckbox(String.format("cb%d", currentId ++), name, initialValue);
	}
	
	public Checkbox addCheckbox(String itemId, String name, Checkbox.Value initialValue) {
		
		Checkbox checkbox = new Checkbox(connection, this, itemId, name, initialValue);
		menuItems.add(checkbox);
		return checkbox;
	}
	
	public Ring addRing(String name, int initialValue, String... values) {

		return addRing(String.format("r%d", currentId++), name, initialValue, values);
	}
	
	public Ring addRing(String itemId, String name, int initialValue, String... values) {
		
		Ring ring = new Ring(connection, this, itemId, name, initialValue, values);
		menuItems.add(ring);
		return ring;
	}
	
	public Slider addSlider(String name) {
		
		return addSlider(String.format("s%d", currentId++), name);
	}
	
	public Slider addSlider(String itemId, String name) {
		
		Slider slider = new Slider(connection, this, itemId, name);
		menuItems.add(slider);
		return slider;
	}
	
	public Numeric addNumeric(String name) {
		
		return addNumeric(String.format("n%d", currentId++), name);
	}
	
	public Numeric addNumeric(String itemId, String name) {
		
		Numeric numeric = new Numeric(connection, this, itemId, name);
		menuItems.add(numeric);
		return numeric;
	}
	
	public Alpha addAlpha(String name) {
		
		return addAlpha(String.format("alpha%d", currentId++), name);
	}

	public Alpha addAlpha(String itemId, String name) {
		
		Alpha alpha = new Alpha(connection, this, itemId, name);
		menuItems.add(alpha);
		return alpha;
	}

	void activate() throws Exception {

		for (MenuItem menuItem : menuItems) {

			menuItem.menuAddItem(itemId);

			menuItem.activate();
		}

		for (MenuItem menuItem : menuItems) {

			menuItem.menuSetItem();
		}
	}
	
	public void show() throws Exception {
		
		show(null);
	}

	public void show(Menu parent) throws Exception {
		
		connection.send("menu_goto", quote(itemId), parent != null ? parent.getItemId() : null);
	}

	@Override
	protected String getType() {

		return "menu";
	}
}
