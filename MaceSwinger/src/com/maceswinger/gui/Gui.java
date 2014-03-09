package com.maceswinger.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.Random;

import com.maceswinger.client.GameClient;
import com.maceswinger.utils.Textures;

public abstract class Gui {



	public GameClient game;
	protected Random rand;
	
	

	public int width;
	public int height;
	protected boolean pauseGame;
	protected float alpha=1.0f;
	protected int tick;
	
	public Gui(GameClient game, int width, int height) {
		this.game = game;
		this.rand = new Random();
		this.width=width;
		this.height=height;

	}
	
	protected void renderImage(int tex, float width, float height, float x, float y){
		glPushMatrix();
		glBindTexture(GL_TEXTURE_2D, Textures.textureID[tex]);
		glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(1, 0);
        glVertex2f(x+width, y);
        glTexCoord2f(1, 1);
        glVertex2f(x+width, y+height);
        glTexCoord2f(0, 1);
		glVertex2f(x, y+height);
        glEnd();
        glPopMatrix();
        glBindTexture(GL_TEXTURE_2D, 0);
	}
	public void render(){
		if(this.tick<20){
		glPushMatrix();
		glBegin(GL_QUADS);
		glColor4f(0,0,0,this.alpha);
		glVertex2f(0,0);
		glVertex2f(width,0);
		glVertex2f(width,height);
		glVertex2f(0,height);
		glEnd();
		glPopMatrix();
		}
	}

	public void tick(int ticks){
		if(this.tick<20){
			this.alpha-=0.05;
	
		}
	}
	
	public abstract void guiActionPerformed(int elementId, int action);

	public boolean pausesGame() {
		return pauseGame;
	}

	
	
	public void closeGui() {
		
		//game.hideGui();
	}

}
