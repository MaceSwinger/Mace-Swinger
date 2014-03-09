package com.maceswinger.server.GUI.commands;

import com.moomoohk.MooCommands.Command;

public class KickCommand extends Command
{
	@Override
	public String getCommand()
	{
		return "/kick";
	}

	@Override
	public String getHelpMessage()
	{
		return "Kicks a client from the server";
	}

	@Override
	public String getUsage()
	{
		return "/kick <username>";
	}

	@Override
	public int getMaxParams()
	{
		return 1;
	}

	@Override
	public int getMinParams()
	{
		return 1;
	}

	@Override
	protected void execute(String[] params)
	{
		this.outputMessage = "Kicking " + params[0] + "...";
		//TODO: Kick functionality
	}
}
