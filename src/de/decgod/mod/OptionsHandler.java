package de.decgod.mod;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.mojang.escape.EscapeComponent;

/**
 * @author Benjamin
 * @version 1.0
 * @see benjamin.wulfert@cstx.de
 */
public class OptionsHandler {

	/** unscaled screen width */
	private int width;

	/** scaled screen width */
	private int scaled_width;

	/** unscaled screen height */
	private int height;

	/** scaled screen height */
	private int scaled_height;

	/** scalefactor which is used for screen-scaling */
	private int scalefactor;

	/** mousespeed */
	private double mousespeed;

	/** playername */
	private String playername;

	/** scaled Screen size */
	private Dimension screen;

	/** unscaled screen size */
	private int screen_size;

	/** scaled screen size */
	private int screen_size_scaled;

	/** ref. for the main-frame, used for location-purposes */
	private JFrame frame;

	/** Instance of the options-object. Contains screensize, etc. */
	private static OptionsHandler instance = null;

	/** allowedChars :) */
	private String allowedChars;

	private OptionsHandler() {
		width = Integer.parseInt(Messages.getString("width"));
		height = Integer.parseInt(Messages.getString("height"));
		scalefactor = Integer.parseInt(Messages.getString("scalefactor"));
		mousespeed = Double.parseDouble(Messages.getString("mousespeed"));
		playername = Messages.getString("playername");
		screen = new Dimension(width * scalefactor, height * scalefactor);
		frame = EscapeComponent.frameOut;
		allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}abcdefghijklmnopqrstuvwxyz_               0123456789+-=*:;÷≈ƒÂ";
		scaled_width = width * scalefactor;
		scaled_height = height * scalefactor;
		screen_size = width * height;
		screen_size_scaled = scaled_width * scaled_height;
	}

	/**
	 * @return instance - returns the instance-object
	 */
	public static OptionsHandler getInstance() {
		if (instance == null) {
			instance = new OptionsHandler();
		}

		return instance;
	}

	/**
	 * @return width - returns the unscaled width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return width - returns the unscaled height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return scalefactor - returns the scalefactor
	 */
	public int getScale() {
		return scalefactor;
	}

	/**
	 * @return mousespeed - returns the mousespeed
	 */
	public double getMouseSpeed() {
		return mousespeed;
	}

	/**
	 * @return screen - returns the screen, contains the scaled size, etc.
	 */
	public Dimension getScreen() {
		return screen;
	}

	/**
	 * @return frame - returns the frame, used for locationing purposes
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * @return playername - returns the playername
	 */
	public String getPlayername() {
		return playername;
	}

	/***
	 * @return allowedChars
	 */
	public String getAllowedChars() {
		return allowedChars;
	}

	/**
	 * @return sheight - returns the scaled height ( height * scalefactor)
	 */
	public int getHeightScaled() {
		return scaled_height;
	}

	/**
	 * @return swidth - returns the scaled width (width * scalefactor)
	 */
	public int getWidthScaled() {
		return scaled_width;
	}
	
	
	/**
	 * @returns the (unscaled) screen size
	 */
	public int getScreenSize() {
		return screen_size;
	}
	
	/**
	 * @returns the scaled screen size
	 */	
	public int getScreenSizeScaled() {
		return screen_size_scaled;
	}
}