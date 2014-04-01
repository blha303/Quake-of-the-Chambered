package com.mojang.escape.level.block;

import java.util.Random;

import com.mojang.escape.Art;
import com.mojang.escape.gui.Sprite;

public class StarBlock extends Block
{
	private Sprite[] sprites = new Sprite[4];
	private int steps = 0;
	
	public StarBlock()
	{
		blocksMotion = true;
		//solidRender = true;

		for (int i = 0; i < sprites.length; i++)
		{
			sprites[i] = new Sprite(0, i, 0, 13, Art.getCol(new Random().nextInt()));

			addSprite(sprites[i]);
		}
		
		floorTex = ceilTex = 7;
		
		col = 0xCCCCCC;
	}
	
	public void tick()
	{
		steps--;

		for (int i = 0; i < sprites.length; i++)
			sprites[i].col = new Random().nextInt();
		
		if (steps <= 0)
		{
			for (int i = 0; i < sprites.length; i++)
				sprites[i].tex = 13 + random.nextInt(2);

			for (int i = 0; i < sprites.length; i++)
				sprites[i].col = new Random().nextInt();
				
			steps = 16;
		}
	}
}