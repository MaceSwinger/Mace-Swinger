
package com.maceswinger.gui.components;

import com.maceswinger.gui.Gui;




public abstract class GuiComponent {
	
	protected Gui parent;
	protected int id;
	public float x,y;
	public GuiComponent(int elementId, Gui gui) {
		this.parent = gui;
	}
		
	public abstract void render();
	public abstract void tick(int ticks);
	
}
