package com.maceswinger.client;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.magnos.entity.EntityList;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;
import com.maceswinger.net.KryoReg;
import com.maceswinger.net.Message;
import com.maceswinger.net.ServerShell;
import com.maceswinger.server.GameServer;
import com.moomoohk.MooCommands.CommandsManager;
import com.moomoohk.Mootilities.OSUtils.OSUtils;

/**
 * 
 * @since Feb 4, 2014
 */
public class GameClient
{
	public EntityList entities = new EntityList();

	public void run() throws LWJGLException
	{
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.setTitle("Mace Swinger");
		Display.create();

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

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
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				internalServer.run(2650);
			}
		}, "Internal server thread").start();

		Client client = new Client();
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
			client.connect(5000, "localhost", 2650); //Where you'd put an ip TODO: Fix synchronization bug
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		while (!Display.isCloseRequested())
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) && Keyboard.isKeyDown(Keyboard.KEY_F4))
				break;
			entities.update(this);
			render();
		}
		Display.destroy();
		internalServer.stop();
		client.stop();
		System.exit(0); //Not sure why, but it won't close on exit otherwise
	}

	private void render()
	{
		Display.sync(60);//60 fps

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glColor3f(0.5f, 0.5f, 1.0f);

		entities.draw(null);

		Display.update();
	}

	public static void main(String[] args)
	{
		try
		{
			HashMap<String, String> flags = CommandsManager.parseFlags(args);
			if ((flags.containsKey("online") && flags.get("online").equals("true") && !flags.containsKey("sid")) || (flags.containsKey("sid") && !flags.containsKey("online")))
			{
				System.out.println("Please use the Mace Swinger Launcher to launch the game.");
				JOptionPane.showMessageDialog(null, "Please use the Mace Swinger Launcher to launch the game.", "", JOptionPane.PLAIN_MESSAGE);
				return;
			}
		}
		catch (IllegalStateException e)
		{
			return;
		}
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
