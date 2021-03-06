package com.mojang.escape.level.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mojang.escape.entities.Entity;
import com.mojang.escape.entities.Item;
import com.mojang.escape.entities.Player;
import com.mojang.escape.gui.Sprite;
import com.mojang.escape.level.Level;

public class Block
{
	protected static Random random = new Random();

	public boolean blocksMotion = false;
	public boolean solidRender = false;

	public String[] messages = {  };

	public static Block solidWall = new SolidBlock();

	public List<Sprite> sprites = new ArrayList<Sprite>();
	public List<Entity> entities = new ArrayList<Entity>();

	public int tex = -1;
	public int col = -1;

	public int floorCol = -1;
	public int ceilCol = -1;

	public int floorTex = -1;
	public int ceilTex = -1;

	public Level level;
	public int x, y;

	public int id;

	public void addSprite(Sprite sprite)
	{
		sprites.add(sprite);
	}

	public boolean use(Level level, Item item)
	{
		return false;
	}

	public void tick()
	{
		for (int i = 0; i < sprites.size(); i++)
		{
			Sprite sprite = sprites.get(i);
			
			sprite.tick();
			
			if (sprite.removed)
				sprites.remove(i--);
		}
	}

	public void removeEntity(Entity entity)
	{
		entities.remove(entity);
	}

	public void addEntity(Entity entity)
	{
		entities.add(entity);
	}

	public boolean blocks(Entity entity)
	{
		return blocksMotion;
	}

	public void decorate(Level level, int x, int y)
	{
		
	}

	private double floorHeight = 0.20;
	
	public double getFloorHeight(Entity e)
	{
		return this.floorHeight;
	}

	public double getWalkSpeed(Player player)
	{
		return 1;
	}

	public double getFriction(Player player)
	{
		return 0.8;
	}
	
	public void setFloorHeight(Entity e, double floorHeight)
	{
		this.floorHeight = floorHeight;
	}

	public void trigger(boolean pressed)
	{
	}
}
