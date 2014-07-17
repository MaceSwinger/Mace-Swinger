package com.maceswinger.mods;

public class Tile {

	public String name;
	public int x,y;
	public ModTexture texture;
	public Tile(String name, ModTexture texture,  int x, int y){
		this.name=name;
		this.x=x;
		this.y=y;
		this.texture = texture;
		
	}
}
