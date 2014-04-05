package com.maceswinger;

import org.magnos.entity.Component;
import org.magnos.entity.Ents;
import org.magnos.entity.vals.IntVal;

import com.maceswinger.items.Item;
import com.maceswinger.net.Register;

public class Components
{
	public static Component<Vector2> position = Ents.newComponent("position", new Vector2());
	public static Component<IntVal> direction = Ents.newComponent("direction", new IntVal());
	public static Component<Color> color = Ents.newComponent("color", new Color());
	public static Component<Sprite> sprite = Ents.newComponent("sprite", new Sprite());
	public static Component<Animation> animation = Ents.newComponent("animation", new Animation());
	//public static Component<Item> heldItem = Ents.newComponent("heldItem", new Item());
	{
		Register.components.add(position);
		Register.components.add(direction);
		Register.components.add(color);
		Register.components.add(sprite);
		Register.components.add(animation);
		//Register.components.add(heldItem);
	}
}