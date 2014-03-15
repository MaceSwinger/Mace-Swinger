package com.maceswinger.gui.components;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import com.maceswinger.gui.Gui;
import com.maceswinger.utils.Easing;
import com.maceswinger.utils.Font;

public class GuiButtonArray extends GuiComponent {

	private Random rand = new Random();
	private List<Button> options = new ArrayList<Button>();

	public GuiButtonArray(int id, Gui gui) {
		super(id, gui);
	}

	public void addButton(Button button) {
		options.add(button);

	}

	public int getId() {
		return id;
	}

	public class Button {
		private float ticks;
		public int id;
		public String text;
		public boolean isAvailable = true;
		private float x, y;
		private float yy;

		private float easDur;
		private float width = 300, height = 50;

		public Button(int id, String text, float xx, float yy, float width, float height) {
			this.id = id;
			this.text = text;
			this.x = xx;
			this.y = 800;
			this.yy = yy;
			this.width = width;
			this.height = height;
			this.easDur = 200.0f + (rand.nextFloat() * 100);
		}

		public boolean isMouseinBounds() {

			if (Mouse.getX() < this.x + width && Mouse.getX() > this.x
					&& Mouse.getY() < this.y + height && Mouse.getY() > this.y) {
				return true;
			}
			return false;

		}

		public void tick() {
			ticks++;

			if (Mouse.isButtonDown(0) && isMouseinBounds())
				parent.guiActionPerformed(id, 0);
			y = Easing.elasticOut(ticks, 0, yy, easDur + 100);

		}

	}

	public void render() {
		for (Button o : options) {

			glPushMatrix();
			if (o.isMouseinBounds())
				GL11.glColor4f(0, 1, 0, 1);
			if (!o.isMouseinBounds())
				GL11.glColor4f(1, 0, 0, 1);
			glBegin(GL_QUADS);
			// glTexCoord2f(textureX,textureY);
			glVertex2f(o.x, o.y);
			// glTexCoord2f(textureX+textureWidth,textureY);
			glVertex2f(o.x + o.width, o.y);
			// glTexCoord2f(textureX+textureWidth,textureY+textureWidth);
			glVertex2f(o.x + o.width, o.y + o.height);
			// glTexCoord2f(textureX,textureY+textureWidth);
			glVertex2f(o.x, o.y + o.height);
			GL11.glColor4f(1, 1, 1, 1);
			glEnd();
			Font.drawString(o.text, o.x + 10, o.y + 10, 0.2f, new Vector4f(0, 0, 1, 1));
			glPopMatrix();
		}

	}

	@Override
	public void tick(int ticks) {
		for (Button b : options) {
			b.tick();
		}

	}

}

