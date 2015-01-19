package org.awi.jlcdproc.commands.keys;

public enum KeyName {

	LEFT("Left"),
	UP("Up"),
	DOWN("Down"),
	RIGHT("Right"),
	ENTER("Enter"),
	ESC("Escape"),
	VIDEO("Video"),
	AUDIO("Audio"),
	TV("Tv"),
	IMAGES("Images"),
	MENU("Menu"),
	VOLUMEUP("Volumeup"),
	VOLUMEDOWN("Volumedown"),
	MUTE("Mute"),
	CHANNELUP("Channelup"),
	CHANNELDOWN("Channeldown"),
	REWIND("Rewind"),
	FASTFORWARD("Fastforward"),
	PLAY("Play"),
	PAUSE("Pause"),
	PREVIOUS("Previous"),
	NEXT("Next"),
	RECORD("Record"),
	STOP("Stop"),
	BACKSPACE("Backspace"),
	TIME("Time"),
	MEDIA("Media"),
	EJECTCD("Ejectcd"),
	SUBTITLE("Subtitle"),
	NUMERIC_STAR("*"),
	NUMERIC_POUND("#"),
	EXIT("Exit"),
	DASHBOARD("Dashboard"),
	BOOKMARKS("Bookmarks"),
	LANGUAGE("Language"),
	DVD("Dvd"),
	EJECTCLOSECD("Ejectclosecd"),
	SCREEN("Screen"),
	COMPOSE("Compose"),
	CYCLEWINDOWS("Cyclewindows"),
	SPACE("Space"),
	CAMERA("Camera"),
	ZOOM("Zoom"),
	CONTEXT_MENU("Contextmenu"),
	BTN_LEFT("Buttonleft"),
	BTN_RIGHT("Buttonright"),
	NUMBER_0("0"),
	NUMBER_1("1"),
	NUMBER_2("2"),
	NUMBER_3("3"),
	NUMBER_4("4"),
	NUMBER_5("5"),
	NUMBER_6("6"),
	NUMBER_7("7"),
	NUMBER_8("8"),
	NUMBER_9("9"),
	;

	
	private String keyName;

	private KeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyName() {
		
		return keyName;
	}
	
	public static KeyName ofKeyName(String keyName) {
		
		for (KeyName key : values()) {
			
			if (key.getKeyName().equals(keyName)) {
				
				return key;
			}
		}
		
		throw new IllegalArgumentException("Unknown key name: " + keyName);
	}
}
