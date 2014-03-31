package com.mojang.escape;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.mojang.escape.entities.Player;
import com.mojang.escape.menu.Menu;

import de.decgod.mod.RuntimeConfiguration;
import de.decgod.mod.Scene;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener, MouseWheelListener
{

	public boolean[] keys = new boolean[65536];

	// NinjadamageMod
	private int oldx = 0;
	private Robot r;
	private boolean mouseMoving = true;

	public InputHandler()
	{
		try
		{
			r = new Robot();
		} catch (AWTException e)
		{
			e.printStackTrace();
		}
	}

	// NinjadamageMod

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		if (!Player.dead)
		{
			Scene.getInstance().getPlayer().setMouse(arg0.getPoint());

			if (arg0.getPoint().getX() > 0 && arg0.getPoint().getX() < RuntimeConfiguration.getInstance().getScreen().getWidth() / 4)
			{
				centerMouse();
			}

			// is mouse on the right side off the window?
			if (arg0.getPoint().getX() > (rightBorder - RuntimeConfiguration.getInstance().getScreen().getWidth() / 4) && arg0.getPoint().getX() < rightBorder)
			{
				centerMouse();
			}

			if (mouseMoving)
			{
				if (arg0.getPoint().getX() > oldx && oldx != 0)
				{
					Scene.getInstance().getPlayer().turnRight(arg0.getPoint().getX() - oldx);
				}

				if (arg0.getPoint().getX() < oldx && arg0.getPoint().getX() > 20 && oldx != 0)
				{
					Scene.getInstance().getPlayer().turnLeft(arg0.getPoint().getX() - oldx);
				}

				oldx = (int) arg0.getPoint().getX();
			} else
			{
				mouseMoving = !false;
			}
		}
	}

	/**
	 * centers the mouse on the frame.
	 */
	public void centerMouse()
	{
		mouseMoving = false;

		int screenCenterX = 0, screenCenterY = 0;

		screenCenterX = EscapeComponent.frameOut.getX() + (int) RuntimeConfiguration.getInstance().getScreen().getWidth() / 2;
		screenCenterY = EscapeComponent.frameOut.getY() + (int) RuntimeConfiguration.getInstance().getScreen().getHeight() / 2;

		oldx = 0;
		
		r.mouseMove(screenCenterX, screenCenterY);
	}

	int rightBorder = (int) RuntimeConfiguration.getInstance().getScreen().getWidth();

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if (arg0.getButton() == MouseEvent.BUTTON1)
		{
			keys[32] = true;
		}
		mouseMoved(arg0);
	}

	// NinjadamageMod

	@Override
	public void focusLost(FocusEvent arg0)
	{
		for (int i = 0; i < keys.length; i++)
			keys[i] = false;
		
		centerMouse();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		if (code > 0 && code < keys.length)
			keys[code] = true;

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		if (code > 0 && code < keys.length)
			keys[code] = false;

		if (Scene.getInstance().getPlayer().getBash().isOpen())
			Scene.getInstance().getPlayer().getBash().listen(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{

		int wheelRotation = e.getWheelRotation();

		Menu menu = Scene.getInstance().getGame().menu;
		if (menu != null)
			menu.tick(Scene.getInstance().getGame(), wheelRotation < 0, wheelRotation > 0, false, false, false);

		if (wheelRotation < 0)
		{

			Scene.getInstance().getPlayer().selectedSlot++;

			if (Scene.getInstance().getPlayer().selectedSlot == 8)
			{
				Scene.getInstance().getPlayer().selectedSlot = 0;
			}
		}
		if (wheelRotation > 0)
		{
			if (Scene.getInstance().getPlayer().selectedSlot == 0)
				Scene.getInstance().getPlayer().selectedSlot = 8;

			Scene.getInstance().getPlayer().selectedSlot--;
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		centerMouse();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
	}

	@Override
	public void focusGained(FocusEvent arg0)
	{
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		mouseMoved(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{

	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		mouseMoved(arg0);
	}

}