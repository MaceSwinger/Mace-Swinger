package core.spawns;

import org.magnos.entity.Entity;
import org.magnos.entity.EntityList;

import com.maceswinger.Animation;
import com.maceswinger.Components;
import com.maceswinger.Rectangle;
import com.maceswinger.client.render.AnimationRenderer;
import com.maceswinger.map.MapObject;

public class Tile implements MapObject
{
	@Override
	public Object spawn(EntityList list, int x, int y, String... params)
	{
		Entity tile = new Entity();
		tile.add(Components.animation);
		tile.set(Components.animation, new Animation(new Animation.Frame(params[0], 50)));

		tile.add(Components.position);
		tile.add(core.Core.Components.collider);

		tile.setRenderer(new AnimationRenderer("tileset"));

		tile.get(Components.position).x = x * 32;
		tile.get(Components.position).y = y * 32;
		tile.set(core.Core.Components.collider, new Rectangle(x * 32, y * 32, 32, 32));
		list.add(tile);
		return tile;
	}

	public String toString()
	{
		return "Tile";
	}
}
