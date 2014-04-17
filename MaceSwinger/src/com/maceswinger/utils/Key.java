package com.maceswinger.utils;

import org.lwjgl.input.Keyboard;

import com.maceswinger.items.Material;

public class Key {

	public static final Key[] keys = new Key[256];
	public static final Key UP = new Key(0, Keyboard.KEY_W);
	public static final Key DOWN = new Key(1, Keyboard.KEY_S);
	public static final Key LEFT = new Key(2, Keyboard.KEY_A);
	public static final Key RIGHT = new Key(3, Keyboard.KEY_D);
	public static final Key JUMP = new Key(4, Keyboard.KEY_SPACE);
	public static final Key ATTACK = new Key(5, Keyboard.KEY_LSHIFT);
	public static final Key INVENTORY = new Key(6, Keyboard.KEY_E);

	
	int key;
	byte id;
	
	
	public Key(int id, int key){
		this.id = (byte) id;
		if (keys[id] != null)
			throw new RuntimeException("Duplicant key id at " + id);
		this.key = key;
		keys[id] = this;
	}

	
	public static boolean isKeyDown(Key key) {
		if (Keyboard.isKeyDown(key.key))
			return true;
		return false;

	}

	public Key setKey(Key key, int newKey) {
		this.key=newKey;
		return this;
	}
}
