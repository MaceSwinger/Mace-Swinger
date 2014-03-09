package com.maceswinger.server.GUI.commands;

import java.awt.Color;

import com.maceswinger.server.GUI.MainFrame;
import com.moomoohk.MooCommands.Command;

public class StartCommand extends Command
{

	@Override
	public String getCommand()
	{
		return "/start";
	}

	@Override
	public String getHelpMessage()
	{
		return "Starts the server (if it's not already running). Default port is 2650";
	}

	@Override
	public String getUsage()
	{
		return "/start [port]";
	}

	@Override
	public int getMaxParams()
	{
		return 1;
	}

	@Override
	public int getMinParams()
	{
		return 0;
	}

	@Override
	protected void execute(String[] params)
	{
		final int port = params.length == 0 ? 2650 : Integer.parseInt(params[0]);
		this.outputMessage = ("Starting server on port " + port + "...");
		new Thread(new Runnable()
		{
			public void run()
			{
				MainFrame.frame.server.run(port);
			}
		}, "Server thread").start();
	}

	protected boolean check(String[] params)
	{
		if (MainFrame.frame.server.isRunning())
		{
			this.outputColor = Color.red;
			this.outputMessage = "Server is already running!";
			return false;
		}
		if (params.length > 0 && (Integer.parseInt(params[0]) > 883951 || Integer.parseInt(params[0]) < 0))
		{
			this.outputColor = Color.red;
			this.outputMessage = "Port " + params[0] + " is out of range! (Must be between 0 and 883951)";
			return false;
		}
		return super.check(params);
	}
}
