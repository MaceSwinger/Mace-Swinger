
package com.maceswinger.items;

import java.util.Random;



public class Condition {
	
	public static final Condition[] mats = new Condition[256];
	public static final Condition SHITTY = new Condition(-2,"Shitty",20);
	public static final Condition USED = new Condition(-1,"Used",40);
	public static final Condition AVERAGE = new Condition(0,"Average",100);
	public static final Condition FINE = new Condition(1,"Fine",40);
	public static final Condition EXCELLENT = new Condition(2,"Excellent",20);
	
	public static int SIZE;
    private byte id;
    private int power;
    private String name;
    private int rareness;
    public Condition(int pwr,String name, int rareness) {
    	this.id = (byte)SIZE;
		if(mats[SIZE] != null) throw new RuntimeException("Duplicant condition id at " + SIZE);
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
  
	public static Condition getCondition(int id) {
        for (Condition  l : mats) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }
	public static int[] rarenessGrid() {
		int[] r = new int[SIZE];
		for (int i = 0; i < SIZE; i++) {
			Condition e = getCondition(i);
			r[i] = e.getRareness();
		}
		return r;
	}

	public static int totalRareness() {
		int r = 0;
		for (int i = 0; i < SIZE; i++) {
			Condition m = getCondition(i);
			r += m.getRareness();
		}
		return r;
	}

	private int getRareness() {
		return this.rareness;
	}

	public static int getRandomCondition(int level) {
		int cumulative_weight = 0;
		int[] weight = rarenessGrid();
		for (int i = 0; i < weight.length; i++) {
			cumulative_weight += weight[i];
			int x = new Random().nextInt(totalRareness());
			if (x < cumulative_weight) {
				return i;
			}
		}
		return 0;
	}
}
