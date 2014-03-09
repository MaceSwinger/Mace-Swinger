package com.maceswinger.items;



public class Condition {
	
	public static final Condition[] mats = new Condition[256];
	public static final Condition SHITTY = new Condition(0, -2,"Shitty");
	public static final Condition USED = new Condition(1, -1,"Used");
	public static final Condition AVERAGE = new Condition(2, 0,"Average");
	public static final Condition FINE = new Condition(3, 1,"Fine");
	public static final Condition EXCELLENT = new Condition(4, 2,"Excellent");
	
	
    private byte id;
    private int power;
    private String name;
    private Condition(int i, int pwr,String name) {
    	this.id = (byte)i;
		if(mats[i] != null) throw new RuntimeException("Duplicant condition id at " + i);
        this.power=pwr;
        this.name=name;
        mats[id] = this;
    }

    public byte getId() {
        return id;
    }
    public int getPower() {
		return power;
	}
    public String getName() {
		return name;
	}
  
	public static Condition getCondition(int id) {
        for (Condition  l : mats) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

}
