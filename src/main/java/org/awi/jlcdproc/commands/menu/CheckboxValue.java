package org.awi.jlcdproc.commands.menu;

public enum CheckboxValue {

	ON,
	OFF,
	GRAY;

	@Override
	public String toString() {

		return name().toLowerCase();
	}
}