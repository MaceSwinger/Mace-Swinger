package com.maceswinger.server.GUI.commands;

import com.moomoohk.MooCommands.Command;

public class UnbanCommand extends Command
{

	@Override
	public String getCommand()
	{
		return "/unban";
	}

	@Override
	public String getHelpMessage()
	{
		return "Unbans a client from the server";
	}

	@Override
	public String getUsage()
	{
		return "/unban <username>";
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
		this.outputMessage = "Unbanning " + params[0];
	}
}
