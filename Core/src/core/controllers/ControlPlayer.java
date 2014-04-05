package core.controllers;

import org.lwjgl.input.Keyboard;
import org.magnos.entity.Control;
import org.magnos.entity.Entity;
import org.magnos.entity.vals.IntVal;

import com.maceswinger.Components;
import com.maceswinger.Vector2;
import com.maceswinger.client.render.SpriteRenderer;

import core.Core;

public class ControlPlayer implements Control {
	@Override
	public void update(Entity e, Object updateState) {
		Vector2 velocity = e.get(Core.Components.velocity);
		Vector2 pos = e.get(Components.position);
		IntVal direction = e.get(Components.direction);
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			Jump.jump(e, updateState, velocity);
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			//Attack.attack(e, updateState, direction);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			velocity.x -= 0.5f;
			direction.v = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			velocity.x += 0.5f;
			direction.v = 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			// open inventory
		}

		SpriteRenderer.mainCamera.x = pos.x - 400 + 16;
		SpriteRenderer.mainCamera.y = pos.y - 300 + 16;
	}
}
