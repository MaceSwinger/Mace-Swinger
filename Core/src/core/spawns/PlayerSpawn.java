package core.spawns;

import org.magnos.entity.Entity;
import org.magnos.entity.EntityList;

import com.maceswinger.Animation;
import com.maceswinger.Components;
import com.maceswinger.Rectangle;
import com.maceswinger.Vector2;
import com.maceswinger.client.render.AnimationRenderer;
import com.maceswinger.map.MapObject;
import com.maceswinger.server.ServerProgram;

import core.Core;

public class PlayerSpawn implements MapObject
{
	@Override
	public Object spawn(EntityList list, int x, int y, String... params)
	{
		ServerProgram.playerSpawn.x = x;
		ServerProgram.playerSpawn.y = y;
		return null;
	}

	public static Entity create(int x, int y)
	{
		Entity player = new Entity();
		player.add(Core.Controllers.player);
		player.add(Core.Controllers.velocity);
		player.add(Core.Controllers.gravity);

		player.add(Components.animation);
		player.add(Components.position);
		player.add(Components.camera);

		player.add(Core.Components.collider);
		player.add(Core.Components.velocity);
		player.add(Core.Components.inventory);

		player.set(Components.camera, Core.mainCamera);
		player.set(Components.animation, new Animation(new Animation.Frame("player_walk_1", 5), new Animation.Frame("player_walk_2", 5), new Animation.Frame("player_walk_3", 5), new Animation.Frame("player_walk_4", 5)));
		player.set(Core.Components.collider, new Rectangle(x * 32 + 3, y * 32 + 1, 17, 48));
		player.set(Components.position, new Vector2(x * 32, y * 32));

		player.setRenderer(new AnimationRenderer("mob"));

		return player;
	}
}
