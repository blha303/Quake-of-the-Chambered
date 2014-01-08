package com.mojang.escape.level.block;

import com.mojang.escape.Sound;
import com.mojang.escape.entities.BoulderEntity;
import com.mojang.escape.entities.Entity;
import com.mojang.escape.gui.Sprite;

public class PitBlock extends Block {
	private boolean filled = false;

	public PitBlock() {
		floorTex = 1;
		blocksMotion = true;
	}

	@Override
	public void addEntity(Entity entity) {
		super.addEntity(entity);
		if (!filled && entity instanceof BoulderEntity) {
			entity.remove();
			filled = true;
			blocksMotion = false;
			addSprite(new Sprite(0, 0, 0, 8 + 2, BoulderEntity.COLOR));
			Sound.thud.play();
		}
	}

	@Override
	public boolean blocks(Entity entity) {
		if (entity instanceof BoulderEntity) return false;
		return blocksMotion;
	}
}
