package com.maceswinger.items;


public enum EnumMaterial {
	PLASTIC(0,1,"Plastic"),WOOD(1,2,"Wooden"),IRON(2,4,"Iron"),SILVER(3,5,"Silver"), ;

    private int matId;
    private int power;
    private String name;
    private EnumMaterial(int levelId, int pwr,String name) {
        this.matId = levelId;
        this.power=pwr;
        this.name=name;
    }

    public int getId() {
        return matId;
    }
    public int getPower() {
		return power;
	}
    public String getName() {
		return name;
	}
    public static EnumMaterial getMaterial(int id) {
        for (EnumMaterial  l : EnumMaterial.values()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return EnumMaterial.PLASTIC;
    }


}
