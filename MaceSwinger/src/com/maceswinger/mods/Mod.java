package com.maceswinger.mods;

public abstract class Mod
{
	public String name = "[MOD NAME]";
	public String desc = "[MOD DESCRIPTION]";

	public abstract void info();

	public abstract void init();
}
