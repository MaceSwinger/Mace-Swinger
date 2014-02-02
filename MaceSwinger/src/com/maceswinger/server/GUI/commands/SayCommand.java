package com.maceswinger.server.GUI.commands;

import java.awt.Color;

import com.maceswinger.server.GUI.MainFrame;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooCommands.CommandsManager;

public class SayCommand extends Command
{

	@Override
	public String getCommand()
	{
		return "/say";
	}

	@Override
	public String getHelpMessage()
	{
		return "Sends a message to all players on the server";
	}

	@Override
	public String getUsage()
	{
		return "/say <message>";
	}

	@Override
	public int getMaxParams()
	{
		return -1;
	}

	@Override
	public int getMinParams()
	{
		return 1;
	}

	public void missingParameters(String[] params)
	{
		this.outputMessage = "Must include a message to send!";
		this.outputColor = Color.red;
	}

	@Override
	protected void execute(String[] params)
	{
		//TODO: Add chat functionality
		String message = "[Server]: " + CommandsManager.stringParams(params, 0);
		MainFrame.frame.addLine(message);
	}
}
