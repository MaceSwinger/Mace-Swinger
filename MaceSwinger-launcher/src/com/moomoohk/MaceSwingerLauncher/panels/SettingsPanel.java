package com.moomoohk.MaceSwingerLauncher.panels;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.moomoohk.MaceSwingerLauncher.MainFrame;
import com.moomoohk.MaceSwingerLauncher.MainFrame.View;
import com.moomoohk.MaceSwingerLauncher.dialogs.ResponseDialog;
import com.moomoohk.MaceSwingerLauncher.utils.CryptoUtils;
import com.moomoohk.MaceSwingerLauncher.utils.LauncherUtils;
import com.moomoohk.MaceSwingerLauncher.utils.Resources;
import com.moomoohk.Mootilities.ExceptionHandling.ExceptionDisplayDialog;
import com.moomoohk.Mootilities.FileUtils.FileUtils;

public class SettingsPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public int width = 350, height = 270;
	public JButton btnForceUpdateGame;
	public JButton btnDeleteGameFiles;
	public JButton btnForceDownload;

	public SettingsPanel()
	{
		setBorder(BorderFactory.createLineBorder(Resources.foreground));
		setBackground(Resources.background.brighter().brighter());

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		setSize(width, height);

		final JCheckBox chckbxPlayAnimation = new JCheckBox("Play launcher start animation");
		chckbxPlayAnimation.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 14));
		chckbxPlayAnimation.setForeground(Resources.foreground);
		chckbxPlayAnimation.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent me)
			{
				chckbxPlayAnimation.requestFocus();
			}
		});
		chckbxPlayAnimation.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				MainFrame.prefs.putBoolean("launcher animation", e.getStateChange() == 1);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, chckbxPlayAnimation, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, chckbxPlayAnimation, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, chckbxPlayAnimation, 340, SpringLayout.WEST, this);
		chckbxPlayAnimation.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(chckbxPlayAnimation);

		final JCheckBox chckbxFullscreen = new JCheckBox("Start game in fullscreen");
		springLayout.putConstraint(SpringLayout.WEST, chckbxFullscreen, 0, SpringLayout.WEST, chckbxPlayAnimation);
		springLayout.putConstraint(SpringLayout.SOUTH, chckbxFullscreen, -189, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, chckbxFullscreen, 0, SpringLayout.EAST, chckbxPlayAnimation);
		chckbxFullscreen.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 14));
		chckbxFullscreen.setForeground(Resources.foreground);
		chckbxFullscreen.setCursor(new Cursor(Cursor.HAND_CURSOR));
		chckbxFullscreen.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent me)
			{
				chckbxFullscreen.requestFocus();
			}
		});
		chckbxFullscreen.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				MainFrame.prefs.putBoolean("fullscreen", e.getStateChange() == 1);
			}
		});
		add(chckbxFullscreen);

		final JCheckBox chckbxSaveLoginCredentials = new JCheckBox("Save login credentials");
		springLayout.putConstraint(SpringLayout.NORTH, chckbxFullscreen, 6, SpringLayout.SOUTH, chckbxSaveLoginCredentials);
		springLayout.putConstraint(SpringLayout.SOUTH, chckbxSaveLoginCredentials, -214, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxSaveLoginCredentials, 6, SpringLayout.SOUTH, chckbxPlayAnimation);
		springLayout.putConstraint(SpringLayout.WEST, chckbxSaveLoginCredentials, 0, SpringLayout.WEST, chckbxPlayAnimation);
		springLayout.putConstraint(SpringLayout.EAST, chckbxSaveLoginCredentials, 0, SpringLayout.EAST, chckbxPlayAnimation);
		chckbxSaveLoginCredentials.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 14));
		chckbxSaveLoginCredentials.setForeground(Resources.foreground);
		chckbxSaveLoginCredentials.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(chckbxSaveLoginCredentials);

		btnForceUpdateGame = new JButton("Check for updates now");
		springLayout.putConstraint(SpringLayout.NORTH, btnForceUpdateGame, 9, SpringLayout.SOUTH, chckbxFullscreen);
		springLayout.putConstraint(SpringLayout.WEST, btnForceUpdateGame, 0, SpringLayout.WEST, chckbxPlayAnimation);
		springLayout.putConstraint(SpringLayout.EAST, btnForceUpdateGame, 0, SpringLayout.EAST, chckbxPlayAnimation);
		btnForceUpdateGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				LauncherUtils.initiateBootstrap(true);
			}
		});
		btnForceUpdateGame.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		add(btnForceUpdateGame);

		btnDeleteGameFiles = new JButton("Delete all game and launcher related files");
		btnDeleteGameFiles.setToolTipText(Resources.saveDir);
		springLayout.putConstraint(SpringLayout.WEST, btnDeleteGameFiles, 0, SpringLayout.WEST, chckbxPlayAnimation);
		springLayout.putConstraint(SpringLayout.EAST, btnDeleteGameFiles, 0, SpringLayout.EAST, chckbxPlayAnimation);
		btnDeleteGameFiles.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (new File(Resources.gamePath).exists())
				{
					if (JOptionPane.showConfirmDialog(MainFrame.mainFrame, "This cannot be undone.", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION)
					{
						btnDeleteGameFiles.setEnabled(false);
						FileUtils.delete(new File(Resources.saveDir));
						MainFrame.mainFrame.loginPanel.btnOffline.setEnabled(false);
						JOptionPane.showMessageDialog(MainFrame.mainFrame, "Done", "", JOptionPane.PLAIN_MESSAGE);
					}
				}
				else
					new ResponseDialog(MainFrame.mainFrame, "Error:", "No game files to delete.").setVisible(true);
			}
		});
		btnDeleteGameFiles.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		add(btnDeleteGameFiles);

		JButton btnBackToMain = new JButton("Back to main menu");
		springLayout.putConstraint(SpringLayout.SOUTH, btnDeleteGameFiles, -26, SpringLayout.NORTH, btnBackToMain);
		springLayout.putConstraint(SpringLayout.NORTH, btnBackToMain, 225, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnBackToMain, 0, SpringLayout.EAST, chckbxPlayAnimation);
		springLayout.putConstraint(SpringLayout.WEST, btnBackToMain, 10, SpringLayout.WEST, this);
		btnBackToMain.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				MainFrame.mainFrame.animateBetween(View.SETTINGS, View.MENU, null);
			}
		});
		btnBackToMain.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.SOUTH, btnBackToMain, -10, SpringLayout.SOUTH, this);
		add(btnBackToMain);

		chckbxPlayAnimation.setSelected(MainFrame.prefs.getBoolean("launcher animation", true));
		chckbxFullscreen.setSelected(MainFrame.prefs.getBoolean("fullscreen", true));
		chckbxSaveLoginCredentials.setSelected(MainFrame.prefs.getBoolean("save login", false));

		btnForceDownload = new JButton("Force download the game now");
		btnForceDownload.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				LauncherUtils.downloadGame();
			}
		});
		btnForceDownload.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.NORTH, btnForceDownload, 127, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnForceDownload, -108, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnForceUpdateGame, -6, SpringLayout.NORTH, btnForceDownload);
		springLayout.putConstraint(SpringLayout.NORTH, btnDeleteGameFiles, 6, SpringLayout.SOUTH, btnForceDownload);
		springLayout.putConstraint(SpringLayout.WEST, btnForceDownload, 1, SpringLayout.WEST, chckbxPlayAnimation);
		springLayout.putConstraint(SpringLayout.EAST, btnForceDownload, 0, SpringLayout.EAST, chckbxPlayAnimation);
		add(btnForceDownload);

		chckbxSaveLoginCredentials.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				MainFrame.prefs.putBoolean("save login", ie.getStateChange() == 1);
				if (MainFrame.prefs.getBoolean("save login", false))
					try
					{
						MainFrame.prefs.put("user", MainFrame.mainFrame.loginPanel.txtUser.getText());
						MainFrame.prefs.put("pass", CryptoUtils.AESencrypt(new String(MainFrame.mainFrame.loginPanel.txtPassword.getPassword())));
					}
					catch (Exception e)
					{
						new ExceptionDisplayDialog(MainFrame.mainFrame, e).setVisible(true);
					}
				else
				{
					MainFrame.prefs.remove("user");
					MainFrame.prefs.remove("pass");
					MainFrame.mainFrame.loginPanel.txtUser.setText("");
					MainFrame.mainFrame.loginPanel.txtPassword.setText("");
				}
			}
		});
		chckbxSaveLoginCredentials.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent me)
			{
				chckbxSaveLoginCredentials.requestFocus();
			}
		});
	}
}
