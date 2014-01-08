package com.mojang.escape.level.block;

import com.mojang.escape.entities.Entity;
import com.mojang.escape.entities.Player;

public class WinBlock extends Block {
	@Override
	public void addEntity(Entity entity) {
		super.addEntity(entity);
		if (entity instanceof Player) {
			((Player)entity).win();
		}
	}
}
