package com.mojang.escape.menu;

import com.mojang.escape.Art;
import com.mojang.escape.Game;
import com.mojang.escape.Sound;
import com.mojang.escape.gui.Bitmap;

import de.decgod.mod.RuntimeConfiguration;

public class TitleMenu extends Menu
{
	private String[] options = { "Singleplayer", "Multiplayer", "Options", "Help", "Quit" };
	private int selected = 0;
	private boolean firstTick = true;

	@Override
	public void render(Bitmap target)
	{

		target.fill(0, 0, RuntimeConfiguration.getInstance().getWidth(), RuntimeConfiguration.getInstance().getHeight(), RuntimeConfiguration.getInstance().getTheme().black);
		// target.draw(Art.logo, 80, 8, 0, 0,
		// RuntimeConfiguration.getInstance().getWidth()/2, 36);

		target.draw("Quake of the", 32, 8 + 1, Art.getCol(0x545454), 3);
		target.draw("Quake of the", 32, 8, Art.getCol(0xFFFF64), 3);

		target.draw("Chambered", 32, 16 + 1, Art.getCol(0x545454), 4);
		target.draw("Chambered", 32, 16, Art.getCol(0xFFFF64), 4);

		for (int i = 0; i < options.length; i++)
		{
			String msg = options[i];
			
			int col = RuntimeConfiguration.getInstance().getTheme().inactiveText;
			
			if (selected == i)
				col = RuntimeConfiguration.getInstance().getTheme().activeText;
			
			target.draw(msg, 32, 128 + i * 10, Art.getCol(col));
		}
	}

	@Override
	public void tick(Game game, boolean up, boolean down, boolean left, boolean right, boolean use)
	{
		if (firstTick)
		{
			firstTick = false;
			
			Sound.altar.play();
		}
		
		if (up || down)
			Sound.click2.play();
		
		if (up)
			selected--;
		
		if (down)
			selected++;
		
		if (selected < 0)
			selected = 0;
		
		if (selected >= options.length)
			selected = options.length - 1;
		if (use)
		{
			Sound.click1.play();

			if (selected == 0)
			{
				game.setMenu(null);
				game.newGame();
			}

			if (selected == options.length - 1)
				System.exit(0);
		}
	}
}