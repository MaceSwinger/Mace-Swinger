package com.maceswinger.items;

public class ItemMace extends Item {

	private MaceHandle handle;
	private MaceShaft shaft;
	private MaceHead head;
	EnumCondition CONDITION;
	EnumElemental ELEMENTAL;

	public ItemMace(MaceHandle handle, MaceShaft shaft, MaceHead head,
			int condition, int elem) {
		this.handle = handle;
		this.head = head;
		this.shaft = shaft;
		this.CONDITION = EnumCondition.getCondition(condition);
		this.ELEMENTAL = EnumElemental.getElemental(elem);
	}


	@Override
	public String getName() {
		if(ELEMENTAL != EnumElemental.INVALID){
		return this.CONDITION.getName() + " " + this.head.MATERIAL.getName()
				+ " Mace of " + this.ELEMENTAL.getName();
		}else{
			return this.CONDITION.getName() + " " + this.head.MATERIAL.getName()
					+ " Mace";
		}

	}
}
