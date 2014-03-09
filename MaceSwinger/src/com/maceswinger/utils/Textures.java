package com.maceswinger.utils;

import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;

import com.maceswinger.Resources;

public class Textures {

	public static String[] textures = {"bitmapfont","shield"};
	public static int[] textureID = new int[textures.length];
	static TextureBinder tex;

	public static void loadAll() {
		for (int i = 0; i < textures.length; i++) {
			textureID[i] = glGenTextures();

			tex = new TextureBinder("image/" + textures[i] + ".png", textureID[i]);
		}
		tex.equals(null);
	}

	public static void deleteAll() {
		for (int i = 0; i < textures.length; i++) {
			glDeleteTextures(textureID[i]);
		}

	}
}
