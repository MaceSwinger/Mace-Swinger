package com.maceswinger.items;



public class Material {
	
	public static final Material[] mats = new Material[256];
	public static final Material PLASTIC = new Material(0, 3,"Plastic");
	public static final Material WOOD = new Material(1, 7,"Wood");
	public static final Material STONE = new Material(2, 11,"Stone");
	public static final Material COPPER = new Material(3, 15,"Copper");
	public static final Material IRON = new Material(4, 19,"Iron");
	public static final Material SILVER = new Material(5, 23,"Silver");
	public static final Material GOLD = new Material(6, 27,"Gold");
	public static final Material PLATINUM = new Material(7, 31,"Platinum");
	public static final Material DIAMOND = new Material(8, 35,"Diamond");
	
    private byte id;
    private int power;
    private String name;
    public Material(int i, int pwr,String name) {
    	this.id = (byte)i;
		if(mats[i] != null) throw new RuntimeException("Duplicant materiala id at " + i);
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
  
	public static Material getMaterial(int id) {
        for (Material  l : mats) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

}
