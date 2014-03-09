package com.maceswinger.test;

public class Reward
{
	private int xp; //something i dunno
	private Object loot;

	public Reward(int xp, Object loot)
	{
		this.xp = xp;
		this.loot = loot;
	}

	public int getXP()
	{
		return this.xp;
	}

	public Object getLoot()
	{
		return this.loot;
	}
}
