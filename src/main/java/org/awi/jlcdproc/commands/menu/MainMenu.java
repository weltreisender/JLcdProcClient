package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.impl.LcdProcInternal;
import org.awi.jlcdproc.io.Connection;

public class MainMenu extends Menu {

	private final String clientName;
	
	public MainMenu(LcdProcInternal lcdProc, Connection connection, String clientName) {
		
		super(lcdProc, null, "_client_menu_", "main");
		
		this.clientName = clientName;
	}

	@Override
	public void activate() throws Exception {
	
		send("client_set", "name", quote(clientName));
		
		super.activate();
		
		send("menu_set_main \"\"");
	}
}
