package com.maceswinger.server.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * @author Vladimir Ikryanov source: http://ui-ideas.blogspot.com/2012/06/mac-os-x-mountain-lion-scrollbars-in.html
 * @author Andrew Cox Added: ScrollBars now hide themselves after a set amount of time Support for containing JTables Contact: http://www.andrewdcox.com/contact.php
 */
public class LightScrollPane extends JComponent
{

	private static final long serialVersionUID = 1L;
	private static final int SCROLL_BAR_ALPHA_ROLLOVER = 150;
	private static final int SCROLL_BAR_ALPHA = 100;
	private static final int THUMB_BORDER_SIZE = 2;
	private static final int THUMB_SIZE = 8;
	private static final Color THUMB_COLOR = Color.BLACK;

	private final JScrollPane scrollPane;
	private final JScrollBar verticalScrollBar;
	private final JScrollBar horizontalScrollBar;

	public Timer timer;
	private boolean childIsTable = false;
	private JComponent comp;

	public LightScrollPane(JComponent component)
	{
		if (component instanceof JTable)
		{
			childIsTable = true;

			//copy the component to a Table object
			JTable table = (JTable) component;

			//create a JPanel to contain the JTable and trick the scroller into
			//thinking it isn't a JTable
			JPanel tablePanel = new JPanel();
			tablePanel.setLayout(new BorderLayout());
			tablePanel.add(table, BorderLayout.CENTER);

			//add an empty label at the end to fix the problem with not showing
			//the last row of the table
			JLabel emptySpace = new JLabel("");
			emptySpace.setMinimumSize(new Dimension(10, 19));
			emptySpace.setPreferredSize(new Dimension(100, 19));
			tablePanel.add(emptySpace, BorderLayout.SOUTH);

			scrollPane = new JScrollPane(tablePanel);
		}
		else
		{
			scrollPane = new JScrollPane(component);
		}

		comp = component;

		verticalScrollBar = scrollPane.getVerticalScrollBar();
		verticalScrollBar.setVisible(false);
		verticalScrollBar.setOpaque(false);
		verticalScrollBar.setUI(new MyScrollBarUI(comp));
		//verticalScrollBar.setBorder(BorderFactory.createEmptyBorder(0,0,0,4));
		verticalScrollBar.addAdjustmentListener(new AdjustmentListener()
		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				verticalScrollBar.setUI(new MyScrollBarUI(comp));
				timer.stop();
				timer.start();
			}

		});

		horizontalScrollBar = scrollPane.getHorizontalScrollBar();
		horizontalScrollBar.setVisible(false);
		horizontalScrollBar.setOpaque(false);
		horizontalScrollBar.setUI(new MyScrollBarUI(comp));
		horizontalScrollBar.addAdjustmentListener(new AdjustmentListener()
		{

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				horizontalScrollBar.setUI(new MyScrollBarUI(comp));
				timer.stop();
				timer.start();
			}

		});

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setLayer(verticalScrollBar, JLayeredPane.PALETTE_LAYER);
		layeredPane.setLayer(horizontalScrollBar, JLayeredPane.PALETTE_LAYER);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setLayout(new ScrollPaneLayout()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutContainer(Container parent)
			{
				viewport.setBounds(0, 0, getWidth(), getHeight());
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						displayScrollBarsIfNecessary(viewport);
					}
				});
			}
		});

		layeredPane.add(horizontalScrollBar);
		layeredPane.add(verticalScrollBar);
		layeredPane.add(scrollPane);

		setLayout(new BorderLayout()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutContainer(Container target)
			{
				super.layoutContainer(target);
				int width = getWidth();
				int height = getHeight();
				scrollPane.setBounds(0, 0, width, height);

				int scrollBarSize = 16;
				int cornerOffset = verticalScrollBar.isVisible() && horizontalScrollBar.isVisible() ? scrollBarSize : 0;
				if (verticalScrollBar.isVisible())
				{
					verticalScrollBar.setBounds(width - scrollBarSize, 0, scrollBarSize, height - cornerOffset);
				}
				if (horizontalScrollBar.isVisible())
				{
					horizontalScrollBar.setBounds(0, height - scrollBarSize, width - cornerOffset, scrollBarSize);
				}
			}
		});
		add(layeredPane, BorderLayout.CENTER);
		layeredPane.setBackground(Color.BLUE);

		setBorder(BorderFactory.createEtchedBorder());

		if (childIsTable)
		{

			//copy the component to a Table object
			JTable table = (JTable) comp;

			//take the header from the JTable and put it above the scrolling content
			JPanel headerPanel = new JPanel();
			headerPanel.setLayout(new BorderLayout());
			headerPanel.add(table.getTableHeader(), BorderLayout.CENTER);

			add(headerPanel, BorderLayout.NORTH);
		}

		//initialize the timer that makes the scrollBars disappear
		timer = new javax.swing.Timer(1000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				verticalScrollBar.setUI(null); //essentially make it invisible without deleting it
				horizontalScrollBar.setUI(null);

				timer.stop();
			}
		});
	}//========================================

	private void displayScrollBarsIfNecessary(JViewport viewPort)
	{
		displayVerticalScrollBarIfNecessary(viewPort);
		displayHorizontalScrollBarIfNecessary(viewPort);

		//start timer to hide scroll bars
		timer.stop();
		timer.start();
	}//==========================================

	private void displayVerticalScrollBarIfNecessary(JViewport viewPort)
	{
		Rectangle viewRect = viewPort.getViewRect();
		Dimension viewSize = viewPort.getViewSize();
		boolean shouldDisplayVerticalScrollBar = viewSize.getHeight() > viewRect.getHeight();
		verticalScrollBar.setVisible(shouldDisplayVerticalScrollBar);
	}//==========================================

	private void displayHorizontalScrollBarIfNecessary(JViewport viewPort)
	{
		Rectangle viewRect = viewPort.getViewRect();
		Dimension viewSize = viewPort.getViewSize();
		boolean shouldDisplayHorizontalScrollBar = viewSize.getWidth() > viewRect.getWidth();
		horizontalScrollBar.setVisible(shouldDisplayHorizontalScrollBar);
	}//==========================================

	private static class MyScrollBarButton extends JButton
	{
		private static final long serialVersionUID = 1L;

		private MyScrollBarButton()
		{
			setOpaque(false);
			setFocusable(false);
			setFocusPainted(false);
			setBorderPainted(false);
			setBorder(BorderFactory.createEmptyBorder());
		}
	}//==========================================

	private static class MyScrollBarUI extends BasicScrollBarUI
	{

		private int dh = 0;

		public MyScrollBarUI(JComponent mainViewport)
		{
			if (mainViewport instanceof JTable)
				dh = -18;
		}//======================================

		@Override
		protected JButton createDecreaseButton(int orientation)
		{
			return new MyScrollBarButton();
		}//======================================

		@Override
		protected JButton createIncreaseButton(int orientation)
		{
			return new MyScrollBarButton();
		}//======================================

		@Override
		protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)
		{
		}

		protected void setThumbRollover(boolean paramBoolean)
		{
			super.setThumbRollover(paramBoolean);
			if (paramBoolean)
			{
				//				timer.stop();
			}
		}

		@Override
		protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
		{

			int alpha = isThumbRollover() ? SCROLL_BAR_ALPHA_ROLLOVER : SCROLL_BAR_ALPHA;

			int orientation = scrollbar.getOrientation();
			int arc = THUMB_SIZE;
			int x = thumbBounds.x + THUMB_BORDER_SIZE;
			int y = thumbBounds.y + THUMB_BORDER_SIZE;

			int width = orientation == JScrollBar.VERTICAL ? THUMB_SIZE : thumbBounds.width - (THUMB_BORDER_SIZE * 2);
			width = Math.max(width, THUMB_SIZE);

			int height = orientation == JScrollBar.VERTICAL ? thumbBounds.height - (THUMB_BORDER_SIZE * 2) : THUMB_SIZE;
			height = Math.max(height, THUMB_SIZE);

			Graphics2D graphics2D = (Graphics2D) g.create();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics2D.setColor(new Color(THUMB_COLOR.getRed(), THUMB_COLOR.getGreen(), THUMB_COLOR.getBlue(), alpha));
			if (orientation == JScrollBar.VERTICAL)
				height += dh;

			graphics2D.fillRoundRect(x, y, width, height, arc, arc);
			graphics2D.dispose();
		}//======================================
	}//End of MyScrollBarUI

	public JScrollBar getVerticalScrollBar()
	{
		return this.verticalScrollBar;
	}

	public JScrollBar getHorizontalScrollBar()
	{
		return this.horizontalScrollBar;
	}
}//End of LightScrollPane