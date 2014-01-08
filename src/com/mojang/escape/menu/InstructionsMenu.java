package com.mojang.escape.menu;

import com.mojang.escape.Art;
import com.mojang.escape.Game;
import com.mojang.escape.Sound;
import com.mojang.escape.gui.Bitmap;

import de.decgod.mod.OptionsHandler;

public class InstructionsMenu extends Menu {
	private int tickDelay = 30;

	@Override
	public void render(Bitmap target) {
		target.fill(0, 0, OptionsHandler.getInstance().getWidth(), OptionsHandler.getInstance().getHeight(), 0);

		target.draw("Instructions", 40, 8, Art.getCol(0xffffff));
		
		String[] lines = {
				"Use W,A,S,D to move, and",
				"the arrow keys to turn.",
				"",
				"The 1-8 keys select",
				"items from the inventory",
				"",
				"Space uses items",
		};
		
		for (int i=0; i<lines.length; i++) {
			target.draw(lines[i], 4, 32+i*8, Art.getCol(0xa0a0a0));
		}

		if (tickDelay == 0) target.draw("-> Continue", 40, target.height - 16, Art.getCol(0xffff80));
	}

	@Override
	public void tick(Game game, boolean up, boolean down, boolean left, boolean right, boolean use) {
		if (tickDelay > 0) tickDelay--;
		else if (use) {
			Sound.click1.play();
			game.setMenu(new TitleMenu());
		}
	}
}
