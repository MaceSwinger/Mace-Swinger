package com.maceswinger.net;

import org.magnos.entity.Controller;
import org.magnos.entity.Entity;
import org.magnos.entity.Ents;

import com.maceswinger.client.GameClient;
import com.maceswinger.server.GameServer;

public class MessageSetController extends Message
{
	/**
	 * Controller id
	 */
	private int cid;
	/**
	 * Entity id
	 */
	private int eid;

	public MessageSetController()
	{
	}

	public MessageSetController(int eid, int cid)
	{
		this.eid = eid;
		this.cid = cid;
	}

	@Override
	public void runClient(GameClient c)
	{
		Entity e = c.entities.at(eid);
		Controller cont = Ents.getControllers().getDefinition(cid);
		if (!e.has(cont))
			e.add(cont);
	}

	@Override
	public void runServer(GameServer s)
	{
	}
}
