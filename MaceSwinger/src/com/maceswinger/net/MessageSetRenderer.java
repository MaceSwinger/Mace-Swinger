package com.maceswinger.net;

import com.maceswinger.client.ClientProgram;
import com.maceswinger.client.render.AnimationRenderer;
import com.maceswinger.server.ServerProgram;

public class MessageSetRenderer extends Message {
	int eid;
	String spritesheet;
	public MessageSetRenderer() { }
	public MessageSetRenderer(int eid, String spritesheet) {
		this.eid = eid;
		this.spritesheet = spritesheet;
	}
	@Override
	public void runClient(ClientProgram c) {
		c.entities.at(eid).setRenderer(new AnimationRenderer(spritesheet));
	}

	@Override
	public void runServer(ServerProgram s) {
	}
}
