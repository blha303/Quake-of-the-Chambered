package com.mojang.escape.level.block;

import com.mojang.escape.gui.Sprite;

public class StarBlock extends Block
{
	private Sprite[] sprites = new Sprite[4];
	private int steps = 0;
	
	public StarBlock()
	{
		blocksMotion = true;

		for (int i = 0; i < sprites.length; i++)
		{
			sprites[i] = new Sprite(0, i, 0, 13, 0xCCCCCC - i * 2);

			addSprite(sprites[i]);
		}
		
		floorTex = ceilTex = 7;
		floorCol = ceilCol = -1;
		
		col = 0xCCCCCC;
	}
	
	public void tick()
	{
		steps--;
		
		if (steps <= 0)
		{
			for (int i = 0; i < sprites.length; i++)
				sprites[i].tex = 13 + random.nextInt(2);
				
			steps = 16;
		}
	}
}