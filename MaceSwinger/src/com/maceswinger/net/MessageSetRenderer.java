package com.maceswinger.net;

import com.maceswinger.client.GameClient;
import com.maceswinger.client.render.AnimationRenderer;
import com.maceswinger.server.GameServer;

public class MessageSetRenderer extends Message
{
	int eid;
	String spritesheet;

	public MessageSetRenderer()
	{
	}

	public MessageSetRenderer(int eid, String spritesheet)
	{
		this.eid = eid;
		this.spritesheet = spritesheet;
	}

	@Override
	public void runClient(GameClient c)
	{
		c.entities.at(eid).setRenderer(new AnimationRenderer(spritesheet));
	}

	@Override
	public void runServer(GameServer s)
	{
	}
}
