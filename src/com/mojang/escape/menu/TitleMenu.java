package com.mojang.escape.menu;

import com.mojang.escape.Art;
import com.mojang.escape.Game;
import com.mojang.escape.Sound;
import com.mojang.escape.gui.Bitmap;

import de.decgod.mod.OptionsHandler;
import de.decgod.mod.Scene;

public class TitleMenu extends Menu {
	private String[] options = { "New game", "Instructions", "About" };
	private int selected = 0;
	private boolean firstTick = true;

	@Override
	public void render(Bitmap target) {
		
		target.fill(0, 0, OptionsHandler.getInstance().getWidth(), OptionsHandler.getInstance().getHeight(),0x000000);
		target.draw(Art.logo, 80, 8, 0, 0, OptionsHandler.getInstance().getWidth()/2, 36);
		
		for (int i = 0; i < options.length; i++) {
			String msg = options[i];
			int col = 0xEEEEEE;
			if (selected == i) {
				col = 0x00b4df;
			}
			target.draw(msg, (OptionsHandler.getInstance().getWidth()/2)-(60), (OptionsHandler.getInstance().getHeight()/2) + i * 10, Art.getCol(col));
		}
	}

	@Override
	public void tick(Game game, boolean up, boolean down, boolean left, boolean right, boolean use) {
		if (firstTick) {
			firstTick = false;
			Sound.altar.play();
		}
		if (up || down) Sound.click2.play();
		if (up) selected--;
		if (down) selected++;
		if (selected < 0) selected = 0;
		if (selected >= options.length) selected = options.length - 1;
		if (use) {
			Sound.click1.play();
			if (selected == 0) {
				game.setMenu(null);
				game.newGame();
			}
			if (selected == 1) {
				game.setMenu(new InstructionsMenu());
			}
			if (selected == 2) {
				game.setMenu(new AboutMenu());
			}
		}
	}
}