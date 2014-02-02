package com.maceswinger.client.render;

import org.magnos.entity.Entity;
import org.magnos.entity.Renderer;

import com.maceswinger.Assets;
import com.maceswinger.Color;
import com.maceswinger.Components;
import com.maceswinger.Sprite;
import com.maceswinger.Vector2;

public class SpriteRenderer implements Renderer
{
	public String spriteSheet;
	public String sprite;

	public SpriteRenderer(String spriteSheet, String sprite)
	{
		this.spriteSheet = spriteSheet;
		this.sprite = sprite;
	}

	@Override
	public Renderer create(Entity e)
	{
		return new SpriteRenderer(spriteSheet, sprite);
	}

	@Override
	public void begin(Entity e, Object drawState)
	{
		Sprite t = Assets.get(spriteSheet, sprite);

		Color col = Color.white();
		if (e.has(Components.color))
		{
			col = e.get(Components.color);
		}
		if (e.has(Components.position))
		{
			Vector2 vec = e.get(Components.position);
			if(e.has(Components.camera)) {
				vec.x -= e.get(Components.camera).x;
				vec.y -= e.get(Components.camera).y;
			}
			t.draw(vec, col);
		}
	}

	@Override
	public void end(Entity e, Object drawState)
	{
	}

	@Override
	public void destroy(Entity e)
	{
	}

	@Override
	public void notify(Entity e, int message)
	{
	}
}