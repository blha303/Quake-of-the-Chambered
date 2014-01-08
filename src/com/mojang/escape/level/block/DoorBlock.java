package com.mojang.escape.level.block;

import com.mojang.escape.Sound;
import com.mojang.escape.entities.Bullet;
import com.mojang.escape.entities.Entity;
import com.mojang.escape.entities.Item;
import com.mojang.escape.entities.OgreEntity;
import com.mojang.escape.entities.Player;
import com.mojang.escape.level.Level;

public class DoorBlock extends SolidBlock {
	public boolean open = false;
	public double openness = 0;

	public DoorBlock() {
		tex = 4;
		solidRender = false;
	}

	@Override
	public boolean use(Level level, Item item) {
		open = !open;
		if (open)
			Sound.click1.play();
		else
			Sound.click2.play();
		return true;
	}

	@Override
	public void tick() {
		super.tick();
		
		if (open) openness += 0.05;
		else openness -= 0.05;
		if (openness < 0) openness = 0;
		if (openness > 1) openness = 1;

		double openLimit = 7 / 8.0;
		if (openness < openLimit && !open && !blocksMotion) {
			if (level.containsBlockingEntity(x - 0.5, y - 0.5, x + 0.5, y + 0.5)) {
				openness = openLimit;
				return;
			}
		}

		blocksMotion = openness < openLimit;
	}

	@Override
	public boolean blocks(Entity entity) {
		double openLimit = 7 / 8.0;
		if (openness >= openLimit && entity instanceof Player) return blocksMotion;
		if (openness >= openLimit && entity instanceof Bullet) return blocksMotion;
		if (openness >= openLimit && entity instanceof OgreEntity) return blocksMotion;
		return true;
	}
}
