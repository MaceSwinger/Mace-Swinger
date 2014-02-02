package com.maceswinger;

import org.magnos.entity.ComponentValueFactory;

public class Flip implements ComponentValueFactory<Flip> {
	public boolean horiz;
	public boolean verti;
	@Override
	public Flip create() {
		return new Flip();
	}
	@Override
	public Flip clone(Flip value) {
		Flip f = new Flip();
		f.horiz = value.horiz;
		f.verti = value.verti;
		return f;
	}
	@Override
	public Flip copy(Flip from, Flip to) {
		to.horiz = from.horiz;
		to.verti = from.verti;
		return null;
	}
}
