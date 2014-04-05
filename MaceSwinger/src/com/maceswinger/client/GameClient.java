
package com.maceswinger.client;

import java.awt.Canvas;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.magnos.entity.EntityList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.maceswinger.gui.Gui;
import com.maceswinger.gui.GuiMainMenu;
import com.maceswinger.mods.ModuleLoader;
import com.maceswinger.net.KryoReg;
import com.maceswinger.net.Message;
import com.maceswinger.net.ServerShell;
import com.maceswinger.server.GameServer;
import com.maceswinger.utils.CustomDisplay;
import com.maceswinger.utils.Sound;
import com.maceswinger.utils.Textures;
import com.moomoohk.MooCommands.CommandsManager;
import com.moomoohk.Mootilities.OSUtils.OSUtils;

/**
 * 
 * @since Feb 4, 2014
 */
public class GameClient 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EntityList entities = new EntityList();
	private int fps;
	private static boolean fullscreen;
	private static boolean VSync;
	public static final float width = 1920/2, height = 1080/2;
	private Gui HUD;
	private Gui gui;
	public static  JFrame frame;
	public  static Canvas canvas;
	final GameServer internalServer = new GameServer(new ServerShell()
	{

		public void clientDisconnected(com.maceswinger.server.Client c)
		{
			System.out.println(c.getUsername() + " disconnected");
		}

		public void clientConnected(com.maceswinger.server.Client c)
		{
			System.out.println(c.getUsername() + " connected");
		}

		public void serverStarted(int port)
		{
			System.out.println("Server started " + port);
		}

		public void serverStopped()
		{
			System.out.println("Server stopped");
		}

		public void message(com.maceswinger.server.Client c, String message)
		{
			System.out.println(c.getUsername() + ": " + message);
		}

		public void portInUse(int port)
		{
			System.out.println("Unable to lock port: " + port + ", is another server running?");
		}
	});
	private Client client;
	public int ticks;
	public boolean isGamePaused = false;

	public void run() throws LWJGLException
	{
		//fullscreen=true;
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

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		init();

		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int framesPS = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while (!Display.isCloseRequested())
		{
			
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			boolean shouldRender = true;

			while (delta >= 1)
			{
				if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) && Keyboard.isKeyDown(Keyboard.KEY_F4))
					break;
				tick();
				delta -= 1;
				shouldRender = true;

			}
			render();
			try
			{
				Thread.sleep(2);

			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			if (shouldRender)
			{
				framesPS++;
				update();

			}

			if (System.currentTimeMillis() - lastTimer >= 1000)
			{
				lastTimer += 1000;
				fps = framesPS;
				framesPS = 0;

			}

		}

		internalServer.stop();
		client.stop();
		exit(0);
	}

	private void tick()
	{
		System.out.println("fps:"+fps);
		if (!Sound.isPlaying[1] && Keyboard.isKeyDown(Keyboard.KEY_P))
			Sound.play(1, 1);
		if (Sound.isPlaying[1] && Keyboard.isKeyDown(Keyboard.KEY_O))
			Sound.pause(1, 1);

		if (!isGamePaused)
		{
			ticks++;
			if (gui != null)
			{
				gui.tick(ticks);
				if (gui != null && !gui.pausesGame())
				{
					tickLevel();
				}
			}
			else
			{
				tickLevel();
			}
		}
	}

	private void tickLevel()
	{
		entities.update(this);

	}

	private void render()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glColor3f(0.5f, 0.5f, 1.0f);
		entities.draw(null);
		if (HUD != null)
			HUD.render();
		if (gui != null)
			gui.render();
	}

	private void update()
	{
		if(!fullscreen)
			resize(canvas.getWidth(),canvas.getHeight());
		if (VSync)
			Display.sync(60);
		Display.update();
	}

	private void resize(int i, int j) {
		GL11.glViewport(0, 0, i, j);
		CustomDisplay.setyScale(GameClient.height / Display.getHeight());
		CustomDisplay.setxScale(GameClient.width / Display.getWidth());
	}

	private void init()
	{
		System.out.println("Loading!");
		long startTime = System.currentTimeMillis();
		Sound.loadSounds();
		Textures.loadAll();
		ModuleLoader.initMods();
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		System.out.println("Loaded in: " + time + " ms");
		
		gui = new GuiMainMenu(this,(int) width, (int)height);
		//startGame();
	}

	public void closeGui()
	{
		this.gui = null;

	}

	public static void exit(int code)
	{
		switch (code)
		{
			case 0:
				System.out.println("System exiting normally");
				long startTime = System.currentTimeMillis();
				Textures.deleteAll();
				Sound.deleteSounds();
				long endTime = System.currentTimeMillis();
				long time = endTime - startTime;
				System.out.println("Exited in: " + time + " ms");
			
				System.exit(0);
		}
	}

	public void startGame()
	{

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				internalServer.run(2650);
			}
		}, "Internal server thread").start();

		client = new Client();
		KryoReg.reg(client.getKryo());
		client.start();
		client.addListener(new Listener.LagListener(50, 100, new Listener()
		{
			@Override
			public void received(Connection connection, Object o)
			{
				if (o.getClass() != FrameworkMessage.keepAlive.getClass() && o instanceof Message)
					((Message) o).runClient(GameClient.this);
			}
		}));
		try
		{
			client.connect(5000, "localhost", 2650); // Where you'd put an ip
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public static String connect(String php, String[] keys, String[] values) throws Exception
	{
		String charset = "UTF-8";
		String query = "?";
		if (keys != null && values != null)
		{
			if (keys.length != values.length)
				throw new IllegalArgumentException("Keys length (" + keys.length + ") != values length (" + values.length + ")");
			for (int i = 0; i < keys.length; i++)
				query += String.format(keys[i] + "=%s", URLEncoder.encode(values[i], charset));
		}

		HttpsURLConnection connection = (HttpsURLConnection) new URL("https://maceswinger.com/utils/" + php + ".php" + query).openConnection();
		connection.setDoOutput(true);
		connection.setConnectTimeout(15 * 1000);
		connection.setReadTimeout(15 * 1000);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
		connection.setRequestProperty("User-Agent", "Mace Swinger Launcher/1.0 (" + OSUtils.getCurrentOS().toString() + ")");
		connection.setRequestMethod("POST");
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		final StringBuilder inputLines = new StringBuilder("");
		while ((inputLine = in.readLine()) != null)
			inputLines.append(inputLine.trim() + "\n");
		in.close();
		connection.disconnect();
		return inputLines.toString().trim();
	}

	public static void main(String[] args)
	{
//		try
//		{
//			HashMap<String, String> flags = CommandsManager.parseFlags(args);
//			if ((flags.containsKey("online") && flags.get("online").equals("true") && !flags.containsKey("sid")) || (flags.containsKey("sid") && !flags.containsKey("online")))
//			{
//				System.out.println("Please use the Mace Swinger Launcher to launch the game.");
//				JOptionPane.showMessageDialog(null, "Please use the Mace Swinger Launcher to launch the game.", "", JOptionPane.PLAIN_MESSAGE);
//				return;
//			}
//			if (flags.get("online").equals("true"))
//			{
//				String sessinfo = connect("sessinfo", new String[] { "sid" }, new String[] { flags.get("sid") });
//				Scanner s = new Scanner(sessinfo);
//				s.useDelimiter(":");
//				boolean valid = s.hasNextLine() && s.nextBoolean();
//				if (!valid || valid && s.nextInt() == 0)
//				{
//					System.out.println("Invalid session ID. Please login and launch the game through the launcher.");
//					JOptionPane.showMessageDialog(null, "Invalid session ID. Please login and launch the game through the launcher.", "", JOptionPane.PLAIN_MESSAGE);
//					return;
//				}
//			}
//
//			JOptionPane.showMessageDialog(null, "this is gaem", "", JOptionPane.PLAIN_MESSAGE);
//			//System.exit(0);
//
//			fullscreen = flags.get("fullscreen").equals("true");
//			//VSync = flags.get("vsync").equals("true");
//		}
//		catch (Exception e)
//		{
//			return;
//		}
		try
		{
			System.setProperty("org.lwjgl.librarypath", OSUtils.getDynamicStorageLocation() + "Mace Swinger" + File.separator + "lwjgl" + File.separator + OSUtils.getCurrentOS().toString().toLowerCase());
			System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		}
		catch (UnsatisfiedLinkError e)
		{
			System.out.println("LWJGL natives not found! Please use the launcher to download them.");
			JOptionPane.showMessageDialog(null, "LWJGL natives not found! Please use the launcher to download them.", "", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		try
		{
			new GameClient().run();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
}
