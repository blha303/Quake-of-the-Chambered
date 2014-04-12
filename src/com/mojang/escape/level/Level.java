package com.mojang.escape.level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.mojang.escape.Art;
import com.mojang.escape.Game;
import com.mojang.escape.entities.BatBossEntity;
import com.mojang.escape.entities.BatEntity;
import com.mojang.escape.entities.BossOgre;
import com.mojang.escape.entities.BoulderEntity;
import com.mojang.escape.entities.Entity;
import com.mojang.escape.entities.EyeBossEntity;
import com.mojang.escape.entities.EyeEntity;
import com.mojang.escape.entities.GhostBossEntity;
import com.mojang.escape.entities.GhostEntity;
import com.mojang.escape.entities.Item;
import com.mojang.escape.entities.KeyEntity;
import com.mojang.escape.entities.Medikit;
import com.mojang.escape.entities.OgreEntity;
import com.mojang.escape.entities.Player;
import com.mojang.escape.gui.Sprite;
import com.mojang.escape.level.block.AltarBlock;
import com.mojang.escape.level.block.BarsBlock;
import com.mojang.escape.level.block.Block;
import com.mojang.escape.level.block.ChestBlock;
import com.mojang.escape.level.block.DoorBlock;
import com.mojang.escape.level.block.ElevatorBlock;
import com.mojang.escape.level.block.FinalUnlockBlock;
import com.mojang.escape.level.block.IceBlock;
import com.mojang.escape.level.block.LadderBlock;
import com.mojang.escape.level.block.LavaBlock;
import com.mojang.escape.level.block.LockedDoorBlock;
import com.mojang.escape.level.block.LootBlock;
import com.mojang.escape.level.block.PitBlock;
import com.mojang.escape.level.block.PressurePlateBlock;
import com.mojang.escape.level.block.SelBlock;
import com.mojang.escape.level.block.SolidBlock;
import com.mojang.escape.level.block.SpiritWallBlock;
import com.mojang.escape.level.block.StarBlock;
import com.mojang.escape.level.block.SwitchBlock;
import com.mojang.escape.level.block.TorchBlock;
import com.mojang.escape.level.block.VanishBlock;
import com.mojang.escape.level.block.WaterBlock;
import com.mojang.escape.level.block.WinBlock;
import com.mojang.escape.menu.GotLootMenu;

import de.decgod.mod.Scene;

public abstract class Level
{
	public Block[] blocks;
	public int width, height;
	private Block solidWall = new SolidBlock();

	public int xSpawn;
	public int ySpawn;

	protected int wallCol = 0x9B6D47;
	protected int floorCol = 0x68492A;
	protected int ceilCol = 0x5B4736;

	protected int wallTex = 0;
	protected int floorTex = 0;
	protected int ceilTex = 0;

	public List<Entity> entities = new ArrayList<Entity>();

	protected Game game;
	public String name = "";

	public Player player;

	public void init(Game game, String name, int w, int h, int[] pixels)
	{
		this.game = game;

		player = Scene.getInstance().getPlayer();

		solidWall.col = Art.getCol(wallCol);
		solidWall.tex = Art.getCol(wallTex);
		
		this.width = w;
		this.height = h;
		
		blocks = new Block[width * height];

		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				int col = pixels[x + y * w] & 0xffffff;
				int id = 255 - ((pixels[x + y * w] >> 24) & 0xff);

				Block block = getBlock(x, y, col);
				block.id = id;

				if (block.tex == -1)
					block.tex = wallTex;
				if (block.floorTex == -1)
					block.floorTex = floorTex;
				if (block.ceilTex == -1)
					block.ceilTex = ceilTex;
				if (block.col == -1)
					block.col = Art.getCol(wallCol);
				if (block.floorCol == -1)
					block.floorCol = Art.getCol(floorCol);
				if (block.ceilCol == -1)
					block.ceilCol = Art.getCol(ceilCol);

				blocks[x + y * w] = block;
				block.level = this;
				block.x = x;
				block.y = y;
			}
		}

		for (int y = 0; y < h; y++)
		{
			for (int x = 0; x < w; x++)
			{
				int col = pixels[x + y * w] & 0xffffff;
				decorateBlock(x, y, blocks[x + y * w], col);
			}
		}
	}

	public void addEntity(Entity e)
	{
		entities.add(e);
		
		e.level = this;
		
		e.updatePos();
	}

	public void removeEntityImmediately(Player player)
	{
		entities.remove(player);
		
		getBlock(player.xTileO, player.zTileO).removeEntity(player);
	}

	protected void decorateBlock(int x, int y, Block block, int col)
	{
		block.decorate(this, x, y);
		
		switch (col)
		{
		case 0xFFFF00:
			xSpawn = x;
			ySpawn = y;
			
			break;
		case 0xAA5500:
			addEntity(new BoulderEntity(x, y));
			
			break;
		case 0xF3F300:
			addEntity(new Medikit(x, y));
			
			break;
		case 0xFF0000:
			addEntity(new BatEntity(x, y));
			
			break;
		case 0xFF0001:
			addEntity(new BatBossEntity(x, y));
			
			break;
		case 0xFF0002:
			addEntity(new OgreEntity(x, y));
			
			break;
		case 0xFF0003:
			addEntity(new BossOgre(x, y));
			
			break;
		case 0xFF0004:
			addEntity(new EyeEntity(x, y));
			
			break;
		case 0xFF0005:
			addEntity(new EyeBossEntity(x, y));
			
			break;
		case 0xFF0006:
			addEntity(new GhostEntity(x, y));
			
			break;
		case 0xFF0007:
			addEntity(new GhostBossEntity(x, y));
			
			break;
		case 0xFF9834:
			addEntity(new KeyEntity(x, y));
			
			break;
		case 0x1A2108:
			block.floorTex = 7;
			block.ceilTex = 7;
			
			break;
		case 0xC6C6C6 | 0xC6C697:
			block.col = Art.getCol(0xA0A0A0);
			
			break;
		case 0x653A00:
			block.floorCol = Art.getCol(0xB56600);
			block.floorTex = 3 * 8 + 1;
			
			break;
		case 0x93FF9B:
			block.col = Art.getCol(0x2AAF33);
			block.tex = 8;
			
			break;
		case 0xE6D8BE:
			block.addSprite(new Sprite(0, 2, 0, 6, Art.getCol(0xE6D8BE)));
			block.addSprite(new Sprite(0, 0, 0, 7, Art.getCol(0xE6D8BE)));
			
			break;
		case 0xE1A186:
			block.col = Art.getCol(0x000000);
			block.solidRender = true;
			block.blocksMotion = true;
			
			break;
		case 0xF982AE:
			block.messages = new String[1];
			block.messages[0] = "THIS HALL SELECTS EASY SKILL.";
			block.floorTex = 5;
			block.floorCol = Art.getCol(0x5B4736);
			block.setFloorHeight(player, 0.1);
			
			break;
		case 0xF982FF:
			block.messages = new String[1];
			block.messages[0] = "THIS HALL SELECTS NORMAL SKILL.";
			block.floorTex = 5;
			block.floorCol = Art.getCol(0x5B4736);
			block.setFloorHeight(player, 0.1);
			
			break;
		case 0xFF82FF:
			block.messages = new String[2];
			block.messages[0] = "THIS HALL SELECTS HARD SKILL.";
			block.messages[1] = "BE PREPARED.";
			block.floorTex = 5;
			block.floorCol = Art.getCol(0x5B4736);
			block.setFloorHeight(player, 0.1);
			
			break;
		case 0x0032FF:
			block.messages = new String[3];
			block.messages[0] = "THIS PASSAGE SELECTS 'NIGHTMARE' SKILL.";
			block.messages[1] = "THIS DIFFICULTY IS NOT EVEN REMOTELY FAIR.";
			block.messages[2] = "YOU HAVE BEEN WARNED.";
			block.floorTex = 5;
			block.floorCol = Art.getCol(0x5B4736);
			block.setFloorHeight(player, 0.1);
			
			break;
		case 0xFFB27F:
			block.messages = new String[4];
			block.messages[0] = "SELECT YOUR DIFFICULTY.";
			block.messages[1] = "LEFT - EASY";
			block.messages[2] = "MIDDLE - NORMAL";
			block.messages[3] = "RIGHT - HARD";
			block.floorTex = 5;
			block.floorCol = Art.getCol(0x5B4736);
			block.setFloorHeight(player, 0.1);
			
			break;
		case 0xFFD8BE:
			block.addSprite(new Sprite(0, 0, 0, 15, Art.getCol(0xBFA982)));
			block.setFloorHeight(player, 0.6);
			
			break;
		case 0xF92EA4:
			block.floorTex = 5;
			block.floorCol = Art.getCol(0x5B4736);
			
			break;
		case 0x33FFFF:
			block.floorTex = 4;
			block.floorCol = Art.getCol(0x99622C);
			
			break;
		case 0x611000:
			block.ceilTex = -1;
			
			break;
		case 0xC0C0C0:
			block.addSprite(new Sprite(0.3, 2, 0.3, 21, Art.getCol(0xCCCCCC)));
			
			break;
		case 0xFF6401:
			block.setFloorHeight(player, 0.5);
			block.addSprite(new Sprite(0, block.getFloorHeight(player) / 2, 0, 3, Art.getCol(0xFF00FF)));

			break;
		case 0xFF6402:
			block.setFloorHeight(player, 0.7);
			block.addSprite(new Sprite(0, block.getFloorHeight(player) / 2, 0, 3, Art.getCol(0xFF00FF)));

			break;
		case 0xFF6403:
			block.setFloorHeight(player, 0.9);
			block.addSprite(new Sprite(0, block.getFloorHeight(player) / 2, 0, 3, Art.getCol(0xFF00FF)));

			break;
		case 0xFF6404:
			block.setFloorHeight(player, 1.1);
			block.addSprite(new Sprite(0, block.getFloorHeight(player) / 2, 0, 3, Art.getCol(0xFF00FF)));

			break;
		case 0xFF6405:
			block.setFloorHeight(player, 1.2);
			block.addSprite(new Sprite(0, block.getFloorHeight(player) / 2, 0, 3, Art.getCol(0xFF00FF)));
			
			break;
		case 0xFF6406:
			block.setFloorHeight(player, 1.3);
			block.addSprite(new Sprite(0, block.getFloorHeight(player) / 2, 0, 3, Art.getCol(0xFF00FF)));
			
			break;
		}
	}

	protected Block getBlock(int x, int y, int col)
	{
		if (col == 0x93FF9B)
			return new SolidBlock();
		if (col == 0x009300)
			return new PitBlock();
		if (col == 0xFFFFFF)
			return new SolidBlock();
		if (col == 0x00FFFF)
			return new VanishBlock();
		if (col == 0xFFFF64)
			return new ChestBlock();
		if (col == 0x0000FF)
			return new WaterBlock();
		if (col == 0xFF3A02)
			return new TorchBlock();
		if (col == 0x4C4C4C)
			return new BarsBlock();
		if (col == 0xFF66FF)
			return new LadderBlock(false);
		if (col == 0x9E009E)
			return new LadderBlock(true);
		if (col == 0xC1C14D)
			return new LootBlock();
		if (col == 0xC6C6C6)
			return new DoorBlock();
		if (col == 0x00FFA7)
			return new SwitchBlock();
		if (col == 0x009380)
			return new PressurePlateBlock();
		if (col == 0xff0005)
			return new IceBlock();
		if (col == 0x3F3F60)
			return new IceBlock();
		if (col == 0xC6C697)
			return new LockedDoorBlock();
		if (col == 0xFFBA02)
			return new AltarBlock();
		if (col == 0x749327)
			return new SpiritWallBlock();
		if (col == 0x1A2108)
			return new Block();
		if (col == 0x00C2A7)
			return new FinalUnlockBlock();
		if (col == 0x000056)
			return new WinBlock();
		if (col == 0xFF6D02 || col == 0x611000)
			return new LavaBlock();
		if (col == 0xF939AE)
			return new StarBlock();
		if (col == 0xF0FFF0)
			return new SelBlock();
		if (col == 0x7F92FF)
			return new ElevatorBlock();
		
		return new Block();
	}

	public Block getBlock(int x, int y)
	{
		if (x < 0 || y < 0 || x >= width || y >= height)
			return solidWall;
		
		return blocks[x + y * width];
	}

	private static Map<String, Level> loaded = new HashMap<String, Level>();

	public static void clear()
	{
		loaded.clear();
	}

	public static Level loadLevel(Game game, String name)
	{
		if (loaded.containsKey(name))
			return loaded.get(name);

		try
		{
			BufferedImage img = ImageIO.read(Level.class.getResource("/level/" + name + ".png"));

			int w = img.getWidth();
			int h = img.getHeight();
			
			int[] pixels = new int[w * h];
			
			img.getRGB(0, 0, w, h, pixels, 0, w);

			Level level = Level.byName(name);
			level.init(game, name, w, h, pixels);
			loaded.put(name, level);

			return level;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private static Level byName(String name)
	{
		try
		{
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			
			return (Level) Class.forName("com.mojang.escape.level." + name + "Level").newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public boolean containsBlockingEntity(double x0, double y0, double x1, double y1)
	{
		int xc = (int) (Math.floor((x1 + x0) / 2));
		int zc = (int) (Math.floor((y1 + y0) / 2));
		int rr = 2;
		for (int z = zc - rr; z <= zc + rr; z++)
		{
			for (int x = xc - rr; x <= xc + rr; x++)
			{
				List<Entity> es = getBlock(x, z).entities;
				for (int i = 0; i < es.size(); i++)
				{
					Entity e = es.get(i);
					if (e.isInside(x0, y0, x1, y1))
						return true;
				}
			}
		}
		return false;
	}

	public boolean containsBlockingNonFlyingEntity(double x0, double y0, double x1, double y1)
	{
		int xc = (int) (Math.floor((x1 + x0) / 2));
		int zc = (int) (Math.floor((y1 + y0) / 2));
		int rr = 2;
		for (int z = zc - rr; z <= zc + rr; z++)
		{
			for (int x = xc - rr; x <= xc + rr; x++)
			{
				List<Entity> es = getBlock(x, z).entities;
				for (int i = 0; i < es.size(); i++)
				{
					Entity e = es.get(i);
					if (!e.flying && e.isInside(x0, y0, x1, y1))
						return true;
				}
			}
		}
		return false;
	}

	public void tick()
	{
		for (int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.tick();
			e.updatePos();
			if (e.isRemoved())
			{
				entities.remove(i--);
			}
		}

		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				blocks[x + y * width].tick();
			}
		}
	}

	public void trigger(int id, boolean pressed)
	{
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				Block b = blocks[x + y * width];
				if (b.id == id)
				{
					b.trigger(pressed);
				}
			}
		}
	}

	public void switchLevel(int id)
	{
	}

	public void findSpawn(int id)
	{
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				Block b = blocks[x + y * width];
				
				if (b.id == id && b instanceof LadderBlock)
				{
					xSpawn = x;
					ySpawn = y;
				}
			}
		}
	}

	public void getLoot(int id)
	{
		if (id == 20)
			game.getLoot(Item.pistol);
		if (id == 21)
			game.getLoot(Item.potion);
	}

	public void win()
	{
		game.win(player);
	}

	public void lose()
	{
		game.lose(player);
	}

	public void showLootScreen(Item item)
	{
		game.setMenu(new GotLootMenu(item));
	}
}