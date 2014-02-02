package com.maceswinger;

import org.magnos.entity.ComponentValueFactory;

public class Animation implements ComponentValueFactory<Animation>
{
	public Animation () { }
	public static class Frame
	{
		String sprite;
		int duration;

		public Frame() { }
		public Frame(String sprite, int duration)
		{
			this.sprite = sprite;
			this.duration = duration;
		}
	}

	Frame[] frames;

	public Animation(Frame... frames)
	{
		this.frames = frames;
	}

	int animationStep = 0;
	int frame = 0;
	public String currentSprite;

	public void update()
	{
		animationStep++;
		if (animationStep >= frames[frame].duration)
		{
			if (frame == frames.length - 1)
				frame = 0;
			else
				frame++;
			animationStep = 0;
		}
		currentSprite = frames[frame].sprite;
	}

	@Override
	public Animation create()
	{
		return new Animation();
	}

	@Override
	public Animation clone(Animation value)
	{
		return new Animation(value.frames);
	}

	@Override
	public Animation copy(Animation from, Animation to)
	{
		to.frames = from.frames;
		return from;
	}
}
