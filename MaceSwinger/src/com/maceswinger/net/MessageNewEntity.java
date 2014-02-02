package com.maceswinger.net;

import org.magnos.entity.Entity;

import com.maceswinger.client.ClientProgram;
import com.maceswinger.server.ServerProgram;

public class MessageNewEntity extends Message {
	public MessageNewEntity() { }
	@Override
	public void runClient(ClientProgram c) {
		Entity e = new Entity();
		c.entities.add(e);
	}
	@Override
	public void runServer(ServerProgram s) {
	}
}
