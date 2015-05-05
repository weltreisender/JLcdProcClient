package org.awi.jlcdproc;

import org.awi.jlcdproc.commands.Backlight;
import org.awi.jlcdproc.commands.keys.Key;
import org.awi.jlcdproc.commands.keys.KeyMode;
import org.awi.jlcdproc.commands.keys.KeyName;
import org.awi.jlcdproc.commands.menu.MainMenu;
import org.awi.jlcdproc.commands.widget.Screen;
import org.awi.jlcdproc.commands.widget.Widget;
import org.awi.jlcdproc.events.MenuEvent;
import org.awi.jlcdproc.impl.EventListenerProvider;
import org.awi.jlcdproc.impl.LcdProcImpl;

/**
 * LcdProc client for Java
 * <p>
 * This class acts as an interface to the Linux LCDproc server.
 */
public interface LcdProc extends AutoCloseable, EventListenerProvider {

	/**
	 * Create a new {@link Screen} object with an automatically generated screen
	 * ID that can be used to create new {@link Widget Widgets}.
	 * 
	 * @return {@link Screen} object
	 * @throws Exception
	 *             if the screen object could not be created
	 */
	public abstract Screen screen() throws Exception;

	/**
	 * Create a new {@link Screen} object with the given screen ID that can be
	 * used to create new {@link Widget Widgets}.
	 * 
	 * @param screenId
	 *            Screen ID used to identify the screen
	 * @return {@link Screen} object
	 * @throws Exception
	 *             if the screen object could not be created
	 */
	public abstract Screen screen(String screenId) throws Exception;

	/**
	 * Create a new {@link MainMenu} with the given name.
	 * 
	 * @param name
	 *            Name of the {@link MainMenu}
	 * 
	 * @return {@link MainMenu}
	 */
	public abstract MainMenu mainMenu(String name);

	/**
	 * Create a new {@link Key} object for the given {@link KeyName}. Listeners
	 * can be added to this object to react if the key was pressed.
	 * 
	 * {@link KeyMode#SHARED} is used for {@link Key} objects created with this
	 * method.
	 * 
	 * @param keyName
	 *            {@link KeyName} to create the object for
	 * @return {@link Key} object
	 * @throws Exception
	 *             if the key object could not be created
	 */
	public abstract Key addKey(KeyName keyName) throws Exception;

	/**
	 * Create a new {@link Key} object for the given {@link KeyName} with the
	 * given {@link KeyMode}. Listeners can be added to this object to react if
	 * the key was pressed.
	 * 
	 * @param keyName
	 *            {@link KeyName} to create the object for
	 * @param keyMode
	 *            {@link KeyMode} to use for the key
	 * @return {@link Key} object
	 * @throws Exception
	 *             if the key object could not be created. E.g. not all keys can
	 *             be created using {@link KeyMode#EXCLUSIVE}
	 */
	public abstract Key addKey(KeyName key, KeyMode keyMode) throws Exception;

	/**
	 * Use this method to get driver information
	 * 
	 * @return Driver informatin
	 * @throws Exception
	 *             if the information could no be retrieved for some reason
	 */
	public abstract String info() throws Exception;

	/**
	 * Change the backlight of the LCD (not possible for all displays)
	 * 
	 * @param backlight
	 *            {@link Backlight} to use for the LCD
	 * @throws Exception
	 *             if the command could not be executed for some reason
	 */
	public abstract void backlight(Backlight backlight) throws Exception;

	/**
	 * Create a new {@link LcdProc} instance that connects to an LcdProc server
	 * on localhost port 13666.
	 * 
	 * @return New instance of {@link LcdProc}
	 * @throws Exception
	 *             if the connection could not be established
	 */
	public static LcdProc create() throws Exception {

		return new LcdProcImpl();
	}

	/**
	 * Create a new {@link LcdProc} instance that connects to an LcdProc server
	 * on the given host and port.
	 * 
	 * @param host
	 *            Hostname or IP address where the LCDproc server is listening
	 * @param port
	 *            Port where the LCDproc server is listening
	 * @return New instance of {@link LcdProc}
	 * @throws Exception
	 *             if the connection could not be stablished
	 */
	public static LcdProc create(String host, int port) throws Exception {

		return new LcdProcImpl(host, port);
	}

	public static void main(String[] args) throws Exception {

		try (LcdProc lcdProc1 = create("mediapc", 13666)) {

			Thread currentThread = Thread.currentThread();

			// Screen screen = lcdProc1.screen();
			// screen.set(heartbeatOff, cursorOn);
			// StringWidget stringWidget = screen.stringWidget();
			//
			// final int[] pos = { 1, 1 };
			// stringWidget.set(1, 2, String.format("%d, %d", pos[0], pos[1]));
			//
			// lcdProc1.addKey(KeyName.RIGHT).addEventListener(e -> {
			// try {
			// pos[0] = ++pos[0] < 17 ? pos[0] : 16;
			// screen.setCursorPosition(pos[0], pos[1]);
			// stringWidget.set(1, 2, String.format("%d, %d", pos[0], pos[1]));
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
			// });
			//
			// lcdProc1.addKey(KeyName.LEFT).addEventListener(e -> {
			// try {
			// pos[0] = --pos[0] > 0 ? pos[0] : 1;
			// screen.setCursorPosition(pos[0], pos[1]);
			// stringWidget.set(1, 2, String.format("%d, %d", pos[0], pos[1]));
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
			// });
			//
			// lcdProc1.addKey(KeyName.UP).addEventListener(e -> {
			// try {
			// pos[1] = --pos[1] > 0 ? pos[1] : 1;
			// screen.setCursorPosition(pos[0], pos[1]);
			// stringWidget.set(1, 2, String.format("%d, %d", pos[0], pos[1]));
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
			// });
			//
			// lcdProc1.addKey(KeyName.DOWN).addEventListener(e -> {
			// try {
			// pos[1] = ++pos[1] < 5 ? pos[1] : 2;
			// screen.setCursorPosition(pos[0], pos[1]);
			// stringWidget.set(1, 2, String.format("%d, %d", pos[0], pos[1]));
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
			// });
			//
			// lcdProc1.addKey(KeyName.ENTER).addEventListener((KeyEvent e) ->
			// currentThread.interrupt());
			//
			// System.out.println(lcdProc1.info());

			//
			// screen.stringWidget().set(1, 1, "test");
			//
			// lcdProc1.info();
			// lcdProc1.backlight(Backlight.TOGGLE);

			// for (KeyName key : new KeyName[] {KeyName.UP, KeyName.DOWN,
			// KeyName.LEFT, KeyName.RIGHT}) {
			//
			// try {
			// lcdProc1.addKey(key,
			// KeyMode.EXCLUSIVE).addEventListener((KeyEvent e) ->
			// lcdProc1.logger.debug(e.toString()));
			// } catch (CommandExecutionException e) {
			//
			// lcdProc1.logger.error(e.getMessage());
			// }
			// }

			MainMenu mainMenu = lcdProc1.mainMenu("main");
			mainMenu.addAction("Exit").addEventListener((MenuEvent e) -> currentThread.interrupt());
			mainMenu.addRing("Ring", 0, "first", "second", "third")
					.addEventListener((MenuEvent e) -> System.out.println(e.toString()));

			mainMenu.activate();
			mainMenu.show();

			currentThread.join();
		} catch (InterruptedException e) {

		}
	}
}