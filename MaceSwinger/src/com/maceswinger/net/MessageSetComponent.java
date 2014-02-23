package com.maceswinger.net;

import org.magnos.entity.Component;
import org.magnos.entity.Entity;
import org.magnos.entity.Ents;

import com.maceswinger.client.GameClient;
import com.maceswinger.server.GameServer;

public class MessageSetComponent extends Message
{
	int cid;
	Object obj;
	int eid;

	public MessageSetComponent()
	{
	}

	public MessageSetComponent(int eid, int cid, Object object)
	{
		this.eid = eid;
		this.cid = cid;
		this.obj = object;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void runClient(GameClient c)
	{
		Entity e = c.entities.at(eid);

		Component<Object> comp = (Component<Object>) Ents.getComponents().getDefinition(cid);
		if (!e.has(comp))
			e.add(comp);
		e.set(comp, obj);
	}

	@Override
	public void runServer(GameServer s)
	{
	}
}
