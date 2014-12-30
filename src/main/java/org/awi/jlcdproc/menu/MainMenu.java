package org.awi.jlcdproc.menu;

import org.awi.jlcdproc.io.Connection;

public class MainMenu extends Menu {

	private final String clientName;
	
	public MainMenu(Connection connection, String clientName) {
		
		super(connection, null, "", "main");
		
		this.clientName = clientName;
	}

	@Override
	public void activate() throws Exception {
	
		connection.send("client_set", "name", quote(clientName));
		
		super.activate();
		
		connection.send("menu_set_main \"\"");
	}
}
