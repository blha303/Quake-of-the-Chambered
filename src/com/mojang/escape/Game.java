package com.mojang.escape;

import java.awt.event.KeyEvent;

import com.mojang.escape.entities.Item;
import com.mojang.escape.entities.Player;
import com.mojang.escape.level.Level;
import com.mojang.escape.level.block.LadderBlock;
import com.mojang.escape.menu.LoseMenu;
import com.mojang.escape.menu.Menu;
import com.mojang.escape.menu.PauseMenu;
import com.mojang.escape.menu.TitleMenu;
import com.mojang.escape.menu.WinMenu;

import de.decgod.mod.Scene;

public class Game
{

	public Level level;
	public static Player player = new Player();
	public int pauseTime;

	public Menu menu;
	public int time;

	public Game()
	{
		setMenu(new TitleMenu());
		Scene.registerComp(level, this, player);
	}

	public void newGame()
	{
		Level.clear();
		level = Level.loadLevel(this, "select");
		player = new Player();
		player.setHealth(20);
		player.level = level;
		level.player = player;
		player.x = level.xSpawn;
		player.z = level.ySpawn;
		level.addEntity(player);
		player.rot = Math.PI + 0.4;
	}

	public void switchLevel(String name, int id)
	{
		pauseTime = 30;
		level.removeEntityImmediately(player);
		level = Level.loadLevel(this, name);
		level.findSpawn(id);
		player.x = level.xSpawn;
		player.z = level.ySpawn;
		((LadderBlock) level.getBlock(level.xSpawn, level.ySpawn)).wait = true;
		player.x += Math.sin(player.rot) * 0.2;
		player.z += Math.cos(player.rot) * 0.2;
		level.addEntity(player);
	}

	public void tick(boolean[] keys)
	{
		time++;
		
		if (pauseTime > 0)
		{
			pauseTime--;

			return;
		}

		boolean strafe = keys[KeyEvent.VK_CONTROL] || keys[KeyEvent.VK_ALT] || keys[KeyEvent.VK_ALT_GRAPH] || keys[KeyEvent.VK_SHIFT];

		boolean lk = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_NUMPAD4];
		boolean rk = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_NUMPAD6];

		boolean up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_NUMPAD8];
		boolean down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_NUMPAD2];
		boolean left = keys[KeyEvent.VK_A] || (strafe && lk);
		boolean right = keys[KeyEvent.VK_D] || (strafe && rk);

		boolean turnLeft = keys[KeyEvent.VK_Q] || (!strafe && lk);
		boolean turnRight = keys[KeyEvent.VK_E] || (!strafe && rk);

		boolean use = keys[KeyEvent.VK_SPACE];

		manageItemUse(keys);

		if (keys[130] /* aka cirkumflex */)
		{
			if (!player.getBash().isOpen())
			{
				player.getBash().setOpen(true);
			} 
			else if (player.getBash().isOpen())
			{
				player.getBash().setOpen(false);
			}

			keys[130] = false;
		}

		if (keys[KeyEvent.VK_ESCAPE])
		{
			keys[KeyEvent.VK_ESCAPE] = false;
			if (menu == null)
			{
				setMenu(new PauseMenu());
			}
		}

		if (use)
		{
			keys[KeyEvent.VK_SPACE] = false;
		}

		if (menu != null)
		{
			keys[KeyEvent.VK_W] = keys[KeyEvent.VK_UP] = keys[KeyEvent.VK_NUMPAD8] = false;
			keys[KeyEvent.VK_S] = keys[KeyEvent.VK_DOWN] = keys[KeyEvent.VK_NUMPAD2] = false;
			keys[KeyEvent.VK_A] = false;
			keys[KeyEvent.VK_D] = false;

			menu.tick(this, up, down, left, right, use);
		} else if (!player.getBash().isOpen())
		{
			player.tick(up, down, left, right, turnLeft, turnRight);
			if (use)
			{
				player.activate();
			}

			level.tick();
		}

		Scene.registerComp(level, this, player);
	}

	// Ninjadamage

	/**
	 * refactored method from notch. Controls the item selection in the menu.
	 * 
	 * @param keys
	 *            - Input from the user
	 */
	protected void manageItemUse(boolean[] keys)
	{
		for (int i = 0; i < 8; i++)
		{
			if (keys[KeyEvent.VK_1 + i])
			{
				keys[KeyEvent.VK_1 + i] = false;
				player.selectedSlot = i;
				player.itemUseTime = 0;
			}
		}
	}

	// Ninjadamage

	public void getLoot(Item item)
	{
		player.addLoot(item);
	}

	public void win(Player player)
	{
		setMenu(new WinMenu(player));
	}

	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}

	public void lose(Player player)
	{
		setMenu(new LoseMenu(player));
	}
}
