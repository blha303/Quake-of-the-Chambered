package com.mojang.escape.level.block;

import com.mojang.escape.Art;
import com.mojang.escape.gui.Sprite;

public class ElevatorBlock extends Block
{
	public ElevatorBlock()
	{
		solidRender = false;
		blocksMotion = false;
		
		addSprite(new Sprite(0, 0, 0, 5, Art.getCol(0xFFFF00)));
	}
}
