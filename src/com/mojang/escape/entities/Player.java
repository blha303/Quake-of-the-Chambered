package com.mojang.escape.entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.mojang.escape.Sound;
import com.mojang.escape.level.block.Block;
import com.mojang.escape.level.block.IceBlock;
import com.mojang.escape.level.block.LavaBlock;
import com.mojang.escape.level.block.WaterBlock;

import de.decgod.bash.Bash;
import de.decgod.mod.RuntimeConfiguration;

public class Player extends Entity
{

	public double bob, bobPhase, turnBob;
	public int selectedSlot = 0;
	public int itemUseTime;
	public double y, ya;
	public int hurtTime = 0;
	public int health = 0;
	public int keys = 0;
	public int gold = 0;
	public static boolean dead = false;
	private int deadTime = 0;
	public int ammo = 0;
	public int potions = 0;
	private Block lastBlock;
	boolean sliding = false;
	public int time;
	double rotSpeed = 0;
	public Item[] items = new Item[8];

	private double walkSpeed = 0.3;
	private String playername = RuntimeConfiguration.getInstance().getPlayername();
	private Bash bash = new Bash();
	private Point mouse = new Point(0, 0);
	double walkspeedExtra = 0.025;
	private int medikits = 0;

	public String getPosition()
	{
		return "X Tile: " + xTileO + ", Z Tile: " + zTileO;
	}

	public void setMouse(Point mouse)
	{
		this.mouse = mouse;
	}

	public Point getMouse()
	{
		return mouse;
	}

	public Player()
	{
		r = 0.3;
		
		for (int i = 0; i < items.length; i++)
			items[i] = Item.none;
		
		items[1] = Item.pistol;
		items[0] = Item.powerglove;
		
		ammo = 100;
	}

	public void turnLeft(double variation)
	{
		rotSpeed = (-variation / 500);
		rota += rotSpeed;
	}

	public void turnRight(double variation)
	{
		rotSpeed = (variation / 500);
		rota -= rotSpeed;
	}

	/* * * * * *
	 * getter& * * setter * * * * * *
	 */

	public void setWalkspeedExtra(double walkspeedExtra)
	{
		this.walkspeedExtra = walkspeedExtra;
	}

	public double getWalkspeedExtra()
	{
		return walkspeedExtra;
	}

	public Bash getBash()
	{
		return bash;
	}

	public String getPlayername()
	{
		return playername;
	}

	public void setPlayername(String playername)
	{
		this.playername = playername;
	}

	public void setHealth(int health)
	{
		this.health = health;
	}

	public int getHealth()
	{
		return health;
	}

	public void setMedikits(int medikits)
	{
		this.medikits = medikits;
	}

	public int getMedikits()
	{
		return medikits;
	}

	public void setBobPhase(double bobPhase)
	{
		this.bobPhase = bobPhase;
	}

	public double getBobPhase()
	{
		return bobPhase;
	}

	// NinjadamageMod

	public void tick(boolean up, boolean down, boolean left, boolean right, boolean turnLeft, boolean turnRight)
	{
		if (dead)
		{
			up = down = left = right = turnLeft = turnRight = false;
			
			deadTime++;
			
			if (deadTime > 60 * 2)
				level.lose();
		}
		else
			time++;
		
		if (itemUseTime > 0)
			itemUseTime--;
		
		if (hurtTime > 0)
			hurtTime--;

		Block onBlock = level.getBlock((int) (x + 0.5), (int) (z + 0.5));

		double fh = onBlock.getFloorHeight(this);
		
		if (onBlock instanceof WaterBlock && !(lastBlock instanceof WaterBlock))
			Sound.splash.play();
		
		if (onBlock instanceof LavaBlock && !(lastBlock instanceof LavaBlock))
			Sound.splash.play();
			
		if (onBlock instanceof LavaBlock)
			hurt(new OgreEntity(0, 0), 1);
		
		lastBlock = onBlock;

		if (dead)
		{
			fh = -0.6;
			
			rot += 0.0150;
		}
		
		if (fh > y)
		{
			y += (fh - y) * 0.2;
			
			ya = 0;
		}
		else
		{
			ya -= 0.01;
			
			y += ya;
			
			if (y < fh)
			{
				y = fh;
				
				ya = 0;
			}
		}

		walkSpeed = walkspeedExtra * onBlock.getWalkSpeed(this);

		double xm = 0;
		double zm = 0;
		
		if (up)
			zm--;
		
		if (down)
			zm++;
		
		if (left)
			xm--;
		
		if (right)
			xm++;
		
		double dd = xm * xm + zm * zm;
		
		if (dd > 0)
			dd = Math.sqrt(dd);
		else
			dd = 1;
		
		xm /= dd;
		zm /= dd;

		bob *= 0.8;
		turnBob *= 0.8;
		turnBob += rota;
		bob += Math.sqrt(xm * xm + zm * zm);
		bobPhase += Math.sqrt(xm * xm + zm * zm) * onBlock.getWalkSpeed(this) / 1.5;
		
		boolean wasSliding = sliding;
		
		sliding = false;

		if (onBlock instanceof IceBlock && getSelectedItem() != Item.skates)
		{
			if (xa * xa > za * za)
			{
				sliding = true;
				za = 0;
				if (xa > 0)
					xa = 0.08;
				else
					xa = -0.08;
				z += (((int) (z + 0.5)) - z) * 0.2;
			}
			else if (xa * xa < za * za)
			{
				sliding = true;
				xa = 0;
				if (za > 0)
					za = 0.08;
				else
					za = -0.08;
				x += (((int) (x + 0.5)) - x) * 0.2;
			} 
			else
			{
				xa -= (xm * Math.cos(rot) + zm * Math.sin(rot)) * 0.1;
				za -= (zm * Math.cos(rot) - xm * Math.sin(rot)) * 0.1;
			}

			if (!wasSliding && sliding)
				Sound.slide.play();
		} else
		{
			xa -= (xm * Math.cos(rot) + zm * Math.sin(rot)) * walkSpeed;
			za -= (zm * Math.cos(rot) - xm * Math.sin(rot)) * walkSpeed;
		}

		move();

		double friction = onBlock.getFriction(this);
		
		xa *= friction;
		za *= friction;
		rot += rota;
		rota *= 0.4;
	}

	public void activate()
	{
		if (dead)
			return;
		
		if (itemUseTime > 0)
			return;
		
		Item item = items[selectedSlot];
		
		if (item == Item.pistol)
		{
			if (ammo > 0)
			{
				Sound.shoot.play();
				itemUseTime = 10;
				
				level.addEntity(new Bullet(this, x, z, rot, 1, 0, 0xffffff));
				
				ammo--;
			}
			return;
		}
		if (item == Item.potion)
		{
			if (potions > 0 && health < 20)
			{
				Sound.potion.play();
				itemUseTime = 20;
				health += 5 + random.nextInt(6);
				if (health > 20)
					health = 20;
				potions--;
			}
			return;
		}

		if (item == Item.medikit)
		{
			if (medikits > 0 && health < 20)
			{
				Sound.potion.play();
				itemUseTime = 20;
				health += 15;
				
				if (health > 20)
					health = 20;
				
				medikits--;
			}
			return;
		}

		if (item == Item.key)
			itemUseTime = 10;
		if (item == Item.powerglove)
			itemUseTime = 10;
		if (item == Item.cutters)
			itemUseTime = 10;

		double xa = (2 * Math.sin(rot));
		double za = (2 * Math.cos(rot));

		int rr = 3;
		int xc = (int) (x + 0.5);
		int zc = (int) (z + 0.5);
		
		List<Entity> possibleHits = new ArrayList<Entity>();
		
		for (int z = zc - rr; z <= zc + rr; z++)
		{
			for (int x = xc - rr; x <= xc + rr; x++)
			{
				List<Entity> es = level.getBlock(x, z).entities;
				for (int i = 0; i < es.size(); i++)
				{
					Entity e = es.get(i);
					if (e == this)
						continue;
					possibleHits.add(e);
				}
			}
		}

		int divs = 100;
		for (int i = 0; i < divs; i++)
		{
			double xx = x + xa * i / divs;
			double zz = z + za * i / divs;
			for (int j = 0; j < possibleHits.size(); j++)
			{
				Entity e = possibleHits.get(j);
				if (e.contains(xx, zz))
				{
					if (e.use(this, items[selectedSlot]))
					{
						return;
					}
				}

			}
			int xt = (int) (xx + 0.5);
			int zt = (int) (zz + 0.5);
			if (xt != (int) (x + 0.5) || zt != (int) (z + 0.5))
			{
				Block block = level.getBlock(xt, zt);
				if (block.use(level, items[selectedSlot]))
				{
					return;
				}
				if (block.blocks(this))
					return;
			}
		}
	}

	@Override
	public boolean blocks(Entity entity, double x2, double z2, double r2)
	{
		// if (entity instanceof Bullet && ((B))) return false;
		return super.blocks(entity, x2, z2, r2);
	}

	public Item getSelectedItem()
	{
		return items[selectedSlot];
	}

	public void addLoot(Item item)
	{
		if (item == Item.pistol)
			ammo += 20;
		
		if (item == Item.potion)
			potions += 1;
		
		if (item == Item.medikit)
			medikits += 1;
		
		for (int i = 0; i < items.length; i++)
		{
			if (items[i] == item)
			{
				if (level != null)
					level.showLootScreen(item);
				return;
			}
		}

		for (int i = 0; i < items.length; i++)
			if (items[i] == Item.none)
			{
				items[i] = item;
				
				selectedSlot = i;
				
				itemUseTime = 0;
				
				if (level != null)
					level.showLootScreen(item);
				
				return;
			}
	}

	public void hurt(Entity enemy, int dmg)
	{
		if (hurtTime > 0 || dead)
			return;

		hurtTime = 40;
		health -= dmg;

		if (health <= 0)
		{
			health = 0;
			
			Sound.death.play();
			
			dead = true;
		}

		Sound.hurt.play();

		double xd = enemy.x - x;
		double zd = enemy.z - z;
		double dd = Math.sqrt(xd * xd + zd * zd);
		
		xa -= xd / dd * 0.1;
		za -= zd / dd * 0.1;
		
		rota += (random.nextDouble() - 0.5) * 0.2;
	}

	@Override
	protected void collide(Entity entity)
	{
		if (entity instanceof Bullet)
		{
			Bullet bullet = (Bullet) entity;
			
			if (bullet.owner.getClass() == this.getClass())
				return;
			
			if (hurtTime > 0)
				return;
			
			entity.remove();
			hurt(entity, 1);
		}
	}

	public void win()
	{
		level.win();
	}
}
