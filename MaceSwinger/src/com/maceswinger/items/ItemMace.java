package com.maceswinger.items;

public class ItemMace extends Item {

	private MaceHandle handle;
	private MaceShaft shaft;
	private MaceHead head;
	Condition CONDITION;
	Elemental ELEMENTAL;

	//mace on derriere
	
	public ItemMace(MaceHandle handle, MaceShaft shaft, MaceHead head,
			int condition, int elem) {
		this.handle = handle;
		this.head = head;
		this.shaft = shaft;
		this.CONDITION = Condition.getCondition(condition);
		this.ELEMENTAL = Elemental.getElemental(elem);
	}

	public int getDamage() {
		return head.MATERIAL.getPower() + this.CONDITION.getPower();
	}

	@Override
	public String getName() {

		return this.CONDITION.getName() + " " + this.head.MATERIAL.getName()
				+ " Mace " + this.ELEMENTAL.getName();

	}
	public static Item createMace(){
		MaceHead a = new MaceHead(Material.getRandomMaterial(0));
		MaceShaft b = new MaceShaft(Material.getRandomMaterial(0));
		MaceHandle c = new MaceHandle(Material.getRandomMaterial(0));
		return new ItemMace(c,b,a,Condition.getRandomCondition(0),Elemental.getRandomElemental(0));
	}
}
