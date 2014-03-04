package com.maceswinger.items;

public enum EnumElemental {
	INVALID(-1,"invalid"), FIRE(0, "Fire"), POISON(1,"Poison"), 
	FROST(2,"Frost");

    private int id;
    private String name;
    private EnumElemental(int id,String name) {
        this.id = id;
        this.name=name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
		return name;
	}
    public static EnumElemental getElemental(int id) {
        for (EnumElemental  l : EnumElemental.values()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return EnumElemental.INVALID;
    }


}
