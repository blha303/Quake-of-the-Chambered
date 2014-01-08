package de.decgod.mod;

import java.util.ArrayList;
import java.util.List;

import com.mojang.escape.gui.Screen;

public class InGameLogger {

	private static InGameLogger instance = null;
	private List<String> messages = new ArrayList<String>();
	boolean rendering = false;
	int renderTime = 500;
	int lastrendered = 0;
	
	private InGameLogger() { }

	public static InGameLogger getInstance() {
		if (instance == null) {
			instance = new InGameLogger();
		}

		return instance;
	}


	public void log(Screen screen) {
		if (renderTime >= 0 && messages.size() > 0) {
			renderTime -= messages.size();
			rendering = true;
			if (rendering) {
				for (int i = 0; i < messages.size(); i++) {
					screen.draw(messages.get(i), 1, screen.height/2 + (i * 8), 0xFFFFFF);
					lastrendered = i;
				}
			}
		} else if (renderTime < 0) {
			rendering = false;
			messages.remove(0);
			renderTime = 500;
		}
	}

	public void addMessage(String msg) {
		messages.add(msg);
	}
	
}
