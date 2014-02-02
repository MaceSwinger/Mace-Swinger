package com.maceswinger.net;

import com.maceswinger.client.ClientProgram;
import com.maceswinger.server.ServerProgram;

public abstract class Message {
	public abstract void runClient(ClientProgram c);
	public abstract void runServer(ServerProgram s);
}
