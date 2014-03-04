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
			MaceHead a = new MaceHead(rand.nextInt(5));
			MaceShaft b = new MaceShaft(rand.nextInt(5));
			MaceHandle c = new MaceHandle(rand.nextInt(5));
			maces[i]= new ItemMace(c,b,a,rand.nextInt(5),rand.nextInt(4)-1);
			System.out.println(maces[i].getName());
		}
	}
		
}
