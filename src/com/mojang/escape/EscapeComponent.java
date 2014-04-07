package com.mojang.escape;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mojang.escape.gui.Screen;

import de.decgod.mod.RuntimeConfiguration;

public class EscapeComponent extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;

	// I have speedran this game. 4:54 seconds is my PB.

	// ninjadamage Mod
	private final int WIDTH = RuntimeConfiguration.getInstance().getWidth();
	private final int HEIGHT = RuntimeConfiguration.getInstance().getHeight();
	private final int SCALE = RuntimeConfiguration.getInstance().getScalefactor();
	private final int width_scaled = WIDTH * SCALE;
	private final int height_scaled = HEIGHT * SCALE;
	private final int screenSize = WIDTH * HEIGHT;
	public static JFrame frameOut;
	// ninjadamage Mod

	private boolean running;
	private Thread thread;

	private Game game;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;
	private InputHandler inputHandler;
	private Cursor emptyCursor, defaultCursor;
	private boolean hadFocus = false;

	public EscapeComponent()
	{
		Dimension size = RuntimeConfiguration.getInstance().getScreen().getSize();
		
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		game = new Game();
		screen = new Screen(WIDTH, HEIGHT);

		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		inputHandler = new InputHandler();

		addKeyListener(inputHandler);
		addFocusListener(inputHandler);
		addMouseListener(inputHandler);
		addMouseMotionListener(inputHandler);
		addMouseWheelListener(inputHandler);
		
		emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "empty");
		defaultCursor = getCursor();
	}

	public synchronized void start()
	{
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop()
	{
		if (!running)
			return;

		running = false;

		try
		{
			thread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static int frames;

	@Override
	public void run()
	{
		int frames = 0;

		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;

		requestFocus();

		while (running)
		{
			long now = System.nanoTime();
			long passedTime = now - lastTime;

			lastTime = now;

			if (passedTime < 0)
				passedTime = 0;

			if (passedTime > 100000000)
				passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean ticked = false;
			
			while (unprocessedSeconds > secondsPerTick)
			{
				tick();
				
				unprocessedSeconds -= secondsPerTick;
				
				ticked = true;

				tickCount++;
				
				if (tickCount % 60 == 0)
				{
					this.frames = frames;
					
					lastTime += 1000;
					
					frames = 0;
				}
			}

			if (ticked)
			{
				render();
				
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}

		}
	}

	private void tick()
	{
		if (hasFocus())
			game.tick(inputHandler.keys);
	}

	BufferStrategy bs;
	Graphics g;

	private void render()
	{
		if (hadFocus != hasFocus())
		{
			hadFocus = !hadFocus;
			setCursor(hadFocus ? emptyCursor : defaultCursor);
		}

		bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}

		screen.render(game, hasFocus());

		for (int i = 0; i < screenSize; i++)
		{
			pixels[i] = screen.pixels[i];
		}

		g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, width_scaled, height_scaled, null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args)
	{
		EscapeComponent game = new EscapeComponent();
		JFrame frame = new JFrame("Decay of the Goddess");
		frameOut = frame;
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(game, BorderLayout.CENTER);
		panel.setDoubleBuffered(true);

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		game.start();
	}

}
