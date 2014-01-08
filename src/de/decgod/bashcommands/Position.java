package de.decgod.bashcommands;

import java.util.HashMap;

import de.decgod.bashcommandimpl.AbstractAction;
import de.decgod.mod.InGameLogger;
import de.decgod.mod.Scene;

public class Position extends AbstractAction {

	@Override
	public String getDescripton() {
		return "Position <x,y>- sets the position of the player";
	}

	@Override
	public void doAction(HashMap<String, String> hm) {
		
		if(hm.size() == 1 && hm.get("key_1").toLowerCase().equals("get")){
			InGameLogger.getInstance().addMessage(Scene.getInstance().getPlayer().getPosition());
		}
		
		if (hm.size() == 2) {
			if (hm.get("key_1").matches("\\d+")
					&& hm.get("key_2").matches("\\d+")) {
				Scene.getInstance().getPlayer().setPosition(Double.parseDouble(hm.get("key_1")),
						Double.parseDouble(hm.get("key_2")));
			}
		}
	}

}