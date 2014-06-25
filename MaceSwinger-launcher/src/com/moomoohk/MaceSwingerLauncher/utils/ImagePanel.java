package com.moomoohk.MaceSwingerLauncher.utils;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.moomoohk.Mootilities.OSUtils.OSUtils;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

/**
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @since Dec 20, 2013
 */
public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private int inset = 3;
	private String link;
	private static final TweenManager tweenManager = SLAnimator.createTweenManager();

	public ImagePanel(URL url, boolean clickable, final String link)
	{
		try
		{
			image = ImageIO.read(url);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		this.link = link;
		if (clickable)
		{
			addMouseListener(new MouseAdapter()
			{
				private boolean hover = false;

				@Override
				public void mouseEntered(MouseEvent e)
				{
					hover = true;
					if (isEnabled())
						popOut(0, 0.4f);
				}

				@Override
				public void mouseExited(MouseEvent e)
				{
					hover = false;
					if (isEnabled())
						popIn(3, 0.4f);
				}

				public void mousePressed(MouseEvent e)
				{
					if (isEnabled() && e.getButton() == 1)
						popIn(4, 0.01f);
				}

				public void mouseReleased(MouseEvent e)
				{
					if (isEnabled() && e.getButton() == 1)
					{
						popOut(1, 0.01f);
						if (hover)
							OSUtils.browse(link);
					}
				}
			});
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	public void popOut(int target, float duration)
	{
		tweenManager.killTarget(inset);
		Tween.to(this, ImagePanelAccessor.HOVER_POP, duration).target(target).start(tweenManager);
	}

	public void popIn(int target, float duration)
	{
		tweenManager.killTarget(inset);
		Tween.to(this, ImagePanelAccessor.HOVER_POP, duration).target(target).start(tweenManager);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(image, inset, inset, getWidth() - inset - inset, getHeight() - inset - inset, null);
		g2.dispose();
	}

	public void setEnabled(boolean f)
	{
		super.setEnabled(f);
		if (f)
			setToolTipText(this.link);
		else
			setToolTipText(null);
	}

	public static class ImagePanelAccessor extends SLAnimator.ComponentAccessor
	{
		public static final int HOVER_POP = 100;

		@Override
		public int getValues(Component target, int tweenType, float[] returnValues)
		{
			int ret = super.getValues(target, tweenType, returnValues);
			if (ret >= 0)
				return ret;
			switch (tweenType)
			{
				case HOVER_POP:
					returnValues[0] = ((ImagePanel) target).inset;
					return 1;
				default:
					return -1;
			}
		}

		@Override
		public void setValues(Component target, int tweenType, float[] newValues)
		{
			super.setValues(target, tweenType, newValues);
			switch (tweenType)
			{
				case HOVER_POP:
					((ImagePanel) target).inset = Math.round(newValues[0]);
					target.repaint();
					target.getParent().repaint();
					break;
			}
		}
	}
}