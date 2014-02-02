package core.controllers;

import java.util.HashMap;

import org.magnos.entity.Entity;
import org.magnos.entity.EntityIterator;
import org.magnos.entity.filters.ComponentFilter;

import com.maceswinger.Vector2;
import com.maceswinger.client.ClientProgram;

import core.Core;

public class JumpMOOFIXMEYOUFUCKFACE {
	private static HashMap<Entity, Integer> jumpCooldowns = new HashMap<Entity, Integer>();
	public static void jump(Entity e, Object updateState, Vector2 velocity)
	{
		if (jumpCooldowns.get(e) == null)
			jumpCooldowns.put(e, 35);
		else
		{
			ClientProgram program = (ClientProgram) updateState;
			for (Entity ent : new EntityIterator(program.entities, new ComponentFilter(Core.Components.collider)))
				if (ent != e)
					if (e.get(Core.Components.collider).overlaps(new Vector2(0, -1), ent.get(Core.Components.collider)))
						velocity.y = 16;
			if (velocity.y > -1)
				velocity.y += 0.3f;
			jumpCooldowns.put(e, jumpCooldowns.get(e) - 1);
		}
		if (jumpCooldowns.get(e) == 0)
			jumpCooldowns.remove(e);
	}
}
