package de.decgod.bashcommands;

import java.util.HashMap;

import de.decgod.bashcommandimpl.AbstractAction;
import de.decgod.mod.InGameLogger;

public class Echo extends AbstractAction {

	@Override
	public String getDescripton() {
		return "Echo <text> - echoes the given string to the *server*";
	}

	@Override
	public void doAction(HashMap<String, String> hm) {
		if (hm.size() == 1) {
			InGameLogger.getInstance().addMessage(hm.get("key_1"));
		}
	}	

}
