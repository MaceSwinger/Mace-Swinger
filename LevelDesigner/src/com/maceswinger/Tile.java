package com.maceswinger;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import com.maceswinger.util.SpriteSheet;

public  class Tile {


	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new Tile(0, "", ' ');
	public static final Tile GRASS = new Tile(1,"grass",'g');
	public static final Tile DIRT = new Tile(2,"dirt",'d');
	public static final Tile STONE = new Tile(3, "stone", 's');
	
	public static final Tile PLAYER = new PlayerSpawn(32,"",'p');
	

	protected byte id;
	protected int tileId;
	protected int x, y;
	float width = 32, height =32;
	int texture;
	float textureWidth = 1.0f/16.0f;
	float textureX,textureY;
	char c;
	String name;
	
	public Tile(int id,String name, char saveAS) {
		this.id = (byte)id;
		if(tiles[id] != null) throw new RuntimeException("Duplicant tile id at " + id);
		tiles[id] = this;
		this.c=saveAS;
		this.name=name;
	}
	
	public byte getId()
	{
		return id;
	}
	public char getC()
	{
		return c;
	}
	public String getName()
	{
		return name;
	}
	
	private  void getTexture(int i){
		
		 textureX= i % 16 * textureWidth;//1 over how many per row
		 textureY= i /16 * textureWidth;
		 
		
	}
	public void render(Level level) {
		
		getTexture(this.getId());
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, SpriteSheet.tileSheet);
		glPushMatrix();
		glBegin(GL_QUADS);
		glTexCoord2f(textureX,textureY+textureWidth);
		glVertex2f(x, y);
		glTexCoord2f(textureX+textureWidth,textureY+textureWidth);
	    glVertex2f(x+width, y);
	    glTexCoord2f(textureX+textureWidth,textureY);
	    glVertex2f(x+width, y+height);
	    glTexCoord2f(textureX,textureY);
	    glVertex2f(x, y+height);
        glEnd();
        glPopMatrix();
        glDisable(GL_TEXTURE_2D);
      
	}
	
	public Tile setX(int x2) {
		// TODO Auto-generated method stub
		this.x = x2;
		return this;

	}

	public Tile setY(int x2) {
		// TODO Auto-generated method stub
		this.y = x2;
		return this;

	}

	public int getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	public int getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	
}
