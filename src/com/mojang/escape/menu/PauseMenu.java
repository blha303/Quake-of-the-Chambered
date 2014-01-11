package com.mojang.escape.menu;

import com.mojang.escape.Art;
import com.mojang.escape.Game;
import com.mojang.escape.Sound;
import com.mojang.escape.gui.Bitmap;

import de.decgod.mod.RuntimeConfiguration;

public class PauseMenu extends Menu {
	private String[] options = {"Continue", "Abort game" };
	private int selected = 0;

	@Override
	public void render(Bitmap target) {
		target.draw(Art.logo, 0, 8, 0, 0, RuntimeConfiguration.getInstance().getWidth(), 36, Art.getCol(RuntimeConfiguration.getInstance().getTheme().white));

		for (int i = 0; i < options.length; i++) {
			String msg = options[i];
			int col = RuntimeConfiguration.getInstance().getTheme().inactiveText;
			if (selected == i) {
				col = RuntimeConfiguration.getInstance().getTheme().activeText;
			}
			target.draw(msg, 40, 60 + i * 10, Art.getCol(col));
		}
	}

	@Override
	public void tick(Game game, boolean up, boolean down, boolean left, boolean right, boolean use) {
		if (up || down) Sound.click2.play();
		if (up) selected--;
		if (down) selected++;
		if (selected < 0) selected = 0;
		if (selected >= options.length) selected = options.length - 1;
		if (use) {
			Sound.click1.play();
			if (selected == 0) {
                game.setMenu(null);
            }
			if (selected == 1) {
                game.setMenu(new TitleMenu());
            }
		}
	}
}
