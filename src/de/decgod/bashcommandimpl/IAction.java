package de.decgod.bashcommandimpl;

import java.util.HashMap;

public interface IAction {
	
	void doAction(HashMap<String, String> hm);
	
	String getCommandName();
	
	String getDescripton();
}
