package com.mojang.escape.entities;

import com.mojang.escape.Art;
import com.mojang.escape.Sound;
import com.mojang.escape.gui.Sprite;

import de.decgod.mod.Scene;

public class Medikit extends Entity {
	public static final int COLOR = Art.getCol(0xffffff);
	private Sprite sprite;
	private double y, ya;

	public Medikit(double x, double z) {
		this.x = x;
		this.z = z;
		y = 0.5;
		ya = 0.025;
		sprite = new Sprite(0, 0, 0, 40, COLOR);
		sprites.add(sprite);
	}

	@Override
	public void tick() {
		move();
		y += ya;
		if (y < 0) y = 0;
		ya -= 0.005;
		sprite.y = y;
	}

	@Override
	protected void collide(Entity entity) {
		if (entity instanceof Player) {
			Sound.key.play();
			Scene.getInstance().getPlayer().health+=5;
			remove();
		}
	}
}
