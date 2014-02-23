package com.moomoohk.MaceSwingerLauncher.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import aurelienribon.slidinglayout.SLKeyframe.Callback;

import com.moomoohk.MaceSwingerLauncher.MainFrame;
import com.moomoohk.MaceSwingerLauncher.MainFrame.View;
import com.moomoohk.MaceSwingerLauncher.utils.LauncherUtils;
import com.moomoohk.MaceSwingerLauncher.utils.Resources;

/**
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @since Dec 20, 2013
 */
public class MenuPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	public int width = 350, height = 270;
	public JButton btnPlay, btnLogOut;

	public MenuPanel()
	{
		setSize(width, height);
		setBorder(BorderFactory.createLineBorder(Resources.foreground));
		setBackground(Resources.background.brighter().brighter());

		JButton btnQuit = new JButton("Quit");
		btnQuit.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		btnQuit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramActionEvent)
			{
				System.exit(0);
			}
		});
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.WEST, btnQuit, 11, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnQuit, -14, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnQuit, -7, SpringLayout.EAST, this);
		setLayout(springLayout);
		add(btnQuit);

		btnLogOut = new JButton("Log out");
		springLayout.putConstraint(SpringLayout.NORTH, btnQuit, 20, SpringLayout.SOUTH, btnLogOut);
		springLayout.putConstraint(SpringLayout.WEST, btnLogOut, 11, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnLogOut, -74, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnLogOut, -7, SpringLayout.EAST, this);
		btnLogOut.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		btnLogOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramActionEvent)
			{
				MainFrame.mainFrame.loginPanel.reset(false);
				MainFrame.mainFrame.animateBetween(View.MENU, View.LOGIN, new Callback()
				{
					public void done()
					{
						MainFrame.mainFrame.loginPanel.txtUser.requestFocus();
						repaint();
					}
				});
			}
		});
		add(btnLogOut);

		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				LauncherUtils.launch(true);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnPlay, 14, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, btnPlay, 12, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnPlay, 54, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnPlay, -7, SpringLayout.EAST, this);
		btnPlay.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		add(btnPlay);

		JButton btnModManagement = new JButton("Mod Management");
		btnModManagement.setEnabled(false);
		springLayout.putConstraint(SpringLayout.NORTH, btnModManagement, 8, SpringLayout.SOUTH, btnPlay);
		springLayout.putConstraint(SpringLayout.WEST, btnModManagement, 11, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnModManagement, -166, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnModManagement, -8, SpringLayout.EAST, this);
		btnModManagement.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		add(btnModManagement);

		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				MainFrame.mainFrame.animateBetween(View.MENU, View.SETTINGS, null);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnLogOut, 6, SpringLayout.SOUTH, btnSettings);
		springLayout.putConstraint(SpringLayout.NORTH, btnSettings, 6, SpringLayout.SOUTH, btnModManagement);
		springLayout.putConstraint(SpringLayout.WEST, btnSettings, 12, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSettings, -120, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnSettings, -7, SpringLayout.EAST, this);
		btnSettings.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		add(btnSettings);
	}
}
