package com.maceswinger.utils;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.maceswinger.client.GameClient;

public class CustomDisplay implements WindowListener {

	private static float yScale;
	private static float xScale;

	public void create(boolean isFullscreen) {
		if (!isFullscreen) {
			GameClient.frame.addWindowListener(this);
			GameClient.canvas.setBounds(0, 0, (int) GameClient.width,
					(int) GameClient.height);
			GameClient.canvas.setIgnoreRepaint(true);
			GameClient.canvas.setFocusable(true);

			GameClient.frame.add(GameClient.canvas);
			GameClient.frame.pack();
			GameClient.frame.setLocationRelativeTo(null);
			GameClient.frame.setFocusable(true);
			GameClient.frame.setVisible(true);
			GameClient.canvas.requestFocus();
			GameClient.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			GameClient.frame.setTitle("Mace Swinger");
			BufferedImage img = null;
			try {
				img = ImageIO.read(this.getClass().getResourceAsStream(
						"/shield.png"));
				GameClient.frame.setIconImage(img);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(GameClient.canvas.isDisplayable());

			try {
				Display.setParent(GameClient.canvas);
				Display.create();
			} catch (LWJGLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			try {
				Display.setFullscreen(true);

				Display.setTitle("Mace Swinger");
				Display.create();
				Display.setResizable(true);
				System.out.println(Display.getDesktopDisplayMode().toString());
				setyScale(GameClient.height / Display.getHeight());
				setxScale(GameClient.width / Display.getWidth());
				// Display.setParent(game.window);
			} catch (LWJGLException e) {
				System.err.println("Display creation failed :(");
			}
		}
		setyScale(GameClient.height / Display.getHeight());
		setxScale(GameClient.width / Display.getWidth());

	}

	public static float getxScale() {
		return xScale;
	}

	public static void setxScale(float xScale) {
		CustomDisplay.xScale = xScale;
	}

	public static float getyScale() {
		return yScale;
	}

	public static void setyScale(float yScale) {
		CustomDisplay.yScale = yScale;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}
