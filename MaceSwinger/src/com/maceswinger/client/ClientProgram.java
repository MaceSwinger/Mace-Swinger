package com.maceswinger.client;

import java.io.IOException;
import java.util.ArrayList;

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
import com.maceswinger.server.ServerProgram;
import com.moomoohk.Mootilities.OSUtils.OSUtils;

public class ClientProgram
{
	public EntityList entities = new EntityList();

	public void run() throws LWJGLException
	{
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.create();
		Display.setTitle("Mace Swinger");

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		final ServerProgram internalServer = new ServerProgram(new ServerShell()
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
					((Message) o).runClient(ClientProgram.this);
			}
		}));
		try
		{
			client.connect(5000, "localhost", 2650);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		while (!Display.isCloseRequested())
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) && Keyboard.isKeyDown(Keyboard.KEY_F4))
				break;
			ArrayList<Integer> keys = new ArrayList<Integer>();
			while (Keyboard.next())
				if (Keyboard.getEventKeyState())
					keys.add(Keyboard.getEventKey());
			client.sendTCP(keys);
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
		//Log.TRACE();
		boolean needsToEatShit = true;
		for (String arg : args)
			if (arg.equals("-eatshitpirates"))
				needsToEatShit = false;
		if (needsToEatShit)
		{
			System.out.println("Please use the Mace Swinger Launcher to launch the game.");
			return;
		}
		System.setProperty("org.lwjgl.librarypath", OSUtils.getDynamicStorageLocation() + "Mace Swinger/lwjgl/" + OSUtils.getCurrentOS().toString().toLowerCase());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));

		//		ModuleLoader.debugPasteCoreMod();
		//		ModuleLoader.initMods();
		try
		{
			new ClientProgram().run();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
}
