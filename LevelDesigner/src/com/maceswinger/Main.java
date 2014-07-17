package com.maceswinger;

import static org.lwjgl.opengl.GL11.glEnable;

import java.awt.Canvas;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.maceswinger.util.CustomDisplay;
import com.maceswinger.util.SpriteSheet;
import com.moomoohk.Mootilities.OSUtils.OSUtils;

public class Main {

	private int fps;
	public static final float width = 1920 / 2, height = 1080 / 2;
	public static JFrame frame;
	public static Canvas canvas;

	private Level level;

	private void run() {
		canvas = new Canvas();
		frame = new JFrame();
		new CustomDisplay().create(false);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		glEnable(GL11.GL_STENCIL_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		init();

		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int framesPS = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while (!Display.isCloseRequested()) {

			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			boolean shouldRender = true;

			while (delta >= 1) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)
						&& Keyboard.isKeyDown(Keyboard.KEY_F4))
					break;
				tick();
				delta -= 1;
				shouldRender = true;

			}
			render();
			try {
				Thread.sleep(2);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				framesPS++;
				update();

			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				fps = framesPS;
				framesPS = 0;

			}

		}

		// exit(0);

	}

	private void tick() {
		// TODO Auto-generated method stub

	}

	private void render() {
		level.renderTiles();

	}

	private void update() {
		resize(canvas.getWidth(), canvas.getHeight());
		Display.sync(60);
		Display.update();
	}

	private void resize(int i, int j) {
		GL11.glViewport(0, 0, i, j);
		CustomDisplay.setyScale(Main.height / Display.getHeight());
		CustomDisplay.setxScale(Main.width / Display.getWidth());
	}

	private void init() {
		this.level = new Level(this);
		level.loadLevel("");
		SpriteSheet.openSheet();

	}

	public static void main(String[] args) {

		try {
			System.setProperty("org.lwjgl.librarypath",
					OSUtils.getDynamicStorageLocation() + "Mace Swinger"
							+ File.separator + "lwjgl" + File.separator
							+ OSUtils.getCurrentOS().toString().toLowerCase());
			System.setProperty("net.java.games.input.librarypath",
					System.getProperty("org.lwjgl.librarypath"));
		} catch (UnsatisfiedLinkError e) {
			System.out
					.println("LWJGL natives not found! Please use the launcher to download them.");
			JOptionPane
					.showMessageDialog(
							null,
							"LWJGL natives not found! Please use the launcher to download them.",
							"", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		new Main().run();

	}

}
