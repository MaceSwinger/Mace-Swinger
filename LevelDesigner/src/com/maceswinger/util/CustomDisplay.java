package com.maceswinger.util;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.maceswinger.Main;

public class CustomDisplay implements WindowListener {

	private static float yScale;
	private static float xScale;

	public void create(boolean isFullscreen) {
		if (!isFullscreen) {
			Main.frame.addWindowListener(this);
			Main.canvas.setBounds(0, 0, (int) Main.width,
					(int) Main.height);
			Main.canvas.setIgnoreRepaint(true);
			Main.canvas.setFocusable(true);

			Main.frame.add(Main.canvas);
			Main.frame.pack();
			Main.frame.setLocationRelativeTo(null);
			Main.frame.setFocusable(true);
			Main.frame.setVisible(true);
			Main.canvas.requestFocus();
			Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Main.frame.setTitle("Mace Swinger Level Designer");
//			BufferedImage img = null;
//			try {
//				img = ImageIO.read(this.getClass().getResourceAsStream(
//						"/shield.png"));
//				Main.frame.setIconImage(img);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

			System.out.println(Main.canvas.isDisplayable());

			try {
				Display.setParent(Main.canvas);
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
				setyScale(Main.height / Display.getHeight());
				setxScale(Main.width / Display.getWidth());
				// Display.setParent(game.window);
			} catch (LWJGLException e) {
				System.err.println("Display creation failed :(");
			}
		}
		setyScale(Main.height / Display.getHeight());
		setxScale(Main.width / Display.getWidth());

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
