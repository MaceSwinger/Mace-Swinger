package com.maceswinger.items;



public class ModBase {

	public static void registerMaterial(Material mat) {
			Material.mats[mat.getId()]=mat;
	}
}
