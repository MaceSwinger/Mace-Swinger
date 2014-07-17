package com.maceswinger.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

public class SpriteSheet {

	
	public static int width;
	public static int height;
	public static int tileSheet = GL11.glGenTextures();
	public static int fontSheet = GL11.glGenTextures();
	public static float textureWidth = 1.0f/16.0f;
	public SpriteSheet(String path) {
		
		
	}
	public static void openSheet(){
		TextureBinder tex = new TextureBinder(SpriteSheet.class.getResourceAsStream("/tileset.png"),
				tileSheet);
		tex.equals(null);
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream("/tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (image == null) {
			return;
		}

		
		width = image.getWidth();
		height = image.getHeight();
	}
}
