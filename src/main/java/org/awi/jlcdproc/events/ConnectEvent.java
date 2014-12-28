package org.awi.jlcdproc.events;

public class ConnectEvent implements Event {

	private final String version;
	private final String protocolVersion;
	private final int width;
	private final int height;
	private final int cellWidth;
	private final int cellHeight;
	
//	LCDproc 0.5.7 protocol 0.3 lcd wid 16 hgt 2 cellwid 5 cellhgt 8
	
	public ConnectEvent(String connectString) {
	
		String[] strings = connectString.split(" ");
		
		this.version = strings[1];
		this.protocolVersion = strings[3];
		this.width = Integer.parseInt(strings[6]);
		this.height = Integer.parseInt(strings[8]);
		this.cellWidth = Integer.parseInt(strings[10]);
		this.cellHeight = Integer.parseInt(strings[12]);
	}

	public String getVersion() {
		return version;
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getCellWidth() {
		return cellWidth;
	}

	public int getCellHeight() {
		return cellHeight;
	}

	@Override
	public String toString() {
		return "Connect [version=" + version + ", protocolVersion=" + protocolVersion + ", width=" + width + ", height=" + height
				+ ", cellWidth=" + cellWidth + ", cellHeight=" + cellHeight + "]";
	}
}
