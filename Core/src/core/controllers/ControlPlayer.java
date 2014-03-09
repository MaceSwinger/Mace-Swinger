package core.controllers;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.magnos.entity.Control;
import org.magnos.entity.Entity;

import com.maceswinger.Components;
import com.maceswinger.Vector2;
import com.maceswinger.client.render.SpriteRenderer;
import com.maceswinger.test.inventory.Inventory;
import com.maceswinger.test.inventory.Item;
import com.maceswinger.test.inventory.ItemStack;

import core.Core;

public class ControlPlayer implements Control
{
	@Override
	public void update(Entity e, Object updateState)
	{
		Vector2 velocity = e.get(Core.Components.velocity);
		Vector2 pos = e.get(Components.position);

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			Jump.jump(e, updateState, velocity);

		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			velocity.x -= 0.5f;

		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			velocity.x += 0.5f;

		if (Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			Inventory inv = e.get(Core.Components.inventory);
			System.out.print("items:" + inv.getStacks().size() + " [");
			for (ItemStack item : inv.getStacks())
				System.out.print(item.getBaseItem() + ":" + item.getStackCount() + " ");
			System.out.println("]");
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			e.get(Core.Components.inventory).addItem(new Item("test item 1", 3, false));

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			e.get(Core.Components.inventory).addItem(new Item("test item 2", 1, false));

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			e.get(Core.Components.inventory).addItem(new Item("name" + new Random().nextInt(10), 64, false));

		//		int left = 100;
		//		int right = 700;
		//		if (pos.x - SpriteRenderer.mainCamera.x <= left)
		//			SpriteRenderer.mainCamera.x = pos.x - left;
		//		if (pos.x - SpriteRenderer.mainCamera.x >= right)
		//			SpriteRenderer.mainCamera.x = pos.x - right;

		SpriteRenderer.mainCamera.x = pos.x - 400 + 16;
		SpriteRenderer.mainCamera.y = pos.y - 300 + 16;
	}
}
