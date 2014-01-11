package com.mojang.escape.gui;

import com.mojang.escape.Art;

import de.decgod.mod.RuntimeConfiguration;

public class Bitmap {

	public final int width;
	public final int height;
	public final int[] pixels;
	private static final String chars = RuntimeConfiguration.getInstance().getAllowedChars();

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void setPixel(int x, int y, int col){
		pixels[(y*height)+x] = col;
	}
	
	public int getPixel(int x, int y){
		return pixels[(y*height)+x];
	}

	public void flipDraw(Bitmap bitmap, int xOffs, int yOffs) {
		for (int y = 0; y < bitmap.height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < bitmap.width; x++) {
				int xPix = xOffs + bitmap.width - x - 1;
				if (xPix < 0 || xPix >= width) continue;

				int src = bitmap.pixels[x + y * bitmap.width];
				pixels[xPix + yPix * width] = src;
			}
		}
	}

	public void draw(Bitmap bitmap, int xOffs, int yOffs, int xo, int yo, int w, int h, int col) {
		for (int y = 0; y < h; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < w; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;
				
				int src = bitmap.pixels[(x + xo) + (y + yo) * bitmap.width];
				if (src >= 0) {
					pixels[xPix + yPix * width] = src * col;
				}
			}
		}
	}
	
	public void draw(Bitmap bitmap, int xOffs, int yOffs, int xo, int yo, int w, int h) {
		for (int y = 0; y < h; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < w; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;
				
				int src = bitmap.pixels[(x + xo) + (y + yo) * bitmap.width];
				if (src != -1) {
					pixels[xPix + yPix * width] = src;						
				}
			}
		}
	}

	public void draw(Bitmap bitmap, int xOffs, int yOffs) {
		for (int y = 0; y < bitmap.height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < bitmap.width; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;

				int src = bitmap.pixels[x + y * bitmap.width];
				
				if (src != -1) {
					pixels[xPix + yPix * width] = src;						
				}
			}
		}
	}
	
	public void scaleDraw(Bitmap bitmap, int scale, int xOffs, int yOffs, int xo, int yo, int w, int h, int col) {
		for (int y = 0; y < h * scale; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < w * scale; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;

				int src = bitmap.pixels[(x / scale + xo) + (y / scale + yo) * bitmap.width];
				if (src >= 0) {
					pixels[xPix + yPix * width] = src * col;
				}
			}
		}
	}

	//decmod - scaled draw without color, 100% src
	public void scaleDraw(Bitmap bitmap, int scale, int xOffs, int yOffs, int xo, int yo, int w, int h) {
		for (int y = 0; y < h * scale; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < w * scale; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;

				int src = bitmap.pixels[(x / scale + xo) + (y / scale + yo) * bitmap.width];
				if (src != -1) {
					pixels[xPix + yPix * width] = src;						
				}
			}
		}
	}
	
	public void draw(String string, int x, int y, int col) {
		for (int i = 0; i < string.length(); i++) {
			int ch = chars.indexOf(string.charAt(i));
			if (ch < 0) continue;

			int xx = ch % 42;
			int yy = ch / 42;
			draw(Art.font, x + i * 6, y, xx * 6, yy * 8, 5, 8, col);
		}
	}
	
	public void draw(String string, int x, int y, int col, int scale) {
		for (int i = 0; i < string.length(); i++) {
			int ch = chars.indexOf(string.charAt(i));
			if (ch < 0) continue;

			int xx = ch % 42;
			int yy = ch / 42;
			scaleDraw(Art.font, scale, x + i * (scale*6), y*scale, xx * 6, yy * 8, 5, 8, col);
		}
	}	
	//decmod
	
	public void fill(int x0, int y0, int x1, int y1, int color) {
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				pixels[x + y * width] = color;
			}
		}
	}
	
}
