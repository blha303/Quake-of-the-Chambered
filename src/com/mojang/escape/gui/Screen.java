package com.mojang.escape.gui;

import java.util.Random;

import com.mojang.escape.Art;
import com.mojang.escape.EscapeComponent;
import com.mojang.escape.Game;
import com.mojang.escape.entities.Item;
import com.mojang.escape.level.block.Block;

import de.decgod.mod.InGameLogger;
import de.decgod.mod.Scene;

public class Screen extends Bitmap
{

	private static final int PANEL_HEIGHT = 29;
	private Bitmap testBitmap;
	private Bitmap3D viewport;
	private int vpxo, vpyo;

	public Screen(int width, int height)
	{
		super(width, height);

		viewport = new Bitmap3D(width, height);

		Random random = new Random();

		testBitmap = new Bitmap(64, 64);

		for (int i = 0; i < 64 * 64; i++)
			testBitmap.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
	}

	public void render(Game game, boolean hasFocus)
	{
		if (game.level == null)
		{
			fill(0, 0, width, height, 0);
		} else
		{
			boolean itemUsed = Scene.getInstance().getPlayer().itemUseTime > 0;
			Item item = Scene.getInstance().getPlayer().items[Scene.getInstance().getPlayer().selectedSlot];

			if (game.pauseTime > 0)
				drawLevelSwitch(game);
			else
			{

				// renders the viewport
				viewport.render(game);

				// renders the postProcessing-layer
				viewport.postProcess(game.level);

				Block block = game.level.getBlock((int) (Scene.getInstance().getPlayer().x + 0.5), (int) (Scene.getInstance().getPlayer().z + 0.5));

				if (block.messages != null && hasFocus)
				{
					for (int y = 0; y < block.messages.length; y++)
					{
						viewport.draw(block.messages[y], (width - block.messages[y].length() * 6) / 2, (viewport.height - block.messages.length * 12) - 64 + y * 12 + 1, Art.getCol(0x000000));
						viewport.draw(block.messages[y], (width - block.messages[y].length() * 6) / 2, (viewport.height - block.messages.length * 12) - 64 + y * 12, Art.getCol(0xFFFFFF));
					}
				}

				draw(viewport, vpxo, vpyo);

				int xx = (int) (Scene.getInstance().getPlayer().turnBob * 32);
				int yy = (int) (Math.sin(Scene.getInstance().getPlayer().bobPhase * 0.1) * 1 * Scene.getInstance().getPlayer().bob + Scene.getInstance().getPlayer().bob * 2);

				if (itemUsed)
					xx = yy = 0;

				xx += width / 2 - (15 * 2);
				yy += height - 15 * 5;

				drawWeapons(itemUsed, item, xx, yy);

				if (Scene.getInstance().getPlayer().hurtTime > 0 || Scene.getInstance().getPlayer().dead)
					drawDeadMessage();
			}

			draw(EscapeComponent.frames + "", 0, 0, 0xFFFFFF);

			drawHud(item);

			int seconds = game.player.time / 60;
			int minutes = seconds / 60;

			seconds %= 60;

			String timeString = minutes + ":";

			if (seconds < 10)
				timeString += "0";

			timeString += seconds;

			draw(timeString, 0, 4, 0xFFFFFF, 2);
		}

		if (game.menu != null)
			drawMenu(game);

		if (!hasFocus)
			drawNotFocused();
	}

	private void drawLevelSwitch(Game game)
	{
		fill(0, 0, width, height, 0);
		
		String[] messages = { "Entering " + game.level.name, };
		
		for (int y = 0; y < messages.length; y++)
		{
			draw(messages[y], (width - messages[y].length() * 6) / 2, (viewport.height - messages.length * 8) / 2 + y * 8 + 1, Art.getCol(0x000000));
			draw(messages[y], (width - messages[y].length() * 6) / 2, (viewport.height - messages.length * 8) / 2 + y * 8, Art.getCol(0xFFFFFF));
		}
	}

	private void drawMenu(Game game)
	{
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = (pixels[i] & 0xfcfcfc) >> 2;
		
		game.menu.render(this);
	}

	private void drawNotFocused()
	{
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = (pixels[i] & 0xfcfcfc) >> 2;
		}
		if (System.currentTimeMillis() / 450 % 2 != 0)
		{
			String msg = "Click to focus!";
			draw(msg, (width - msg.length() * 6) / 2, height / 3 + 4, 0xffffff);
		}
	}

	private void drawHud(Item item)
	{
		// draws the panel, hud background
		// draw(Art.panel, 0, height - PANEL_HEIGHT, 0, 0, width,PANEL_HEIGHT,
		// Art.getCol(0x707070));

		// draws special characters such as keys, hearts, etc. and the amount of
		// it
		// draw("K:", 3, height - 26 + 0, 0x00ffff, 1);
		// draw("" + Scene.getInstance().getPlayer().keys, 10, height - 26 + 0,
		// 0xffffff);
		draw("G:", 0, 16, 0xffff00, 2);
		draw("" + Scene.getInstance().getPlayer().gold, 24, 16, 0xffffff, 2);
		draw("H:", 0, 16 + 8, 0xff0000, 2);
		draw("" + Scene.getInstance().getPlayer().health, 24, 16 + 8, 0xffffff, 2);

		//scaleDraw(Art.playerhud, 3, 0, height - Art.playerhud.height * 3, 0, 0, 16, 16, Art.getCol(0x6B5237));

		// draws items in hud
		// for (int i = 0; i < 8; i++) {
		// Item slotItem = Scene.getInstance().getPlayer().items[i];
		// if (slotItem != Item.none) {
		// draw(Art.items, 30 + i * 32, height - PANEL_HEIGHT + 2,
		// slotItem.icon * 32, 0, 32, 32,
		// Art.getCol(slotItem.color));
		// if (slotItem == Item.pistol) {
		// String str = "" + Scene.getInstance().getPlayer().ammo;
		// draw(str, 30 + i * 32 + 17 - str.length() * 6, height
		// - PANEL_HEIGHT + 1 + 10, 0x555555);
		// }
		// if (slotItem == Item.potion) {
		// String str = "" + Scene.getInstance().getPlayer().potions;
		// draw(str, 30 + i * 32 + 17 - str.length() * 6, height
		// - PANEL_HEIGHT + 1 + 10, 0x555555);
		// }
		// if (slotItem == Item.medikit) {
		// String str = "" + Scene.getInstance().getPlayer().getMedikits();
		// draw(str, 30 + i * 32 + 17 - str.length() * 6, height
		// - PANEL_HEIGHT + 1 + 10, 0x555555);
		// }
		// }
		// }

		// draws the rectangle around the selected item
		// draw(Art.items, 30 + Scene.getInstance().getPlayer().selectedSlot *
		// 16, height - PANEL_HEIGHT + 2, 0, 48, 17, 17, Art.getCol(0xffffff));

		// draws the itemname
		// draw(item.name, 26 + (8 * 16 - item.name.length() * 4) / 2,height -
		// 9, 0xffffff);

		// Ninjadamage checks if bash is open, if
		if (Scene.getInstance().getPlayer().getBash().isOpen())
		{
			Scene.getInstance().getPlayer().getBash().log(this);
		}

		InGameLogger.getInstance().log(this);
	}

	private void drawDeadMessage()
	{
		double offs = 1.5 - Scene.getInstance().getPlayer().hurtTime / 30.0;
		Random random = new Random(111);

		if (Scene.getInstance().getPlayer().dead)
			offs = 0.5;

		for (int i = 0; i < pixels.length; i++)
		{
			double xp = ((i % width) - viewport.width / 2.0) / width * 2;
			double yp = ((i / width) - viewport.height / 2.0) / viewport.height * 2;

			if (random.nextDouble() + offs < Math.sqrt(xp * xp + yp * yp))
				pixels[i] = (random.nextInt(5) / 4) * 0x550000;
		}
	}

	private void drawWeapons(boolean itemUsed, Item item, int xx, int yy)
	{
		if (item != Item.none)
		{
			// scaleDraw(Art.items, 1, xx - 15 * 2, yy - 15,
			// 32 * 2 * item.icon + 1, 32 * 2 + 1 * 2
			// + (itemUsed ? 32 * 2 : 0), 30 * 2, 30 * 2);
			// if (item != Item.none) {
			scaleDraw(Art.items, 5, xx, yy, 16 * item.icon + 1, 16 + 1 + (itemUsed ? 16 : 0), 15, 15, Art.getCol(item.color));
			// }
		}
	}

}