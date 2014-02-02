package com.maceswinger.net;

import com.maceswinger.server.Client;

public interface ServerShell
{
	public void clientConnected(Client c);

	public void clientDisconnected(Client c);

	public void serverStarted(int port);

	public void serverStopped();

	public void message(Client c, String message);

	public void portInUse(int port);
}
