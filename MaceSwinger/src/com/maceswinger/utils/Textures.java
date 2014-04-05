package com.maceswinger.utils;

import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;

public class Textures {

	public static String[] gui = {"bitmapfont"};
	public static String[] gui_mainmenu = {"BG_Ground","BG_Sky","Logo", "Vignette"};
	public static String[] gui_mainmenu_Uthgourd = {"LeftHand","Legs", "Torso"};
	public static String[] gui_mainmenu_B_Backs = {"Mods","Play","Quit", "Settings"};
	public static String[] gui_mainmenu_B_Text = {"Mods","Play","Quit", "Settings"};
	public static String[] textures ={"bitmapfont","BG_Ground","BG_Sky","Logo", "Vignette","LeftHand","Legs", "Torso","Mods","Play","Quit", "Settings","ModsT","PlayT","QuitT", "SettingsT"};
	public static int[] textureID = new int[textures.length];

	public static void loadAll() {
	
		for (int i = 0; i < textures.length; i++) {
			textureID[i] = glGenTextures();
			TextureBinder.bindTexture("gui/" + textures[i] + ".png", textureID[i]);
		}
		

	}

	public static void deleteAll() {
		for (int i = 0; i < textureID.length; i++) {
			glDeleteTextures(textureID[i]);
		}

	}
	
}
