
package com.maceswinger.utils;

public enum EnumTextures {

	FONT(0),
	BG_Ground(1),BG_Sky(2),Logo(3),Vignette(4),
	LeftHand(5),Legs(6),Torso(7),
	Mods(8), Play(9), Quit(10), Settings(11), 
	ModsT(12), PlayT(13), QuitT(14), SettingsT(15), 
	
	;
	public int id;

	EnumTextures(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}
}

