package com.mojang.escape;

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

import de.decgod.mod.OptionsHandler;
import de.decgod.mod.Scene;

public class InputHandler implements KeyListener, FocusListener, MouseListener,
		MouseMotionListener, MouseWheelListener {
	public boolean[] keys = new boolean[65536];

	// NinjadamageMod
	private int oldx = 0;
	private Robot r = null;
	private boolean mouseMoving = true;

	// NinjadamageMod

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
		Scene.getInstance().getPlayer().setMouse(arg0.getPoint());
		
		try {
			if (r == null) {
				r = new Robot();
			}

			// is mouse on the left side off the window?
			if (arg0.getPoint().getX() > 0
					&& arg0.getPoint().getX() < OptionsHandler.getInstance()
							.getScreen().getWidth() / 3) {
				centerMouse();
			}

			// is mouse on the right side off the window?
			if (arg0.getPoint().getX() > (rightBorder - OptionsHandler.getInstance()
					.getScreen().getWidth() / 3)
					&& arg0.getPoint().getX() < rightBorder) {
				centerMouse();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (mouseMoving) {
			if (arg0.getPoint().getX() > oldx && oldx != 0) {
				Scene.getInstance().getPlayer().turnRight(arg0.getPoint().getX() - oldx);
			}

			if (arg0.getPoint().getX() < oldx && arg0.getPoint().getX() > 20
					&& oldx != 0) {
				Scene.getInstance().getPlayer().turnLeft(arg0.getPoint().getX() - oldx);
			}

			oldx = (int) arg0.getPoint().getX();
		} else {
			mouseMoving = !false;
		}

	}
	
	/**
	 * centers the mouse on the frame.
	 */
	public void centerMouse() {

		mouseMoving = false;

		int screenCenterX = 0, screenCenterY = 0;

		screenCenterX = EscapeComponent.frameOut.getX()
				+ (int) OptionsHandler.getInstance().getScreen().getWidth() / 2;
		screenCenterY = EscapeComponent.frameOut.getY()
				+ (int) OptionsHandler.getInstance().getScreen().getHeight() / 2;

		oldx = 0;
		r.mouseMove(screenCenterX, screenCenterY);

	}

	int rightBorder = (int) OptionsHandler.getInstance().getScreen().getWidth();

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON1) {
			keys[32] = true;
		}
	}

	// NinjadamageMod

	@Override
	public void focusLost(FocusEvent arg0) {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code > 0 && code < keys.length) {
			keys[code] = true;
		}
		
		if (Scene.getInstance().getPlayer().getBash().isOpen()) {
			Scene.getInstance().getPlayer().getBash().listen(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code > 0 && code < keys.length) {
			keys[code] = false;
		}
	}

	// NinjadamageMod
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {

			Scene.getInstance().getPlayer().selectedSlot++;

			if (Scene.getInstance().getPlayer().selectedSlot == 8) {
				Scene.getInstance().getPlayer().selectedSlot = 0;
			}
		}
		if (e.getWheelRotation() > 0) {

			if (Scene.getInstance().getPlayer().selectedSlot == 0) {
				Scene.getInstance().getPlayer().selectedSlot = 8;
			}

			Scene.getInstance().getPlayer().selectedSlot--;
		}
	}

	// Ninjadamage

	@Override
	public void mouseExited(MouseEvent arg0) {
		centerMouse();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	public void focusGained(FocusEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}

}