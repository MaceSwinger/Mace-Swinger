package com.maceswinger.server.GUI.commands;

import com.maceswinger.server.GUI.MainFrame;
import com.moomoohk.MooCommands.Command;

public class ClearCommand extends Command
{

	@Override
	public String getCommand()
	{
		return "/clear";
	}

	@Override
	public String getHelpMessage()
	{
		return "Clears the console window";
	}

	@Override
	public String getUsage()
	{
		return "/clear";
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

	@Override
	protected void execute(String[] params)
	{
		MainFrame.frame.consoleTextPane.setText("");
	}
}
