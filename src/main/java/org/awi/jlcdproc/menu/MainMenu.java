package org.awi.jlcdproc.menu;

import org.awi.jlcdproc.io.Connection;

public class MainMenu extends Menu {

	private final String clientName;
	
	public MainMenu(Connection connection, String clientName) {
		
		super(connection, null, "_client_menu_", "main");
		
		this.clientName = clientName;
	}

	@Override
	public void activate() throws Exception {
	
		send("client_set", "name", quote(clientName));
		
		super.activate();
		
		send("menu_set_main \"\"");
	}
}
