package core.controllers;

import org.magnos.entity.Control;
import org.magnos.entity.Entity;

import core.Core;

public class ControlGravity implements Control {
	@Override
	public void update(Entity e, Object updateState) {
		if (e.has(Core.Components.velocity))
			e.get(Core.Components.velocity).y -= 1f;
	}
}
