package de.decgod.bashcommands;

import java.util.HashMap;

import de.decgod.bashcommandimpl.AbstractAction;
import de.decgod.mod.Scene;

public class Client extends AbstractAction{

	@Override
	public String getDescripton() {
		return "Player";
	}

	@Override
	public void doAction(HashMap<String, String> hm) {
		if(hm.size() == 2 && hm.get("key_2").matches("\\d+")){
			if (hm.get("key_1").toLowerCase().equals("speed")) {
				Scene.getInstance().getPlayer().setWalkspeedExtra(Double.parseDouble(hm.get("key_2")));
			}else if (hm.get("key_1").toLowerCase().equals("health")) {
				Scene.getInstance().getPlayer().setHealth(Integer.parseInt(hm.get("key_2")));
			}
		}
	}

}
