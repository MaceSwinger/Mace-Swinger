package com.maceswinger.server.GUI.commands;

import com.moomoohk.MooCommands.Command;

public class BanCommand extends Command
{

	@Override
	public String getCommand()
	{
		return "/ban";
	}

	@Override
	public String getHelpMessage()
	{
		return "Bans a client from the server";
	}

	@Override
	public String getUsage()
	{
		return "/ban <username>";
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
		this.outputMessage = "Banning " + params[0];
	}
}
