package core.controllers;

import org.magnos.entity.Control;
import org.magnos.entity.Entity;
import org.magnos.entity.EntityIterator;
import org.magnos.entity.filters.ComponentFilter;

import com.maceswinger.Components;
import com.maceswinger.Vector2;
import com.maceswinger.client.GameClient;

import core.Core;

public class ControlVelocity implements Control {
	@Override
	public void update(Entity e, Object updateState)
	{
		GameClient program = (GameClient) updateState;
		if (e.has(Components.position) && e.has(core.Core.Components.velocity))
		{
			Vector2 pos = e.get(Components.position);
			Vector2 velocity = e.get(core.Core.Components.velocity);
			Vector2 previousPos = new Vector2(pos);
			if (Math.abs(velocity.x) > Math.abs(velocity.y))
			{
				velocityMoveX(e, program.entities, pos, velocity);
				velocityMoveY(e, program.entities, pos, velocity);
			}
			else
			{
				velocityMoveY(e, program.entities, pos, velocity);
				velocityMoveX(e, program.entities, pos, velocity);
			}
			e.get(Core.Components.collider).x += pos.x - previousPos.x;
			e.get(Core.Components.collider).y += pos.y - previousPos.y;
			velocity.x *= 0.9f;
			velocity.y *= 0.9f;
		}
	}

	private void velocityMoveX(Entity e, Entity collisionRoot, Vector2 pos, Vector2 velocity)
	{
		if (e.has(Core.Components.collider))
		{
			for (Entity ent : new EntityIterator(collisionRoot, new ComponentFilter(Core.Components.collider)))
			{
				if (ent != e && ent.get(Core.Components.collider).overlaps(new Vector2(-velocity.x, 0), e.get(Core.Components.collider)))
				{
					velocity.x -= velocity.x > 0 ? 1.5f : 0;
					velocity.x += velocity.x < 0 ? 1.5f : 0;
					return;
				}
			}
		}
		pos.x += velocity.x;
	}

	private void velocityMoveY(Entity e, Entity collisionRoot, Vector2 pos, Vector2 velocity)
	{
		if (e.has(Core.Components.collider))
		{
			for (Entity ent : new EntityIterator(collisionRoot, new ComponentFilter(Core.Components.collider)))
			{
				if (ent != e && ent.get(Core.Components.collider).overlaps(new Vector2(0, -velocity.y), e.get(Core.Components.collider)))
				{
					velocity.y -= velocity.y > 0 ? 1.5f : 0;
					velocity.y += velocity.y < 0 ? 1.5f : 0;
					return;
				}
			}
		}
		pos.y += velocity.y;
	}
}
