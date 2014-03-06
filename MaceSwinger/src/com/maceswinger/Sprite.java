package com.maceswinger;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.magnos.entity.ComponentValueFactory;

public class Sprite implements ComponentValueFactory<Sprite>
{
	public Texture texture;
	public Rectangle region;

	public static Random r = new Random();

	public Sprite(Texture texture, Rectangle region)
	{
		this.texture = texture;
		this.region = region;
	}

	public Sprite()
	{
		this(null, null);
	}

	public void draw(Vector2 pos, Color color)
	{
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		color.glSet();

		GL11.glTexCoord2f(region.x / texture.width, (region.y + region.h) / texture.height);
		GL11.glVertex2f(pos.x, pos.y);

		GL11.glTexCoord2f((region.x + region.w) / texture.width, (region.y + region.h) / texture.height);
		GL11.glVertex2f(pos.x + region.w, pos.y);

		GL11.glTexCoord2f((region.x + region.w) / texture.width, region.y / texture.height);
		GL11.glVertex2f(pos.x + region.w, pos.y + region.h);

		GL11.glTexCoord2f(region.x / texture.width, region.y / texture.height);
		GL11.glVertex2f(pos.x, pos.y + region.h);

		GL11.glEnd();
	}

	@Override
	public Sprite create()
	{
		return new Sprite(null, null);
	}

	@Override
	public Sprite clone(Sprite value)
	{
		return new Sprite(value.texture, value.region.clone(new Rectangle()));
	}

	@Override
	public Sprite copy(Sprite from, Sprite to)
	{
		to.region = from.region;
		to.texture = from.texture;
		return null;
	}
}
