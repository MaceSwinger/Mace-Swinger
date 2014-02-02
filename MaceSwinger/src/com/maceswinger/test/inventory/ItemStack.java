package com.maceswinger.test.inventory;

public class ItemStack
{
	private Item baseItem;
	private int count;

	public ItemStack(Item i)
	{
		this.baseItem = i;
		this.count = 0;
	}

	public void addItem(Item i)
	{
		if (!i.equals(baseItem) || isFull())
			return;
		this.count++;
	}

	public void removeItem(Item i)
	{
		if (!i.equals(baseItem) || isEmpty())
			return;
		this.count--;
	}

	public Item getBaseItem()
	{
		return this.baseItem;
	}

	public int getStackCount()
	{
		return this.count;
	}

	public int getMaxStackCount()
	{
		return this.baseItem.getMaxStackCount();
	}

	public boolean isFull()
	{
		return this.count == this.baseItem.getMaxStackCount();
	}

	public boolean isEmpty()
	{
		return this.count == 0;
	}
}
