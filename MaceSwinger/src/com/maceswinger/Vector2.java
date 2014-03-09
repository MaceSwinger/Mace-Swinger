package com.maceswinger;

import org.magnos.entity.ComponentValueFactory;

/**
 * 2D vector class.
 * 
 * @since Feb 2, 2014
 */
public class Vector2 implements ComponentValueFactory<Vector2>
{
	public float x;
	public float y;

	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector2(Vector2 cloned)
	{
		this(cloned.x, cloned.y);
	}

	public Vector2()
	{
		this(0, 0);
	}

	@Override
	public Vector2 create()
	{
		return new Vector2(0, 0);
	}

	@Override
	public Vector2 clone(Vector2 value)
	{
		return new Vector2(value);
	}

	@Override
	public Vector2 copy(Vector2 from, Vector2 to)
	{
		to.x = from.x;
		to.y = from.y;

		return to;
	}

	@Override
	public String toString()
	{
		return "[" + x + ", " + y + "]";
	}
}
