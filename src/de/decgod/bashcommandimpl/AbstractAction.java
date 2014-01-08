package de.decgod.bashcommandimpl;

public abstract class AbstractAction implements IAction{

	@Override
	public String getCommandName(){
		return getClass().getSimpleName();
	}
	
}
