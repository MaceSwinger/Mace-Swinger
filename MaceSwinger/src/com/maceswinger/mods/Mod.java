package com.maceswinger.mods;

import com.maceswinger.items.Condition;
import com.maceswinger.items.Elemental;
import com.maceswinger.items.Material;

public abstract class Mod {
	public String name = "[MOD NAME]";
	public String desc = "[MOD DESCRIPTION]";

	public abstract void info();

	public abstract void init();

	public static void registerMaterial(Material mat) {
		Material.mats[mat.getId()] = mat;
		System.out.println("Added material: "+mat.getName());
	}

	public static void registerElemental(Elemental mat) {
		Elemental.elems[mat.getId()] = mat;
		System.out.println("Added elemental: "+mat.getName());
	}

	public static void registerCondition(Condition mat) {
		Condition.mats[mat.getId()] = mat;
		System.out.println("Added condition: "+mat.getName());
	}
}
