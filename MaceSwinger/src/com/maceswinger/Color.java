package com.maceswinger;

import org.lwjgl.opengl.GL11;
import org.magnos.entity.ComponentValueFactory;

public class Color implements ComponentValueFactory<Color>{
	public static Color red() {
		return new Color(255, 0, 0, 255);
	}
	public static Color white() {
		return new Color(255, 255, 255, 255);
	}
	public static Color blue() {
		return new Color(0, 0, 255, 255);
	}
	public int r;
	public int g;
	public int b;
	public int a;
	public Color() {
		this(255, 255, 255, 255);
	}
	public Color(Color clone) {
		this(clone.r, clone.g, clone.b, clone.a);
	}
	public Color(int r, int g, int b) {
		this(r, g, b, 255);
	}
	public Color(int r, int g, int b, int a) {
		this.r = r;
		this.b = b;
		this.g = g;
		this.a = a;
	}
	public void glSet() {
		GL11.glColor4f(r / 255, g / 255, b / 255, a / 255);
	}
	@Override
	public Color create() {
		return new Color();
	}
	@Override
	public Color clone(Color value) {
		return new Color(value);
	}
	@Override
	public Color copy(Color from, Color to) {
		to.r = from.r;
		to.g = from.g;
		to.b = from.b;
		to.a = from.a;
		return to;
	}
}
