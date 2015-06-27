package org.awi.jlcdproc.events;

import org.awi.jlcdproc.commands.Hello;

/**
 * Event received as result of the {@link Hello} command.
 *
 * A typical event received from the LCDproc server looks like this:
 * 
 * LCDproc 0.5.7 protocol 0.3 lcd wid 16 hgt 2 cellwid 5 cellhgt 8
 * 
 * The constructor parses this string and the values are exposed through
 * getters.
 */
public class ConnectEvent implements CommandResultEvent {

	private final String version;
	private final String protocolVersion;
	private final int width;
	private final int height;
	private final int cellWidth;
	private final int cellHeight;

	/**
	 * Constructor
	 * 
	 * @param connectString String received as a result to the {@link Hello} command.
	 */
	public ConnectEvent(String connectString) {

		String[] strings = connectString.split(" ");

		this.version = strings[1];
		this.protocolVersion = strings[3];
		this.width = Integer.parseInt(strings[6]);
		this.height = Integer.parseInt(strings[8]);
		this.cellWidth = Integer.parseInt(strings[10]);
		this.cellHeight = Integer.parseInt(strings[12]);
	}

	/**
	 * Getter
	 * 
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Getter
	 * 
	 * @return protocol version
	 */
	public String getProtocolVersion() {
		
		return protocolVersion;
	}

	/**
	 * Getter
	 * 
	 * @return width of the display
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Getter
	 * 
	 * @return Height of the display
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Getter
	 * 
	 * @return cell width of the display
	 */
	public int getCellWidth() {
		return cellWidth;
	}

	/**
	 * Getter
	 * 
	 * @return cell height of the display
	 */
	public int getCellHeight() {
		return cellHeight;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConnectEvent [version=" + version + ", protocolVersion=" + protocolVersion + ", width=" + width + ", height="
				+ height + ", cellWidth=" + cellWidth + ", cellHeight=" + cellHeight + "]";
	}

	/* (non-Javadoc)
	 * @see org.awi.jlcdproc.events.CommandResultEvent#isSuccess()
	 */
	@Override
	public boolean isSuccess() {

		return true;
	}
}
