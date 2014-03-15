
package com.maceswinger.items;

import java.util.Random;

public class MaceTestMain {

	private Random rand = new Random();
	private Item[] maces = new Item[100];
	public static void main(String[] args) {
		MaceTestMain m = new MaceTestMain();
		m.testMaces();
	}

	private void testMaces() {
	
		for(int i=0;i<maces.length;i++){
			maces[i] = ItemMace.createMace();
			System.out.println(maces[i].getName()+"\t\tDmg: "+maces[i].getDamage());
		}
	}
		
}

