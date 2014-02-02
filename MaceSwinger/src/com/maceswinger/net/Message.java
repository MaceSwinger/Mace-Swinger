package com.maceswinger.net;

import com.maceswinger.client.ClientProgram;
import com.maceswinger.server.ServerProgram;

/**
 * Represents messages which get sent between server and client.
 * 
 * @since Feb 2, 2014
 */
public abstract class Message
{
	/**
	 * Code to run on the client when it receives this message.
	 * 
	 * @param c
	 *            Client in question
	 */
	public abstract void runClient(ClientProgram c);

	/**
	 * Code to run on the server when it receives this message.
	 * 
	 * @param s
	 *            Server in question
	 */
	public abstract void runServer(ServerProgram s);
}
