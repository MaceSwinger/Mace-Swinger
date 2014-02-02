package com.maceswinger.server.GUI.commands;

import java.awt.Color;

import com.maceswinger.server.GUI.MainFrame;
import com.moomoohk.MooCommands.Command;

public class StopCommand extends Command
{

	@Override
	public String getCommand()
	{
		return "/stop";
	}

	@Override
	public String getHelpMessage()
	{
		return "Stops the server";
	}

	@Override
	public String getUsage()
	{
		return "/stop";
	}

	@Override
	public int getMaxParams()
	{
		return 0;
	}

	@Override
	public int getMinParams()
	{
		return 0;
	}

	protected boolean check(String[] params)
	{
		if (!MainFrame.frame.server.isRunning())
		{
			this.outputColor = Color.red;
			this.outputMessage = "Server isn't running!";
			return false;
		}
		return super.check(params);
	}

	@Override
	protected void execute(String[] params)
	{
		MainFrame.frame.server.stop();
	}
}
