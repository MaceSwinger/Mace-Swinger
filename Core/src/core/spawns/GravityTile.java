package core.spawns;

import org.magnos.entity.Entity;
import org.magnos.entity.EntityList;

import com.maceswinger.map.MapObject;

import core.Core;

public class GravityTile extends Tile implements MapObject
{
	@Override
	public Object spawn(EntityList list, int x, int y, String... params)
	{
		Entity tile = (Entity) super.spawn(list, x, y, params);

		tile.add(Core.Components.velocity);
		tile.add(Core.Controllers.gravity);
		tile.add(Core.Controllers.velocity);

		list.add(tile);
		return (Object) tile;
	}

	public String toString()
	{
		return "Gravity Tile";
	}
}
