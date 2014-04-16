package com.maceswinger.items;

import java.util.Random;

public class ItemMace extends Item {

	private MaceHandle handle;
	private MaceShaft shaft;
	private MaceHead head;
	Condition CONDITION;
	Elemental ELEMENTAL;

	// mace on derriere
	public static final ItemMace[] legendaries = new ItemMace[2];
	public static final ItemMace MAISONDERRIERE = new ItemMace(0,"Mace on Derriere", 100);
	public static final ItemMace STARTERMACE = new ItemMace(1,"Crumy old stick", 1);

	private byte id;
	private int damage;
	private String name;

	public ItemMace(int id, String name, int dmg) {
		this.id = (byte) id;
		if (legendaries[id] != null)
			throw new RuntimeException("Duplicant legendary id at " + id);
		this.damage = dmg;
		this.name = name;
		legendaries[id] = this;

	}

	public ItemMace(MaceHandle handle, MaceShaft shaft, MaceHead head,
			int condition, int elem) {
		this.handle = handle;
		this.head = head;
		this.shaft = shaft;
		this.CONDITION = Condition.getCondition(condition);
		this.ELEMENTAL = Elemental.getElemental(elem);
		this.setName();
	}

	public int getDamage() {
		return head.MATERIAL.getPower() + this.CONDITION.getPower();
	}

	public void setName() {

		this.name = this.CONDITION.getName() + " "
				+ this.head.MATERIAL.getName() + " Mace "
				+ this.ELEMENTAL.getName();

	}
	public String getName(){
		return name;
	}
	public static String getTotalUniqueMaces(){
		
		int r = (int) Math.pow(3, Material.SIZE)*Condition.SIZE*Elemental.SIZE*4+legendaries.length;
		return r+" maces in total!";
		
	}
	public static Item createMace() {
		if(new Random().nextInt(100000)==0){//one in one millon to get legendary mace
			return MAISONDERRIERE;
		}
		MaceHead a = new MaceHead(Material.getRandomMaterial(0));
		MaceShaft b = new MaceShaft(Material.getRandomMaterial(0));
		MaceHandle c = new MaceHandle(Material.getRandomMaterial(0));
		return new ItemMace(c, b, a, Condition.getRandomCondition(0),
				Elemental.getRandomElemental(0));
	}
}
