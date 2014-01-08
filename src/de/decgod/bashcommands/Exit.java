package de.decgod.bashcommands;

import java.util.HashMap;

import de.decgod.bashcommandimpl.AbstractAction;


public class Exit extends AbstractAction {

	@Override
	public String getDescripton() {
		return "Exit - quits the game";
	}

	@Override
	public void doAction(HashMap<String, String> hm) {
		System.exit(-3);
	}	

}
