package core;

import org.magnos.entity.Component;
import org.magnos.entity.Controller;
import org.magnos.entity.Ents;
import org.magnos.entity.vals.FloatVal;

import com.maceswinger.Camera;
import com.maceswinger.Rectangle;
import com.maceswinger.Vector2;
import com.maceswinger.map.MapLoader;
import com.maceswinger.mods.Mod;
import com.maceswinger.mods.ModInfo;
import com.maceswinger.net.Register;
import com.maceswinger.server.ServerProgram;
import com.maceswinger.test.inventory.Inventory;

import core.controllers.ControlGoblinAI;
import core.controllers.ControlGravity;
import core.controllers.ControlPlayer;
import core.controllers.ControlVelocity;
import core.spawns.GoblinSpawn;
import core.spawns.GravityTile;
import core.spawns.PlayerSpawn;
import core.spawns.Tile;

@ModInfo
public class Core extends Mod
{
	public static class Controllers
	{
		public static Controller gravity = Ents.newController("gravity", new ControlGravity());
		public static Controller velocity = Ents.newController("velocity", new ControlVelocity());
		public static Controller player = Ents.newController("player", new ControlPlayer());
		public static Controller goblinAi = Ents.newController("goblin_ai", new ControlGoblinAI());
	}

	public static class Components
	{
		public static Component<Vector2> velocity;

		public static Component<FloatVal> rotation;
		public static Component<Rectangle> collider;

		public static Component<Inventory> inventory;
	}

	public static Camera mainCamera = new Camera(0, 0);

	@Override
	public void info()
	{
		name = "MS Core";
		desc = "Main data package for Mace Swinger";
	}

	@Override
	public void init()
	{
		Components.velocity = Ents.newComponent("velocity", new Vector2());
		Components.rotation = Ents.newComponent("rotation", new FloatVal());
		Components.collider = Ents.newComponent("rectangle", new Rectangle());
		Components.inventory = Ents.newComponent("inventory", new Inventory());

		MapLoader.addMapObject("PlayerSpawn", new PlayerSpawn());
		MapLoader.addMapObject("GoblinSpawn", new GoblinSpawn());
		MapLoader.addMapObject("Tile", new Tile());
		MapLoader.addMapObject("GravityTile", new GravityTile());

		//Register.components.add(Components.velocity);
		Register.components.add(Components.rotation);
		Register.components.add(Components.collider);
		Register.components.add(Components.inventory);

		Register.controllers.add(Controllers.goblinAi);
		Register.controllers.add(Controllers.velocity);
		Register.controllers.add(Controllers.gravity);
		Register.controllers.add(Controllers.player);

		ServerProgram.onPlayerConnect = new Runnable()
		{
			public void run()
			{
				ServerProgram.entities.add(PlayerSpawn.createNewPlayer(ServerProgram.entities, (int) ServerProgram.playerSpawn.x, (int) ServerProgram.playerSpawn.y));
			}
		};
	}
}