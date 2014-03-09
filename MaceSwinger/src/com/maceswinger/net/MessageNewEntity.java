package com.maceswinger.net;

import org.magnos.entity.Entity;

import com.maceswinger.client.GameClient;
import com.maceswinger.server.GameServer;

public class MessageNewEntity extends Message
{
	public MessageNewEntity()
	{
	}

	@Override
	public void runClient(GameClient c)
	{
		Entity e = new Entity();
		c.entities.add(e);
	}

	@Override
	public void runServer(GameServer s)
	{
	}
}
