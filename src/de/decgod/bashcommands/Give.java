package de.decgod.bashcommands;

import java.util.HashMap;

import com.mojang.escape.entities.Item;

import de.decgod.bashcommandimpl.AbstractAction;
import de.decgod.mod.InGameLogger;
import de.decgod.mod.Scene;

public class Give extends AbstractAction {

	@Override
	public String getDescripton() {
		return "Give <item> - gives the given item";
	}

	@Override
	public void doAction(HashMap<String, String> hm) {
		if (hm.size() == 1) {
			if (Item.itemExists(hm.get("key_1"))) {
				Scene.getInstance()
						.getPlayer()
						.addLoot(
								Item.valueOf(hm.get("key_1")
										.replaceAll(" ", "").toLowerCase()));
			} else {
				for (Item i : Item.values()) {
					InGameLogger.getInstance().addMessage(
							i.name.replaceAll(" ", "") + " - " + i.description);
				}
			}
		} else if (hm.size() == 2 && hm.get("key_2").matches("\\d+")) {
			if (Item.itemExists(hm.get("key_1"))) {
				for (int i = 0; i < Integer.parseInt(hm.get("key_2")); i++) {
					Scene.getInstance()
							.getPlayer()
							.addLoot(
									Item.valueOf(hm.get("key_1")
											.replaceAll(" ", "").toLowerCase()));
				}
			} else {
				for (Item i : Item.values()) {
					InGameLogger.getInstance().addMessage(
							i.name.replaceAll(" ", "") + " - " + i.description);
				}
			}
		}
	}
}