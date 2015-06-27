package org.awi.jlcdproc.commands.menu;

import org.awi.jlcdproc.commands.CommandUtils;
import org.awi.jlcdproc.impl.LcdProcInternal;

import static org.awi.jlcdproc.commands.CommandParameters.*;

/**
 * This is the main menu of client's menu hierarchy. Upon activation, it sets the client and menu name
 */
public class MainMenu extends Menu {

	private final String clientName;
	
	/**
	 * Constructor
	 * 
	 * @param lcdProc
	 * @param clientName
	 */
	public MainMenu(LcdProcInternal lcdProc, String clientName) {
		
		super(lcdProc, null, "_client_menu_", "main");
		
		this.clientName = clientName;
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.commands.menu.Menu#activate()
	 */
	@Override
	public void activate() throws Exception {
	
		send("client_set", params("name", CommandUtils.quote(clientName)));
		
		super.activate();
		
		send("menu_set_main", params("\"\""));
	}
}
