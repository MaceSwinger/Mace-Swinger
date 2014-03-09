package com.maceswinger.items;

public class ItemMace extends Item {

	private MaceHandle handle;
	private MaceShaft shaft;
	private MaceHead head;
	Condition CONDITION;
	Elemental ELEMENTAL;

	public ItemMace(MaceHandle handle, MaceShaft shaft, MaceHead head,
			int condition, int elem) {
		this.handle = handle;
		this.head = head;
		this.shaft = shaft;
		this.CONDITION = Condition.getCondition(condition);
		this.ELEMENTAL = Elemental.getElemental(elem);
	}

	public int getDamage(){
		return head.MATERIAL.getPower()+this.CONDITION.getPower();
	}
	@Override
	public String getName() {
		if(ELEMENTAL != null){
		return this.CONDITION.getName() + " " + this.head.MATERIAL.getName()
				+ " Mace of " + this.ELEMENTAL.getName();
		}else{
			return this.CONDITION.getName() + " " + this.head.MATERIAL.getName()
					+ " Mace";
		}

	}
}
