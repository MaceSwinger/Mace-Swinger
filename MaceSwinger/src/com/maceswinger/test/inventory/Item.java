package com.maceswinger.test.inventory;

public class Item
{
	private int maxStackCount;
	private boolean consumable;
	private String name;

	public Item(String name, int maxStackCount, boolean consumable)
	{
		this.maxStackCount = maxStackCount;
		this.consumable = consumable;
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public int getMaxStackCount()
	{
		return this.maxStackCount;
	}

	public boolean isConsumable()
	{
		return this.consumable;
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof Item))
			return false;
		Item temp = (Item) o;
		return temp.name.equals(this.name) && temp.maxStackCount == this.maxStackCount && temp.consumable == this.consumable;
	}

	public String toString()
	{
		return this.name;
	}
}