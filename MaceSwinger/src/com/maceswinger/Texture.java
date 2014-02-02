package com.maceswinger;

import org.lwjgl.opengl.GL11;

public class Texture {
	private int id;
	public float width;
	public float height;
	public Texture(int glID, float width, float height) {
		this.id = glID;
		this.width = width;
		this.height = height;
	}
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
}
