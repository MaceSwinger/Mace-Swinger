package com.maceswinger.gui;

import org.lwjgl.util.vector.Vector4f;

import com.maceswinger.client.GameClient;
import com.maceswinger.utils.Font;


public class GuiHUD extends Gui{
	
	
	
	public GuiHUD(GameClient game, int width, int height) {
		super(game, width, height);
		
	}

	

	@Override
	public void render() {
		
		Font.drawString("woah a heads up display!", 20, 20,  2f, new Vector4f(0,1,0,1));
		Font.drawString("woah a font made of squares!", 20, 20,  2f, new Vector4f(0,1,0,1));
		
		
		
	}
	@Override
	public void tick(int ticks) {
		
	}

	@Override
	public void guiActionPerformed(int elementId, int action) {
		
	}
}
