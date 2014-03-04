package com.maceswinger.items;

public class MaceShaft {
	
EnumMaterial MATERIAL;
	
	public MaceShaft(int mat){
		this.MATERIAL=EnumMaterial.getMaterial(mat);
	}
}
