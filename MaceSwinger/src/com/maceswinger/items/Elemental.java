package com.maceswinger.items;

import java.util.Random;

public class Elemental {

	public static final Elemental[] elems = new Elemental[256];
	public static final Elemental NONE = new Elemental("", 100);
	public static final Elemental FIRE = new Elemental("of Fire", 20);
	public static final Elemental POISON = new Elemental("of Poison", 20);
	public static final Elemental FROST = new Elemental("of Frost", 20);

	private static int SIZE;
	private byte id;
	private String name;
	private int rareness;

	private Elemental(String name, int rareness) {
		this.id = (byte) SIZE;
		if (elems[SIZE] != null)
			throw new RuntimeException("Duplicant elemental id at " + SIZE);
		this.name = name;
		this.rareness = rareness;
		elems[id] = this;
		SIZE++;
		
	}

	public byte getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static Elemental getElemental(int id) {
		for (Elemental l : elems) {
			if (l.getId() == id) {
				return l;
			}
		}
		return null;
	}

	public static int[] rarenessGrid() {
		int[] r = new int[SIZE];
		for (int i = 0; i < SIZE; i++) {
			Elemental e = getElemental(i);
			r[i] = e.getRareness();
		}
		return r;
	}

	public static int totalRareness() {
		int r = 0;
		for (int i = 0; i < SIZE; i++) {
			Elemental m = getElemental(i);
			r += m.getRareness();
		}
		return r;
	}

	private int getRareness() {
		return this.rareness;
	}

	public static int getRandomElemental(int level) {
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
