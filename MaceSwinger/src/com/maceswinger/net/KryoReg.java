package com.maceswinger.net;

import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.maceswinger.Animation;
import com.maceswinger.Camera;
import com.maceswinger.Rectangle;
import com.maceswinger.Vector2;
import com.maceswinger.test.inventory.Inventory;
import com.maceswinger.test.inventory.Item;
import com.maceswinger.test.inventory.ItemStack;

public class KryoReg {
	public static void reg(Kryo k) {
		k.register(Vector2.class);
		k.register(Rectangle.class);
		k.register(Camera.class);
		k.register(Inventory.class);
		k.register(ItemStack.class);
		k.register(Item.class);
		k.register(ArrayList.class);
		k.register(Animation.class);
		k.register(Animation.Frame.class);
		k.register(Animation.Frame[].class);
		
		k.register(MessageNewEntity.class);
		k.register(MessageSetComponent.class);
		k.register(MessageSetRenderer.class);
	}
}
