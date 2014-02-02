package com.maceswinger;

import org.magnos.entity.ComponentValueFactory;

public class Camera implements ComponentValueFactory<Camera> {
	public float x;
	public float y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public Camera() {
		x = 0;
		y = 0;
	}
	@Override
	public Camera create() {
		return new Camera(x, y);
	}

	@Override
	public Camera clone(Camera value) {
		return new Camera(value.x, value.y);
	}

	@Override
	public Camera copy(Camera from, Camera to) {
		to.x = from.x;
		to.y = from.y;
		return to;
	}

}
