package com.maceswinger.mods;


public enum TextureType{

	
	MACE("maces"),MOB("mob"),TILE("tileset")
	
	
	;
	public String type;

	TextureType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
	
	}
