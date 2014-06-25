package com.maceswinger;

import org.magnos.entity.Component;
import org.magnos.entity.Ents;
import org.magnos.entity.vals.IntVal;

import com.maceswinger.Animation;
import com.maceswinger.Color;
import com.maceswinger.Sprite;
import com.maceswinger.Vector2;
import com.maceswinger.client.render.lighting.Block;
import com.maceswinger.net.Register;

public class Components
{
	public static Component<Vector2> position = Ents.newComponent("position", new Vector2());
	public static Component<IntVal> direction = Ents.newComponent("direction", new IntVal());
	public static Component<Color> color = Ents.newComponent("color", new Color());
	public static Component<Sprite> sprite = Ents.newComponent("sprite", new Sprite());
	public static Component<Animation> animation = Ents.newComponent("animation", new Animation());
	public static Component<ItemVal> heldItem = Ents.newComponent("heldItem", new ItemVal());
	public static Component<Block> block = Ents.newComponent("block", new Block());
	{
		Register.components.add(position);
		Register.components.add(direction);
		Register.components.add(color);
		Register.components.add(sprite);
		Register.components.add(animation);
		Register.components.add(heldItem);
		Register.components.add(block);
	}
}