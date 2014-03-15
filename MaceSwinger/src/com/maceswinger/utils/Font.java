
package com.maceswinger.utils;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glRotatef;

import java.util.Random;

import org.lwjgl.util.vector.Vector4f;







public class Font {
	
	private static float textureWidth= 1.0f / 16.0f;
    private static float textureX, textureY;
    private static String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ: *";
	private static String chars2 = "                                 !\"#$%&'()*+,-./" +
			"0123456789:;<=>?" +
			"@ABCDEFGHIJKLMNO" +
			"PQRSTUVWXYZ[\\]^_" +
			"`abcdefghijklmno" +
			"pqrstuvwxyz{|}!!" +
			"!"
			+ "abcdefghijklmnopqrstuvwxyzäöü{} "
			+ "1234567890+-/*^!.,?#()ß€$&%\"':;";
	private static String width5 = "ABCDEFGHJLMNOPQRSTUVWXYZÄÖÜmvw234567890+-^?#€$&%{}";
	private static String width4 = "Kabcdefghnopqrstuyzäöü/=ß\"";
	private static String width3 = "Iktx1*><";
	private static String width2 = "jl,()'; ";
	private static String width1 = "i!.:";
	private static Random rand = new Random();
	public static void draw8bitString(String msg, float x, float y, float scale, Vector4f color) {

		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.charAt(i));
			float charWidth2 = getCharWidth2(msg.charAt(i)) * scale;
			if (charIndex < 0)
				charIndex = 10;// make it a
			glColor4f(color.x, color.y, color.z, color.w);
			renderChar(charIndex, x, y, scale);
			glColor4f(1, 1, 1, 1);
			x += charWidth2 / 13;
		}
	}

	private static void renderChar(int id, float x, float y, float scale) {

		switch (id) {
		case 64:// random
			renderChar(rand.nextInt(chars.length()), x, y, scale);
			break;
		case 63:// space
			break;
		case 62:// :
			int[] co = { 16, 32 };
			render(co, x, (int) y, scale);
			break;
		case 0:
			int[] ze = { 9, 10, 11, 16, 19, 20, 24, 26, 28, 32, 34, 36, 40, 41, 44, 49, 50, 51 };
			render(ze, x, (int) y, scale);
			break;
		case 1:// a
			int[] on = { 10, 17, 18, 24, 26, 34, 42, 50 };
			render(on, x, (int) y, scale);
			break;
		case 2:// a
			int[] tw = { 9, 10, 11, 16, 20, 27, 34, 41, 48, 49, 50, 51, 52 };
			render(tw, x, (int) y, scale);
			break;
		case 3:// a
			int[] th = { 9, 10, 11, 16, 20, 26, 27, 36, 40, 44, 49, 50, 51 };
			render(th, x, (int) y, scale);
			break;
		case 4:// a
			int[] fo = { 9, 16, 24, 26, 32, 33, 34, 35, 36, 42, 50 };
			render(fo, x, (int) y, scale);
			break;
		case 5:// a
			int[] fi = { 8, 9, 10, 11, 12, 16, 25, 26, 27, 36, 40, 44, 49, 50, 51 };
			render(fi, x, (int) y, scale);
			break;
		case 6:// a
			int[] si = { 9, 10, 11, 16, 24, 25, 26, 27, 32, 36, 40, 44, 49, 50, 51 };
			render(si, x, (int) y, scale);
			break;
		case 7:// a
			int[] se = { 8, 9, 10, 11, 12, 20, 27, 34, 42, 50 };
			render(se, x, (int) y, scale);
			break;
		case 8:// a
			int[] ei = { 9, 10, 11, 16, 19, 25, 26, 27, 32, 36, 40, 44, 49, 50, 51 };
			render(ei, x, (int) y, scale);
			break;
		case 9:// a
			int[] ni = { 9, 10, 11, 12, 16, 20, 25, 26, 27, 28, 36, 40, 44, 49, 50, 51 };
			render(ni, x, (int) y, scale);
			break;
		case 10:// a
			int[] a = { 17, 18, 27, 33, 34, 35, 40, 43, 49, 50, 51 };
			render(a, x, (int) y, scale);
			break;
		case 11:// b
			int[] b = { 8, 16, 24, 25, 26, 32, 35, 40, 43, 48, 49, 50 };
			render(b, x, (int) y, scale);
			break;
		case 12:// c
			int[] c = { 25, 26, 32, 40, 49, 50 };
			render(c, x, (int) y, scale);
			break;
		case 13:// d
			int[] d = { 11, 19, 25, 26, 27, 32, 35, 40, 43, 49, 50, 51 };
			render(d, x, (int) y, scale);
			break;
		case 14:// e
			int[] e = { 17, 18, 24, 27, 32, 33, 34, 35, 40, 49, 50, 51 };
			render(e, x, (int) y, scale);
			break;
		case 15:// f
			int[] f = { 10, 11, 17, 25, 32, 33, 34, 41, 49 };
			render(f, x, (int) y, scale);
			break;
		case 16:// g
			int[] g = { 25, 26, 27, 32, 35, 41, 42, 43, 51, 56, 57, 58 };
			render(g, x, (int) y, scale);
			break;
		case 17:// h
			int[] h = { 8, 16, 24, 32, 33, 34, 40, 43, 48, 51 };
			render(h, x, (int) y, scale);
			break;
		case 18:// i
			int[] i = { 16, 32, 40, 48 };
			render(i, x, (int) y, scale);
			break;
		case 19:// j
			int[] j = { 17, 33, 41, 49, 56 };
			render(j, x, (int) y, scale);
			break;
		case 20:// k
			int[] k = { 8, 16, 24, 26, 32, 33, 40, 42, 48, 50 };
			render(k, x, (int) y, scale);
			break;
		case 21:// l
			int[] l = { 8, 16, 24, 32, 40, 49 };
			render(l, x, (int) y, scale);
			break;
		case 22:// m
			int[] m = { 24, 25, 27, 32, 34, 36, 40, 42, 44, 48, 50, 52 };
			render(m, x, (int) y, scale);
			break;
		case 23:// n
			int[] n = { 24, 25, 26, 32, 35, 40, 43, 48, 51 };
			render(n, x, (int) y, scale);
			break;
		case 24:// o
			int[] o = { 25, 26, 32, 35, 40, 43, 49, 50 };
			render(o, x, (int) y, scale);
			break;
		case 25:// p
			int[] p = { 24, 25, 26, 32, 35, 40, 43, 48, 49, 50, 56 };
			render(p, x, (int) y, scale);
			break;
		case 26:// q
			int[] q = { 25, 26, 27, 32, 35, 40, 43, 49, 50, 51, 59 };
			render(q, x, (int) y, scale);
			break;
		case 27:// r
			int[] r = { 25, 26, 32, 35, 40, 48 };
			render(r, x, (int) y, scale);
			break;
		case 28:// s
			int[] s = { 17, 18, 24, 33, 34, 43, 48, 49, 50 };
			render(s, x, (int) y, scale);
			break;
		case 29:// t
			int[] t = { 9, 17, 24, 25, 26, 33, 41, 50 };
			render(t, x, (int) y, scale);
			break;
		case 30:// u
			int[] u = { 24, 27, 32, 35, 40, 43, 49, 50, 51 };
			render(u, x, (int) y, scale);
			break;
		case 31:// v
			int[] v = { 24, 28, 32, 36, 41, 43, 50 };
			render(v, x, (int) y, scale);
			break;
		case 32:// w
			int[] w = { 24, 28, 32, 36, 40, 42, 44, 49, 51 };
			render(w, x, (int) y, scale);
			break;
		case 33:// x
			int[] xx = { 24, 26, 33, 40, 42, 48, 50 };
			render(xx, x, (int) y, scale);
			break;
		case 34:// y
			int[] yy = { 24, 27, 32, 35, 41, 42, 43, 51, 57, 58 };
			render(yy, x, (int) y, scale);
			break;
		case 35:// z
			int[] z = { 24, 25, 26, 27, 34, 41, 48, 49, 50, 51 };
			render(z, x, (int) y, scale);
			break;

		}

	}

	private static void render(int[] p, float x, int y, float scale) {
		byte[] pixels = new byte[64];
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
		for (int i = 0; i < p.length; i++) {
			pixels[p[i]] = 1;
		}
		for (int i = 0; i < pixels.length; i++) {
			if (pixels[i] == 0)
				continue;
			float yy = (y - ((i / 8) * scale));
			float xx = (x + ((i % 8) * scale));
			glPushMatrix();
			float off = scale * 2;
			glBegin(GL_QUADS);
			glColor4f(0, 1, 1, 0.1f);
			glVertex2f(xx - off, yy - off);
			glColor4f(1, 0, 0, 0.1f);
			glVertex2f(scale + xx + off, yy - off);
			glColor4f(0, 1, 1, 0.1f);
			glVertex2f(scale + xx + off, scale + yy + off);
			glColor4f(0, 1, 1, 0.1f);
			glVertex2f(xx - off, scale + yy + off);
			glColor4f(1, 1, 1, 1);
			glEnd();
			glBegin(GL_QUADS);
			glVertex2f(scale + xx, yy);
			glVertex2f(xx, yy);
			glVertex2f(xx, scale + yy);
			glVertex2f(scale + xx, scale + yy);
			glEnd();

			glPopMatrix();
		}
	}

	public static int getStringWidth2(String msg, float scale) {
		int textWidth = 0;
		for (int i = 0; i < msg.length(); i++) {
			int charWidth = getCharWidth2(msg.charAt(i));
			textWidth = (int) (textWidth + charWidth * scale + 1);
		}
		return textWidth;
	}

	

	public static int getCharWidth2(char c) {
		int charWidth = 128;
		if (width5.indexOf(c) >= 0) {
			charWidth = (128 / 6) * 5;
		}
		if (width4.indexOf(c) >= 0) {
			charWidth = (128 / 6) * 4;
		}
		if (width3.indexOf(c) >= 0) {
			charWidth = (128 / 6) * 3;
		}
		if (width2.indexOf(c) >= 0) {
			charWidth = (128 / 6) * 2;
		}
		if (width1.indexOf(c) >= 0) {
			charWidth = 128 / 6;
		}
		return charWidth;
	}



	 
	public static void drawString(String msg, float x, float y, float scale, Vector4f color) {
		
		
		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars2.indexOf(msg.charAt(i));
			
			float charWidth = 128*scale;
			if (charIndex < 0)
				charIndex = 29;
	    	getTexture(charIndex);
	    	glEnable(GL_TEXTURE_2D);
	    	glColor4f(color.x,color.y, color.z, color.w);
	    	glBindTexture(GL_TEXTURE_2D,Textures.textureID[0]);
			glBegin(GL_QUADS);
			glTexCoord2f(textureX,textureY+textureWidth);
	        glVertex2f(x, y);
	        glTexCoord2f(textureX+textureWidth,textureY+textureWidth);
	        glVertex2f(charWidth+x, y);
	        glTexCoord2f(textureX+textureWidth,textureY);
	        glVertex2f(charWidth+x, charWidth+y);
	        glTexCoord2f(textureX,textureY);
	        glVertex2f(x, charWidth+y);
	        glEnd();
			glColor4f(1,1,1,1);
			x+=getCharWidth2(msg.charAt(i))*scale;
		}
	}

	
	
	public static int getStringWidth(String msg, float scale) {
		int textWidth = 0;
		for (int i = 0; i < msg.length(); i++) {
			float charWidth = getCharWidth(msg.charAt(i));
			textWidth = (int) (textWidth + charWidth * scale + 1);
		}
		return textWidth;
	}
	private static void getTexture(int i){
		
		 textureX= i % 16 * textureWidth;
		 textureY= i / 16 * textureWidth;
		 
		
	}
	public static float getCharWidth(char c) {
		float charWidth = 6.0f;
		if(width5.indexOf(c) >= 0) {
			charWidth = 2.5f;
		}
		if(width4.indexOf(c) >= 0) {
			charWidth = 2f;
		}
		if(width3.indexOf(c) >= 0) {
			charWidth = 1.5f;
		}
		if(width2.indexOf(c) >= 0) {
			charWidth = 1f;
		}
		if(width1.indexOf(c) >= 0) {
			charWidth = 0.5f;
		}
		return charWidth;
	}

	
	}







