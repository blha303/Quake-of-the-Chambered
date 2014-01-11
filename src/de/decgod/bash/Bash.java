package de.decgod.bash;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mojang.escape.gui.Bitmap;
import com.mojang.escape.gui.Screen;

import de.decgod.bashcommandimpl.IAction;
import de.decgod.bashcommands.Client;
import de.decgod.bashcommands.Echo;
import de.decgod.bashcommands.Exit;
import de.decgod.bashcommands.Give;
import de.decgod.bashcommands.Help;
import de.decgod.bashcommands.Position;
import de.decgod.bashcommands.Spawn;
import de.decgod.mod.InGameLogger;
import de.decgod.mod.RuntimeConfiguration;

public class Bash {

	private boolean isOpen;
	private String command;
	private String input;
	private String allowedChars = RuntimeConfiguration.getInstance()
			.getAllowedChars();
	private boolean rendering = false;
	private Bitmap consoleBackground = new Bitmap(RuntimeConfiguration.getInstance().getScreen().width, 12);
	private List<IAction> commands;

	public Bash() {

		deactivateAndReset();

		commands = new ArrayList<IAction>();
		Echo echo = new Echo();
		Exit exit = new Exit();
		Help help = new Help();
		Position position = new Position();
		Give give = new Give();
		Spawn spawn = new Spawn();
		Client client = new Client();
		
		commands.add(echo);
		commands.add(exit);
		commands.add(help);
		commands.add(position);
		commands.add(give);
		commands.add(spawn);
		commands.add(client);
	}

	/** Listens to the input, directed from InputHandler */
	public void listen(KeyEvent e) {
		if (isOpen) {

			// casts the char-value from the KeyEvent to a String
			input = String.valueOf(e.getKeyChar());
			// controlls the input
			controlInputString();

			// if the user press's enter...
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				// if there is a command (validation) - execute it.
				validateAndExecuteCommand();
				// deactivates the bash and resets the commandstring
				deactivateAndReset();
			}
			
			if (command.length() > 0) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					command = command.substring(0, command.length() - 1);
				}
			}


//			System.out.println("input: >" + input + "< command: > " + command
//					+ "<");
		}
	}

	public HashMap<String, String> parseParams(String rawParams) {

		HashMap<String, String> hm = new HashMap<String, String>();

		final String SEPERATOR = ",";

		int paramCount = 1;

		while (rawParams.length() > 0 && !rawParams.startsWith(SEPERATOR)) {
			if (rawParams.contains(SEPERATOR)
					&& !rawParams.startsWith(SEPERATOR)) {
				hm.put("key_" + paramCount,
						rawParams.substring(0, rawParams.indexOf(SEPERATOR)));
				rawParams = rawParams
						.substring(rawParams.indexOf(SEPERATOR) + 1);
			} else {
				hm.put("key_" + paramCount, rawParams);
				rawParams = "";
			}
			paramCount++;
		}

		return hm;

	}

	public void validateAndExecuteCommand() {

		boolean commandFound = false;
		String paramsRaw = "";

		for (IAction ia : commands) {
			// if (command.startsWith(ia.getCommandName())) {
			// ia.doAction(command.substring(ia.getCommandName().length()+1));
			// }

			if (command.toUpperCase().startsWith(
					ia.getCommandName().toUpperCase())
					&& !commandFound) {

				// removes all whitespaces out off the string
				paramsRaw = command.substring(ia.getCommandName().length())
						.replaceAll(" ", "");

				// parses the raw commandString and evaluates the params,
				// returns hashmap
				// executes the command
				ia.doAction(parseParams(paramsRaw));

				commandFound = true;
			}

			// if (Pattern.compile(Pattern.quote(ia.getCommandName()),
			// Pattern.CASE_INSENSITIVE).matcher(ia.getCommandName()).find() &&
			// ia.getCommandName().length() >= ia.getCommandName().length()+1) {
			// }

		}

		if (!commandFound) {
			InGameLogger.getInstance().addMessage("Unknown command!");
		}

	}

	public void controlInputString() {
		if (allowedChars.indexOf(input) > -1)
			command += input;
	}

	public void deactivateAndReset() {
		rendering = false;
		isOpen = false;
		command = "";
		input = "";
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public List<IAction> getCommands() {
		return commands;
	}

	public void log(Screen screen) {
		if (command.length() >= 0) {
			screen.draw(consoleBackground, 0, 0);
			rendering = true;
			if (rendering) {
				screen.draw(">" + command + "_", 1, 2, 0xFFFFFF);
			}
		}
	}
}
