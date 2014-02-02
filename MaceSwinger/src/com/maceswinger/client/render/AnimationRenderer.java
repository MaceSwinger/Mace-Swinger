package com.maceswinger.client.render;

import org.magnos.entity.Entity;
import org.magnos.entity.Renderer;

import com.maceswinger.Animation;
import com.maceswinger.Components;

public class AnimationRenderer implements Renderer
{
	public SpriteRenderer internalSpriteRenderer;

	public AnimationRenderer(String spriteSheet)
	{
		internalSpriteRenderer = new SpriteRenderer(spriteSheet, "");
	}

	@Override
	public Renderer create(Entity e)
	{
		return new AnimationRenderer(internalSpriteRenderer.spriteSheet);
	}

	@Override
	public void begin(Entity e, Object drawState)
	{
		if (!e.has(Components.animation))
			System.out.println("Warning: Tried to AnimationRenderer an entity without animation component");
		else
		{
			Animation anim = e.get(Components.animation);
			anim.update();
			internalSpriteRenderer.sprite = anim.currentSprite;
			internalSpriteRenderer.begin(e, drawState);
		}
	}

	@Override
	public void end(Entity e, Object drawState)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy(Entity e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void notify(Entity e, int message)
	{
		// TODO Auto-generated method stub

	}

}
