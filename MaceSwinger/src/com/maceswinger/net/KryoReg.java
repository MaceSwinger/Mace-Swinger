package com.maceswinger.net;

import java.util.ArrayList;

import org.magnos.entity.vals.IntVal;

import com.esotericsoftware.kryo.Kryo;
import com.maceswinger.Animation;
import com.maceswinger.ItemVal;
import com.maceswinger.Rectangle;
import com.maceswinger.Vector2;
import com.maceswinger.client.render.lighting.Block;
import com.maceswinger.test.inventory.Inventory;
import com.maceswinger.test.inventory.ItemStack;

/**
 * Registers serializables
 * 
 * @since Feb 2, 2014
 */
public class KryoReg
{
	/**
	 * Registers all the serializable classes
	 * 
	 * @param k
	 *            Kryo instance to register to
	 */
	public static void reg(Kryo k)
	{
		k.register(IntVal.class);
		k.register(Vector2.class);
		k.register(Rectangle.class);
		k.register(Inventory.class);
		k.register(ItemStack.class);
		k.register(ItemVal.class);
		k.register(Block.class);
		k.register(ArrayList.class);
		k.register(Animation.class);
		k.register(Animation.Frame.class);
		k.register(Animation.Frame[].class);

		k.register(MessageNewEntity.class);
		k.register(MessageSetComponent.class);
		k.register(MessageSetController.class);
		k.register(MessageSetRenderer.class);
	}
}
