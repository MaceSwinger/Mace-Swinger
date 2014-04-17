package core.controllers;

import org.lwjgl.input.Keyboard;
import org.magnos.entity.Control;
import org.magnos.entity.Entity;
import org.magnos.entity.vals.IntVal;

import com.maceswinger.Components;
import com.maceswinger.Vector2;
import com.maceswinger.client.render.SpriteRenderer;
import com.maceswinger.utils.Key;

import core.Core;

public class ControlPlayer implements Control {
	@Override
	public void update(Entity e, Object updateState) {
		Vector2 velocity = e.get(Core.Components.velocity);
		Vector2 pos = e.get(Components.position);
		IntVal direction = e.get(Components.direction);
		if (Key.isKeyDown(Key.JUMP))
			Jump.jump(e, updateState, velocity);
		if (Key.isKeyDown(Key.ATTACK)) {
			//Attack.attack(e, updateState, direction);
		}
		if (Key.isKeyDown(Key.LEFT)){ 
			velocity.x -= 0.5f;
			direction.v = 0;
		}
		if (Key.isKeyDown(Key.RIGHT)) {
			velocity.x += 0.5f;
			direction.v = 1;
		}
		if (Key.isKeyDown(Key.INVENTORY)) {
			// open inventory
		}

		SpriteRenderer.mainCamera.x = pos.x - 400 + 16;
		SpriteRenderer.mainCamera.y = pos.y - 300 + 16;
	}
}
