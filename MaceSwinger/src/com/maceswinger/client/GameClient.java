package com.maceswinger.client;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.magnos.entity.Entity;
import org.magnos.entity.EntityList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.maceswinger.Components;
import com.maceswinger.Vector2;
import com.maceswinger.client.render.SpriteRenderer;
import com.maceswinger.client.render.lighting.Block;
import com.maceswinger.client.render.lighting.Intersect;
import com.maceswinger.client.render.lighting.Light;
import com.maceswinger.client.render.lighting.Point;
import com.maceswinger.client.render.lighting.Ray;
import com.maceswinger.client.render.lighting.Wall;
import com.maceswinger.gui.Gui;
import com.maceswinger.gui.GuiMainMenu;
import com.maceswinger.mods.ModuleLoader;
import com.maceswinger.net.KryoReg;
import com.maceswinger.net.Message;
import com.maceswinger.net.ServerShell;
import com.maceswinger.server.GameServer;
import com.maceswinger.utils.CustomDisplay;
import com.maceswinger.utils.CustomMouse;
import com.maceswinger.utils.ShaderLoader;
import com.maceswinger.utils.Sound;
import com.maceswinger.utils.Textures;
import com.moomoohk.Mootilities.OSUtils.OSUtils;

/**
 * 
 * @since Feb 4, 2014
 */
public class GameClient {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EntityList entities = new EntityList();
	private int fps;
	private static boolean fullscreen;
	private static boolean VSync;
	public static final float width = 1920 / 2, height = 1080 / 2;
	private Gui HUD;
	private Gui gui;
	public static JFrame frame;
	public static Canvas canvas;
	public ArrayList<Light> lights = new ArrayList<Light>();
	public ArrayList<Block> blocks = new ArrayList<Block>();
	private Comparator<Intersect> sortIntersect = new Comparator<Intersect>() {
		public int compare(Intersect n0, Intersect n1) {
			if (n1.angle < n0.angle)
				return 1;
			if (n1.angle > n0.angle)
				return -1;
			return 0;
		}
	};
	private int lightProgram;
	final GameServer internalServer = new GameServer(new ServerShell() {

		public void clientDisconnected(com.maceswinger.server.Client c) {
			System.out.println(c.getUsername() + " disconnected");
		}

		public void clientConnected(com.maceswinger.server.Client c) {
			System.out.println(c.getUsername() + " connected");
		}

		public void serverStarted(int port) {
			System.out.println("Server started " + port);
		}

		public void serverStopped() {
			System.out.println("Server stopped");
		}

		public void message(com.maceswinger.server.Client c, String message) {
			System.out.println(c.getUsername() + ": " + message);
		}

		public void portInUse(int port) {
			System.out.println("Unable to lock port: " + port
					+ ", is another server running?");
		}
	});
	private Client client;
	public int ticks;
	public boolean isGamePaused = false;

	public void run() throws LWJGLException {

		// fullscreen=true;
		canvas = new Canvas();
		frame = new JFrame();
		new CustomDisplay().create(fullscreen);
		AL.create();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		glEnable(GL11.GL_STENCIL_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		init();

		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int framesPS = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while (!Display.isCloseRequested()) {

			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			boolean shouldRender = true;

			while (delta >= 1) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)
						&& Keyboard.isKeyDown(Keyboard.KEY_F4))
					break;
				tick();
				delta -= 1;
				shouldRender = true;

			}
			render();
			try {
				Thread.sleep(2);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				framesPS++;
				update();

			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				fps = framesPS;
				framesPS = 0;

			}

		}

		internalServer.stop();
		client.stop();
		exit(0);
	}

	private void tick() {
		//System.out.println(fps);
		if (!Sound.isPlaying[1] && Keyboard.isKeyDown(Keyboard.KEY_P))
			Sound.play(1, 1);
		if (Sound.isPlaying[1] && Keyboard.isKeyDown(Keyboard.KEY_O))
			Sound.pause(1, 1);

		if (!isGamePaused) {
			ticks++;
			if (gui != null) {
				gui.tick(ticks);
				if (gui != null && !gui.pausesGame()) {
					tickLevel();
				}
			} else {
				tickLevel();
			}
		}
	}

	private void tickLevel() {
		entities.update(this);

	}

	private void render() {

		glClear(GL_COLOR_BUFFER_BIT);
		glBindTexture(GL_TEXTURE_2D, 0);
		glClearColor(0, 0, 0, 1);
		float x = SpriteRenderer.mainCamera.x;
		float y = SpriteRenderer.mainCamera.y;
		glPushMatrix();
		GL11.glTranslatef(-x, -y, 0);
		blocks.clear();
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.at(i);
			if (e.has(Components.block) && e.has(Components.position)) {
				Vector2 pos = e.get(Components.position);
//				if(pos.x>-SpriteRenderer.mainCamera.x+200)continue;// TODO add something so only close entities are used
//				if(pos.y>-SpriteRenderer.mainCamera.y+200)continue;
//				if(pos.x<-SpriteRenderer.mainCamera.x-200)continue;
//				if(pos.y<-SpriteRenderer.mainCamera.y-200)continue;
				Block block = e.get(Components.block);
				block.x = (int) e.get(Components.position).x;
				block.y = (int) e.get(Components.position).y;
				blocks.add(block);
			}
		}
		int n = 0;
		for (Light light : lights) {
			if (n == 0)
				light.location.set(new Vector2f(CustomMouse.getX()+x, CustomMouse
						.getY()+y));
			n++;
			light.clear();
			for (Block block : blocks) {

				Vector2f[] vertices = block.getVertices();
				light.walls.add(new Wall(new Point(vertices[0], 0, 0),
						new Point(vertices[1], 0, 0)));
				light.walls.add(new Wall(new Point(vertices[1], 0, 0),
						new Point(vertices[2], 0, 0)));
				light.walls.add(new Wall(new Point(vertices[2], 0, 0),
						new Point(vertices[3], 0, 0)));
				light.walls.add(new Wall(new Point(vertices[3], 0, 0),
						new Point(vertices[0], 0, 0)));
			}
			light.walls.add(new Wall(new Point(new Vector2f(width-x,height-y), 0, 0),
					new Point(new Vector2f(width-x, height+y), 0, 0)));
			light.walls.add(new Wall(new Point(new Vector2f(0, height), 0, 0),
					new Point(new Vector2f(width+x, height+y), 0, 0)));
			light.walls.add(new Wall(new Point(new Vector2f(width+x, height+y), 0,
					0), new Point(new Vector2f(width+x, height-y), 0, 0)));
			light.walls.add(new Wall(new Point(new Vector2f(width+x, height-y), 0, 0),
					new Point(new Vector2f(width-x, height-y), 0, 0)));

			for (Wall wall : light.walls) {
				light.points.add(new Point(wall.start.pos, 0, 0));
			}
			for (Point point : light.points) {
				float angle = (float) Math.atan2(
						point.pos.y - light.location.y, point.pos.x
								- light.location.x);
				point.angle = angle;
				light.angles.add(angle - 0.0005f);
				light.angles.add(angle);
				light.angles.add(angle + 0.0005f);

			}

			for (float angle : light.angles) {
				float dx = (float) Math.cos(angle);
				float dy = (float) Math.sin(angle);
				Ray r = new Ray(light.location, new Vector2f(light.location.x
						+ dx, light.location.y + dy));

				Intersect closestIntersect = null;
				for (Wall wall : light.walls) {
					Intersect intersect = getIntersection(r, wall);
					if (intersect == null)
						continue;
					if (closestIntersect == null
							|| intersect.distance < closestIntersect.distance) {
						closestIntersect = intersect;
					}
				}
				if (closestIntersect == null)
					continue;
				closestIntersect.angle = angle;
				light.intersects.add(closestIntersect);
			}
			Collections.sort(light.intersects, sortIntersect);

		}
		for (Light light : lights) {
			glUseProgram(lightProgram);
			glUniform2f(glGetUniformLocation(lightProgram, "lightLocation"),
					(light.location.getX() - x) / CustomDisplay.getxScale(),
					(light.location.getY() - y) / CustomDisplay.getyScale());
			glUniform3f(glGetUniformLocation(lightProgram, "lightColor"),
					light.red, light.green, light.blue);
			glUniform1f(glGetUniformLocation(lightProgram, "lightRadius"),
					light.radius);
			glBindTexture(GL_TEXTURE_2D,Textures.textureID[0]);
			glUniform1i(glGetUniformLocation(lightProgram, "lookup"), 0);
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE, GL_ONE);
			glColor3f(1, 0, 1);
			glBegin(GL11.GL_TRIANGLE_STRIP);
			for (Intersect i : light.intersects) {

				glVertex2f(i.pos.x, i.pos.y);
				glVertex2f(light.location.x, light.location.y);

			}
			Intersect i = light.intersects.get(0);
			glVertex2f(i.pos.x, i.pos.y);
			glEnd();

		}
		glUseProgram(0);

		glColor3f(1, 1, 1);
		glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		entities.draw(null);
		glPopMatrix();
		if (HUD != null)
			HUD.render();
		if (gui != null)
			gui.render();
	}

	private Intersect getIntersection(Ray ray, Wall segment) {

		// RAY in parametric: Point + Delta*T1
		float r_px = ray.start.x;
		float r_py = ray.start.y;
		float r_dx = ray.end.x - ray.start.x;
		float r_dy = ray.end.y - ray.start.y;

		// SEGMENT in parametric: Point + Delta*T2
		float s_px = segment.start.pos.x;
		float s_py = segment.start.pos.y;
		float s_dx = segment.end.pos.x - segment.start.pos.x;
		float s_dy = segment.end.pos.y - segment.start.pos.y;

		// Are they parallel? If so, no intersect
		float r_mag = (float) Math.sqrt(r_dx * r_dx + r_dy * r_dy);
		float s_mag = (float) Math.sqrt(s_dx * s_dx + s_dy * s_dy);
		if (r_dx / r_mag == s_dx / s_mag && r_dy / r_mag == s_dy / s_mag) {
			// Unit vectors are the same.
			return null;
		}

		// SOLVE FOR T1 & T2
		// r_px+r_dx*T1 = s_px+s_dx*T2 && r_py+r_dy*T1 = s_py+s_dy*T2
		// ==> T1 = (s_px+s_dx*T2-r_px)/r_dx = (s_py+s_dy*T2-r_py)/r_dy
		// ==> s_px*r_dy + s_dx*T2*r_dy - r_px*r_dy = s_py*r_dx + s_dy*T2*r_dx -
		// r_py*r_dx
		// ==> T2 = (r_dx*(s_py-r_py) + r_dy*(r_px-s_px))/(s_dx*r_dy -
		// s_dy*r_dx)
		float T2 = (r_dx * (s_py - r_py) + r_dy * (r_px - s_px))
				/ (s_dx * r_dy - s_dy * r_dx);
		float T1 = (s_px + s_dx * T2 - r_px) / r_dx;

		// Must be within parametic whatevers for RAY/SEGMENT
		if (T1 < 0)
			return null;
		if (T2 < 0 || T2 > 1)
			return null;

		// Return the POINT OF INTERSECTION
		return new Intersect(new Vector2f(r_px + r_dx * T1, r_py + r_dy * T1),
				T1, 0);

	}

	private void update() {
		if (!fullscreen)
			resize(canvas.getWidth(), canvas.getHeight());
		if (VSync)
			Display.sync(60);
		Display.update();
	}

	private void resize(int i, int j) {
		GL11.glViewport(0, 0, i, j);
		CustomDisplay.setyScale(GameClient.height / Display.getHeight());
		CustomDisplay.setxScale(GameClient.width / Display.getWidth());
	}

	private void init() {
		SpriteRenderer.mainCamera = new Vector2();
		System.out.println("Loading!");
		long startTime = System.currentTimeMillis();
		Sound.loadSounds();
		Textures.loadAll();
		ModuleLoader.initMods();
		ModuleLoader.debugPasteCoreMod();
		setUpShaders();
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		System.out.println("Loaded in: " + time + " ms");

		gui = new GuiMainMenu(this, (int) width, (int) height);

	}

	private void setUpShaders() {
		lightProgram = ShaderLoader.loadShaderPair("rsc/shader.vs",
				"rsc/shader.frag");
	}

	public void closeGui() {
		this.gui = null;

	}

	/**
	 * Must be the exit route of the game
	 * 
	 * @param code
	 *            - Exit code
	 */
	public static void exit(int code) {
		switch (code) {
		case 0:
			System.out.println("System exiting normally");
			long startTime = System.currentTimeMillis();
			Textures.deleteAll();
			Sound.deleteSounds();
			AL.destroy();
			Display.destroy();
			long endTime = System.currentTimeMillis();
			long time = endTime - startTime;
			System.out.println("Exited in: " + time + " ms");

			System.exit(0);
			break;
		case 1:
			System.err.println("Exciting due to error!");
			long startTime1 = System.currentTimeMillis();
			Textures.deleteAll();
			Sound.deleteSounds();
			AL.destroy();
			Display.destroy();
			long endTime1 = System.currentTimeMillis();
			long time1 = endTime1 - startTime1;
			System.out.println("Exited in: " + time1 + " ms");

			System.exit(0);
			break;
		}
	}

	public void startGame() {

		for (int i = 1; i <= 1; i++) {
			Vector2f location = new Vector2f((float) Math.random() * width,
					(float) Math.random() * height);
			lights.add(new Light(location, 20, 1,
					1, 1));
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				internalServer.run(2650);
			}
		}, "Internal server thread").start();

		client = new Client();
		KryoReg.reg(client.getKryo());
		client.start();
		client.addListener(new Listener.LagListener(50, 100, new Listener() {
			@Override
			public void received(Connection connection, Object o) {
				if (o.getClass() != FrameworkMessage.keepAlive.getClass()
						&& o instanceof Message)
					((Message) o).runClient(GameClient.this);
			}
		}));
		try {
			client.connect(5000, "localhost", 2650); // Where you'd put an ip
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String connect(String php, String[] keys, String[] values)
			throws Exception {
		String charset = "UTF-8";
		String query = "?";
		if (keys != null && values != null) {
			if (keys.length != values.length)
				throw new IllegalArgumentException("Keys length ("
						+ keys.length + ") != values length (" + values.length
						+ ")");
			for (int i = 0; i < keys.length; i++)
				query += String.format(keys[i] + "=%s",
						URLEncoder.encode(values[i], charset));
		}

		HttpsURLConnection connection = (HttpsURLConnection) new URL(
				"https://maceswinger.com/utils/" + php + ".php" + query)
				.openConnection();
		connection.setDoOutput(true);
		connection.setConnectTimeout(15 * 1000);
		connection.setReadTimeout(15 * 1000);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=" + charset);
		connection.setRequestProperty("User-Agent",
				"Mace Swinger Launcher/1.0 ("
						+ OSUtils.getCurrentOS().toString() + ")");
		connection.setRequestMethod("POST");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String inputLine;
		final StringBuilder inputLines = new StringBuilder("");
		while ((inputLine = in.readLine()) != null)
			inputLines.append(inputLine.trim() + "\n");
		in.close();
		connection.disconnect();
		return inputLines.toString().trim();
	}

	public static void main(String[] args) {
		// try
		// {
		// HashMap<String, String> flags = CommandsManager.parseFlags(args);
		// if ((flags.containsKey("online") &&
		// flags.get("online").equals("true") && !flags.containsKey("sid")) ||
		// (flags.containsKey("sid") && !flags.containsKey("online")))
		// {
		// System.out.println("Please use the Mace Swinger Launcher to launch the game.");
		// JOptionPane.showMessageDialog(null,
		// "Please use the Mace Swinger Launcher to launch the game.", "",
		// JOptionPane.PLAIN_MESSAGE);
		// return;
		// }
		// if (flags.get("online").equals("true"))
		// {
		// String sessinfo = connect("sessinfo", new String[] { "sid" }, new
		// String[] { flags.get("sid") });
		// Scanner s = new Scanner(sessinfo);
		// s.useDelimiter(":");
		// boolean valid = s.hasNextLine() && s.nextBoolean();
		// if (!valid || valid && s.nextInt() == 0)
		// {
		// System.out.println("Invalid session ID. Please login and launch the game through the launcher.");
		// JOptionPane.showMessageDialog(null,
		// "Invalid session ID. Please login and launch the game through the launcher.",
		// "", JOptionPane.PLAIN_MESSAGE);
		// return;
		// }
		// }
		//
		// JOptionPane.showMessageDialog(null, "this is gaem", "",
		// JOptionPane.PLAIN_MESSAGE);
		// //System.exit(0);
		//
		// fullscreen = flags.get("fullscreen").equals("true");
		// //VSync = flags.get("vsync").equals("true");
		// }
		// catch (Exception e)
		// {
		// return;
		// }
		try {
			System.setProperty("org.lwjgl.librarypath",
					OSUtils.getDynamicStorageLocation() + "Mace Swinger"
							+ File.separator + "lwjgl" + File.separator
							+ OSUtils.getCurrentOS().toString().toLowerCase());
			System.setProperty("net.java.games.input.librarypath",
					System.getProperty("org.lwjgl.librarypath"));
		} catch (UnsatisfiedLinkError e) {
			System.out
					.println("LWJGL natives not found! Please use the launcher to download them.");
			JOptionPane
					.showMessageDialog(
							null,
							"LWJGL natives not found! Please use the launcher to download them.",
							"", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		try {
			new GameClient().run();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
}