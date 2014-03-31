package de.decgod.mod;

import com.mojang.escape.EscapeComponent;
import de.decgod.view.Theme;

import javax.swing.*;
import java.awt.*;

/**
 * @author Benjamin
 * @version 1.0
 * @see wulfert.benjamin@googlemail.com
 */
public class RuntimeConfiguration {

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

	/** fov */
	private int fov = 130;
	
	/** scaled Screen size */
	private Dimension screen;

	/** unscaled screen size */
	private int screen_size;

	/** scaled screen size */
	private int screen_size_scaled;

	/** ref. for the main-frame, used for location-purposes */
	private JFrame frame;

	/** Instance of the options-object. Contains screensize, etc. */
	private static RuntimeConfiguration instance = null;

	/** allowedChars :) */
	private String allowedChars;

    private Theme theme;

	private RuntimeConfiguration() {
		width = Integer.parseInt(Messages.getString("width"));
		height = Integer.parseInt(Messages.getString("height"));
		scalefactor = Integer.parseInt(Messages.getString("scalefactor"));
		mousespeed = Double.parseDouble(Messages.getString("mousespeed"));
		playername = Messages.getString("playername");
		fov = Integer.parseInt(Messages.getString("fov"));
		screen = new Dimension(width * scalefactor, height * scalefactor);
		frame = EscapeComponent.frameOut;
		allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}abcdefghijklmnopqrstuvwxyz_               0123456789+-=*:;����";
		scaled_width = width * scalefactor;
		scaled_height = height * scalefactor;
		screen_size = width * height;
		screen_size_scaled = scaled_width * scaled_height;
		
        this.theme = new Theme();
	}

	/**
	 * @return instance - returns the instance-object
	 */
	public static RuntimeConfiguration getInstance() {
		if (instance == null) {
			instance = new RuntimeConfiguration();
		}

		return instance;
	}

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getScaled_width() {
        return scaled_width;
    }

    public void setScaled_width(int scaled_width) {
        this.scaled_width = scaled_width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getScaled_height() {
        return scaled_height;
    }

    public void setScaled_height(int scaled_height) {
        this.scaled_height = scaled_height;
    }

    public int getScalefactor() {
        return scalefactor;
    }

    public void setScalefactor(int scalefactor) {
        this.scalefactor = scalefactor;
    }

    public double getMousespeed() {
        return mousespeed;
    }

    public void setMousespeed(double mousespeed) {
        this.mousespeed = mousespeed;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public Dimension getScreen() {
        return screen;
    }

    public void setScreen(Dimension screen) {
        this.screen = screen;
    }

    public int getScreen_size() {
        return screen_size;
    }

    public void setScreen_size(int screen_size) {
        this.screen_size = screen_size;
    }

    public int getScreen_size_scaled() {
        return screen_size_scaled;
    }

    public void setScreen_size_scaled(int screen_size_scaled) {
        this.screen_size_scaled = screen_size_scaled;
    }
    
    public int getFov()
    {
    	return fov;
    }
    
    public void setFov(int fov)
    {
    	this.fov = fov;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public static void setInstance(RuntimeConfiguration instance) {
        RuntimeConfiguration.instance = instance;
    }

    public String getAllowedChars() {
        return allowedChars;
    }

    public void setAllowedChars(String allowedChars) {
        this.allowedChars = allowedChars;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}