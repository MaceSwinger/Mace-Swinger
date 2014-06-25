package core.controllers;

import java.util.Random;

import org.magnos.entity.Control;
import org.magnos.entity.Entity;

import com.maceswinger.Vector2;

import core.Core;

public class ControlGoblinAI implements Control
{
	@Override
	public void update(Entity e, Object updateState)
	{
		Vector2 velocity = e.get(Core.Components.velocity);
		//		velocity.x += 1;
		if (new Random().nextInt(10) == 5)
		{
			velocity.x += new Random().nextFloat() * (new Random().nextBoolean() ? -1f : 1f);
			Jump.jump(e, updateState, velocity);
		}
	}
}
