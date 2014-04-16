
package com.maceswinger.items;

import java.util.Random;



public class Material {
	
	public static final Material[] mats = new Material[256];
	public static final Material PLASTIC = new Material(3,"Plastic",100);
	public static final Material WOOD = new Material(7,"Wood",200);
	public static final Material STONE = new Material(11,"Stone",150);
	public static final Material COPPER = new Material(15,"Copper",100);
	public static final Material IRON = new Material(19,"Iron",60);
	public static final Material SILVER = new Material(23,"Silver",40);
	public static final Material GOLD = new Material(27,"Gold",20);
	public static final Material PLATINUM = new Material(31,"Platinum",10);
	public static final Material DIAMOND = new Material(35,"Diamond",4);
	
	public static int SIZE;
    private byte id;
    private int power;
    private String name;
    private int rareness;
    public Material(int pwr,String name, int rareness) {
    	this.id = (byte)SIZE;
		if(mats[SIZE] != null) throw new RuntimeException("Duplicant materiala id at " + SIZE);
        this.power=pwr;
        this.name=name;
        this.rareness=rareness;
        mats[id] = this;
        SIZE++;
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
	public static int[] rarenessGrid(){
		int[] r = new int[SIZE];
		 for (int i=0;i<SIZE;i++) {
			 Material m = getMaterial(i);
			 r[i]=m.getRareness();
		 }
		 return r;
	}
	public static int totalRareness(){
		int r=0;
		for (int i=0;i<SIZE;i++) {
			 Material m = getMaterial(i);
			 r+=m.getRareness();
		 }
		 return r;
	}
	private int getRareness() {
		return this.rareness;
	}

	public static int getRandomMaterial(int level) {
		int cumulative_weight = 0;
		int[] weight = rarenessGrid();
				for (int i=0;i<weight.length;i++){
				    cumulative_weight += weight[i];
				    int x = new Random().nextInt(totalRareness());
				    if( x < cumulative_weight){
				        return i;
				    }
				}
		return 0;
	}
}
