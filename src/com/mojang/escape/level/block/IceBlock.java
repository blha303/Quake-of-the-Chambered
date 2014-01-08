package com.mojang.escape.level.block;

import com.mojang.escape.Art;
import com.mojang.escape.entities.Bullet;
import com.mojang.escape.entities.Entity;
import com.mojang.escape.entities.EyeBossEntity;
import com.mojang.escape.entities.EyeEntity;
import com.mojang.escape.entities.Item;
import com.mojang.escape.entities.Player;

public class IceBlock extends Block {
	public IceBlock() {
		blocksMotion = false;
		floorTex = 16;
	}

	@Override
	public void tick() {
		super.tick();
		floorCol = Art.getCol(0x8080ff);
	}

	@Override
	public double getWalkSpeed(Player player) {
		if (player.getSelectedItem() == Item.skates) return 0.05;
		return 1.4;
	}

	@Override
	public double getFriction(Player player) {
		if (player.getSelectedItem() == Item.skates) return 0.98;
		return 1;
	}

	@Override
	public boolean blocks(Entity entity) {
		if (entity instanceof Player) return false;
		if (entity instanceof Bullet) return false;
		if (entity instanceof EyeBossEntity) return false;
		if (entity instanceof EyeEntity) return false;
		return true;
	}
}
