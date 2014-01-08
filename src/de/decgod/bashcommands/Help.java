package de.decgod.bashcommands;

import java.util.HashMap;

import de.decgod.bashcommandimpl.AbstractAction;
import de.decgod.bashcommandimpl.IAction;
import de.decgod.mod.InGameLogger;
import de.decgod.mod.Scene;

public class Help extends AbstractAction{

	@Override
	public String getDescripton() {
		return "Help - lists every available command in the bash";
	}

	@Override
	public void doAction(HashMap<String, String> hm) {
		InGameLogger.getInstance().addMessage(
				Scene.getInstance().getPlayer().getBash().getCommands().size() + " Commands in the list");
	
			for(IAction ia : Scene.getInstance().getPlayer().getBash().getCommands()){
				InGameLogger.getInstance().addMessage(ia.getDescripton());
			}
	}

}
