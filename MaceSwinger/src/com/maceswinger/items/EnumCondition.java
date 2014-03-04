package com.maceswinger.items;

public enum EnumCondition {
	INVALID(-1, 0, "invalid"), SHITTY(0, -2, "Shitty"), USED(1, -1, "Used"), 
	AVERAGE(2, 0, "Average"), FINE(3, 1, "Fine"),EXCELLENT(4, 2, "Excellent"), ;

    private int id;
    private int power;
    private String name;
    private EnumCondition(int id, int pwr,String name) {
        this.id = id;
        this.power=pwr;
        this.name=name;
    }

    public int getId() {
        return id;
    }
    public int getPower() {
		return power;
	}
    public String getName() {
		return name;
	}
    public static EnumCondition getCondition(int id) {
        for (EnumCondition  l : EnumCondition.values()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return EnumCondition.INVALID;
    }


}