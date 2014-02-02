package com.maceswinger;

import org.magnos.entity.ComponentValueFactory;


public class Rectangle implements ComponentValueFactory<Rectangle> {
	public float x;
	public float y;
	public float w;
	public float h;
	
	public Rectangle() {
		this(0, 0, 0, 0);
	}
	public Rectangle(Rectangle rect) {
		this(rect.x, rect.y, rect.w, rect.h);
	}
	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public Vector2 topLeft() {
		return new Vector2(x, y);
	}
	public Vector2 topRight() {
		return new Vector2(x + w, y);
	}
	public Vector2 bottomLeft() {
		return new Vector2(x, y + h);
	}
	public Vector2 bottomRight() {
		return new Vector2(x + w, y + h);
	}
	public boolean overlaps(Vector2 other) {
		return overlaps(new Vector2(0, 0), other);
	}
	public boolean overlaps(Vector2 selfOffset, Vector2 other) {
		return other.x > x + selfOffset.x &&
				other.x < x + w + selfOffset.x &&
				other.y > y + selfOffset.y &&
				other.y < y + h + selfOffset.y;
	}
	public boolean overlaps(Vector2 selfOffset, Rectangle other) {
		if (x + selfOffset.x < other.x + other.w && x + selfOffset.x + w > other.x &&
			    y + selfOffset.y < other.y + other.h && y + h + selfOffset.y > other.y) {
			return true;
		}
		return false;
	}
	public boolean overlaps(Rectangle other) {
		return overlaps(new Vector2(), other);
	}
	
	@Override
	public Rectangle create() {
		return null;
	}

	@Override
	public Rectangle clone(Rectangle value) {
		return new Rectangle(value);
	}

	@Override
	public Rectangle copy(Rectangle from, Rectangle to) {
		to.x = from.x;
		to.y = from.y;
		to.w = from.w;
		to.h = from.h;
		return to;
	}
}
