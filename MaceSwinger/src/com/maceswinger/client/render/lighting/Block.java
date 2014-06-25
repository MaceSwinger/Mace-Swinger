package com.maceswinger.client.render.lighting;

import org.lwjgl.util.vector.Vector2f;
import org.magnos.entity.ComponentValueFactory;
import static org.lwjgl.opengl.GL11.*;
public class Block implements ComponentValueFactory<Block>{
	public int x, y, width, height;

	public Block(){
		this(0,0);
	}
	public Block(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public Block(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Vector2f[] getVertices() {
		return new Vector2f[] {
				new Vector2f(x, y),
				new Vector2f(x, y + height),
				new Vector2f(x + width, y + height),
				new Vector2f(x + width, y)
		};
	}
	public Vector2f[] getVertices(float x, float y) {
		return new Vector2f[] {
				new Vector2f(x, y),
				new Vector2f(x, y + height),
				new Vector2f(x + width, y + height),
				new Vector2f(x + width, y)
		};
	}
	public void render(){
		glPushMatrix();
        glTranslatef(x, y, 0);
        //glRotated(Math.toDegrees(body.getAngle()), 0, 0, 1);
        glColor3f(1,0,1);
        glRectf(0, 0, width, height);
		glColor3f(1,1,1);
        glPopMatrix();
      
	}
	public Block(Block value) {
		this.width = value.width;
		this.height = value.height;
	}
	

	@Override
	public Block create() {
		return new Block();
	}

	@Override
	public Block clone(Block value) {
		// TODO Auto-generated method stub
		return new Block(value);
	}

	@Override
	public Block copy(Block from, Block to) {
		to.width = from.width;
		to.height = from.height;

		return to;
	}
	
}
