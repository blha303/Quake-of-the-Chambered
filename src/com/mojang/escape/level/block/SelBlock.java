package com.mojang.escape.level.block;

import com.mojang.escape.Art;
import com.mojang.escape.gui.Sprite;

public class SelBlock extends Block
{
	private Sprite sprite;
	private int hoverTime;
	
	public SelBlock()
	{
		sprite = new Sprite(0.2, 1.5, 0.6, 26, Art.getCol(0xFF6464));
		
		addSprite(sprite);
	}
	
	public void tick()
	{
		super.tick();
		
		hoverTime++;
		
		sprite.y = 0.90 + (Math.sin(hoverTime / 50.0) / 2);
	}
}