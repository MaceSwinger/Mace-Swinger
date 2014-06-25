package core;

import org.magnos.entity.Component;
import org.magnos.entity.Controller;
import org.magnos.entity.Ents;
import org.magnos.entity.vals.FloatVal;
import org.magnos.entity.vals.IntVal;

import com.maceswinger.Rectangle;
import com.maceswinger.Vector2;
import com.maceswinger.map.MapLoader;
import com.maceswinger.mods.Mod;
import com.maceswinger.mods.ModInfo;
import com.maceswinger.net.Register;
import com.maceswinger.server.GameServer;
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
		public static Controller gravity;
		public static Controller velocity;
		public static Controller player;
		public static Controller goblinAi;
	}

	public static class Components
	{
		public static Component<Vector2> velocity;

		public static Component<FloatVal> rotation;
		public static Component<Rectangle> collider;

		public static Component<Inventory> inventory;
		public static Component<IntVal> jumpCooldown;
		public static Component<IntVal> attackCooldown;
	}

	@Override
	public void info()
	{
		name = "MS Core";
		desc = "Main data package for Mace Swinger";
	}

	@Override
	public void init()
	{
		Controllers.gravity = Ents.newController("gravity", new ControlGravity());
		Controllers.velocity = Ents.newController("velocity", new ControlVelocity());
		Controllers.player = Ents.newController("player", new ControlPlayer());
		Controllers.goblinAi = Ents.newController("goblin_ai", new ControlGoblinAI());

		Components.velocity = Ents.newComponent("velocity", new Vector2());
		Components.rotation = Ents.newComponent("rotation", new FloatVal());
		Components.collider = Ents.newComponent("rectangle", new Rectangle());
		Components.inventory = Ents.newComponent("inventory", new Inventory());
		Components.jumpCooldown = Ents.newComponent("jumpCooldown", new IntVal());
		Components.attackCooldown = Ents.newComponent("attackCooldown", new IntVal());

		MapLoader.addMapObject("PlayerSpawn", new PlayerSpawn());
		MapLoader.addMapObject("GoblinSpawn", new GoblinSpawn());
		MapLoader.addMapObject("Tile", new Tile());
		MapLoader.addMapObject("GravityTile", new GravityTile());

		Register.components.add(Components.velocity);
		Register.components.add(Components.rotation);
		Register.components.add(Components.collider);
		Register.components.add(Components.inventory);
		Register.components.add(Components.jumpCooldown);
		Register.components.add(Components.attackCooldown);

		Register.controllers.add(Controllers.goblinAi);
		Register.controllers.add(Controllers.velocity);
		Register.controllers.add(Controllers.gravity);
		Register.controllers.add(Controllers.player);

		GameServer.onPlayerConnect = new Runnable()
		{
			public void run()
			{
				GameServer.entities.add(PlayerSpawn.create((int) GameServer.playerSpawn.x, (int) GameServer.playerSpawn.y));
			}
		};
	}
}