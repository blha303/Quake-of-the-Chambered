package com.mojang.escape.level.block;

import com.mojang.escape.entities.Item;
import com.mojang.escape.level.Level;

public class LockedDoorBlock extends DoorBlock {
	public LockedDoorBlock() {
		tex = 5;
	}

	@Override
	public boolean use(Level level, Item item) {
		return false;
	}

	@Override
	public void trigger(boolean pressed) {
		open = pressed;
	}

}
