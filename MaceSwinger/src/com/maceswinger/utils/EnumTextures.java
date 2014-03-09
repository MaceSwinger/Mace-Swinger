package com.maceswinger.utils;

public enum EnumTextures {

	FONT(0), 
	;
	public int id;

	EnumTextures(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}
}
