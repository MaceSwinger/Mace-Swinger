package com.maceswinger.items;

public class ModExample extends ModBase {

	public static final Material STEEL = new Material(9, 102,"STEEL");
	
	public static void loadMod(){
		ModBase.registerMaterial(STEEL);
	}
}
