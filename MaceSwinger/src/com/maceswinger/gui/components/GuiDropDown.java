package com.maceswinger.gui.components;


import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import com.maceswinger.gui.Gui;
import com.maceswinger.utils.Easing;
import com.maceswinger.utils.Font;
import com.maceswinger.utils.Textures;

public class GuiDropDown extends GuiComponent{



	private List<TexturedButton> options = new ArrayList<TexturedButton>();
	private TexturedButton head;
	private boolean isDroppedDown = false;

	

	public GuiDropDown(int id, Gui gui) {
		super(id, gui);
		
	}
	public void setHead(TexturedButton h){
		this.head=h;
		this.head.isVisable=true;
		this.head.y=h.y;
	}
	public void addButton(TexturedButton button) {
		options.add(button);
		
	}

	public int getId() {
		return id;
	}

	
	public class TexturedButton {
		public int id;
		public int text;
		public boolean isAvailable = true;
		private float x,y;
		private float width,height;
		private boolean isVisable = false;
		public float yTarget;
		private int ticks;
		private String tag;
		public TexturedButton(int id, int text, float x, float y, float width, float height, String tag) {
			this.id = id;
			this.text = text;
			this.x=x;
			this.y=y;
			this.yTarget=y;
			this.width = width;
			this.height=height;
			this.tag=tag;
		}
		public boolean isMouseinBounds(){
			
			if(Mouse.getX()<this.x+width&&Mouse.getX()>this.x&&Mouse.getY()<this.y+height&&Mouse.getY()>this.y){
				System.out.println("in bounds");
				return true;
			}
			return false;
			
			
		}
		public void tick(){
			ticks++;
			y=Easing.elasticOut(ticks,0,yTarget,100);
			
			if(Mouse.isButtonDown(0)&&isMouseinBounds())parent.guiActionPerformed(id, 0);
			if(Mouse.isButtonDown(1)&&isMouseinBounds())parent.guiActionPerformed(id, 1);
		}
	}
		
	
	
	public void dropDown(){
		System.out.println("drop");
		isDroppedDown=true;
		for (TexturedButton o : options) {
			o.isVisable=true;
			o.y=0;
		}
	}
	public void pushUp(){
		isDroppedDown=false;
		for (TexturedButton o : options) {
			o.isVisable=false;
			o.y=o.yTarget;
			o.ticks=0;
		}
	}
	public void render() {
		glPushMatrix();
		if(head.isMouseinBounds())GL11.glColor4f(1,1,1,1);
		if(!head.isMouseinBounds())GL11.glColor4f(1,1,1,0.5f);
		glBindTexture(GL_TEXTURE_2D,Textures.textureID[head.text]);
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2f(head.x, head.y);
		glTexCoord2f(1,0);
	    glVertex2f(head.x+head.width, head.y);
	    glTexCoord2f(1,1);
	    glVertex2f(head.x+head.width, head.y+head.height);
	    glTexCoord2f(0,1);
	    glVertex2f(head.x, head.y+head.height);
	    GL11.glColor4f(1,1,1,1);
        glEnd();
        glPopMatrix();

		if(isDroppedDown){
		for (TexturedButton o : options) {
			glPushMatrix();
			if(o.isMouseinBounds()){
				GL11.glColor4f(1,1,1,1);
				glBindTexture(GL_TEXTURE_2D,0);
				Font.drawString(o.tag, o.x+50, o.y, 2f, new Vector4f(1, 1, 1, 1));
			}
			if(!o.isMouseinBounds())GL11.glColor4f(1,1,1,0.5f);
			glBindTexture(GL_TEXTURE_2D,Textures.textureID[o.text]);
			glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f(o.x, o.y);
			glTexCoord2f(1,0);
		    glVertex2f(o.x+o.width, o.y);
		    glTexCoord2f(1,1);
		    glVertex2f(o.x+o.width, o.y+o.height);
		    glTexCoord2f(0,1);
		    glVertex2f(o.x, o.y+o.height);
		    GL11.glColor4f(1,1,1,1);
	        glEnd();
	        glPopMatrix();
	       
		}
		}
		
	}

	@Override
	public void tick(int ticks) {
		head.tick();
		for(TexturedButton b:options){
			
			if(b.isVisable)b.tick();
			
		}
		
	}

	
}
