package com.maceswinger.items;

public class MaceHandle {

	EnumMaterial MATERIAL;
	
	public MaceHandle(int mat){
		this.MATERIAL=EnumMaterial.getMaterial(mat);
	}
}
