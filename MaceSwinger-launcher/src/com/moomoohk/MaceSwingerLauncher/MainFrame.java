package com.moomoohk.MaceSwingerLauncher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import aurelienribon.slidinglayout.SLAnimator;
import aurelienribon.slidinglayout.SLConfig;
import aurelienribon.slidinglayout.SLKeyframe;
import aurelienribon.slidinglayout.SLKeyframe.Callback;
import aurelienribon.slidinglayout.SLPanel;
import aurelienribon.slidinglayout.SLSide;
import aurelienribon.tweenengine.Tween;

import com.moomoohk.MaceSwingerLauncher.panels.LoginPanel;
import com.moomoohk.MaceSwingerLauncher.panels.MenuPanel;
import com.moomoohk.MaceSwingerLauncher.panels.MissingFilesPanel;
import com.moomoohk.MaceSwingerLauncher.panels.SettingsPanel;
import com.moomoohk.MaceSwingerLauncher.panels.UpToDatePanel;
import com.moomoohk.MaceSwingerLauncher.panels.UpdatePanel;
import com.moomoohk.MaceSwingerLauncher.utils.ImagePanel;
import com.moomoohk.MaceSwingerLauncher.utils.ImagePanel.ImagePanelAccessor;
import com.moomoohk.MaceSwingerLauncher.utils.LauncherUtils;
import com.moomoohk.MaceSwingerLauncher.utils.Resources;
import com.moomoohk.Mootilities.ExceptionHandling.ExceptionDisplayDialog;
import com.moomoohk.Mootilities.OSUtils.OSUtils;
import com.moomoohk.Mootilities.OSUtils.OSUtils.OS;

/**
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @since Dec 20, 2013
 */
public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	public static MainFrame mainFrame;
	public static Preferences prefs = Preferences.userNodeForPackage(MainFrame.class);

	private boolean started = false;
	private JPanel contentPane;
	private final SLPanel panel = new SLPanel();
	private ImagePanel logo, footer;
	private SLConfig emptyCfg, splashCfg, loginCfg, menuCfg, settingsCfg, updateCfg, missingFilesCfg, upToDateCfg;
	private SLKeyframe splashKf, loginKf;

	public MenuPanel menuPanel = new MenuPanel();
	public LoginPanel loginPanel = new LoginPanel();
	public SettingsPanel settingsPanel = new SettingsPanel();
	public UpdatePanel updatePanel = new UpdatePanel();
	public UpToDatePanel upToDatePanel = new UpToDatePanel();
	public MissingFilesPanel missingFilesPanel = new MissingFilesPanel();

	private ImagePanel settingsLogo = new ImagePanel(Resources.settingsLogo, false, null);

	public static enum View
	{
		SPLASH, LOGIN, MENU, SETTINGS, UPDATEAVAILABLE, MISSINGFILES, UPTODATE;
	}

	public MainFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500, 530));
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Mace Swinger Launcher");
		setIconImage(new ImageIcon(Resources.shield).getImage());
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().setBackground(Resources.background);

		this.logo = new ImagePanel(Resources.logo, true, "https://maceswinger.com");
		this.logo.setEnabled(false);

		this.footer = new ImagePanel(Resources.footer, false, null);
		this.footer.setEnabled(false);

		emptyCfg = new SLConfig(panel);
		splashCfg = new SLConfig(panel).row(0.5f).row(120).row(0.5f).col(1f).col(300).col(1f).place(1, 1, logo);
		loginCfg = new SLConfig(panel).gap(10, 10).col(1f).col(300).col(1f).row(0.5f).row(120).row(0.5f).row(245).row(0.5f).row(50).place(1, 1, logo).place(3, 1, loginPanel).place(5, 1, footer);
		menuCfg = new SLConfig(panel).gap(10, 20).col(1f).col(menuPanel.width).col(1f).row(1f).row(120).beginGrid(1, 1).col(1f).col(300).col(1f).row(120).place(0, 1, logo).endGrid().row(menuPanel.height).row(1f).place(2, 1, menuPanel);
		settingsCfg = new SLConfig(panel).gap(10, 10).col(1f).col(settingsPanel.width).col(1f).row(1f).row(120).beginGrid(1, 1).col(1f).col(300).col(1f).row(120).place(0, 1, settingsLogo).endGrid().row(settingsPanel.height).row(1f).place(2, 1, settingsPanel);
		updateCfg = new SLConfig(panel).gap(10, 10).col(1f).col(400).col(1f).row(1f).row(120).beginGrid(1, 1).col(1f).col(300).col(1f).row(120).place(0, 1, logo).endGrid().row(300).row(1f).place(2, 1, updatePanel);
		missingFilesCfg = new SLConfig(panel).gap(10, 10).col(1f).col(300).col(1f).row(0.5f).row(120).row(0.5f).row(245).row(0.5f).place(1, 1, logo).place(3, 1, missingFilesPanel);
		upToDateCfg = new SLConfig(panel).gap(10, 10).col(1f).col(400).col(1f).row(1f).row(120).beginGrid(1, 1).col(1f).col(300).col(1f).row(120).place(0, 1, logo).endGrid().row(300).row(1f).place(2, 1, upToDatePanel);

		splashKf = new SLKeyframe(splashCfg, 0.5f).setStartSide(SLSide.TOP, logo);
		loginKf = new SLKeyframe(loginCfg, 0.5f).setStartSide(SLSide.BOTTOM, loginPanel).setStartSide(SLSide.BOTTOM, footer).setDelay(0.5f, logo).setDelay(0.5f, loginPanel).setDelay(1f, footer);

		panel.setTweenManager(SLAnimator.createTweenManager());
		if (prefs.getBoolean("launcher animation", true))
			panel.initialize(emptyCfg);
		else
			panel.initialize(loginCfg);
	}

	public void setVisible(boolean f)
	{
		super.setVisible(f);
		if (f && !started && prefs.getBoolean("launcher animation", true))
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						Thread.sleep(1000);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					started = true;
					splash();
				}
			}).start();
		else
		{
			loginPanel.txtUser.requestFocus();
			logo.setEnabled(true);
			footer.setEnabled(true);
			repaint();
		}
	}

	public void splash()
	{
		panel.createTransition().push(splashKf).push(loginKf.setDelay(3f, logo).setDelay(3f, loginPanel).setDelay(3.5f, footer).setCallback(new Callback()
		{
			public void done()
			{
				loginPanel.txtUser.requestFocus();
				logo.setEnabled(true);
				footer.setEnabled(true);
				repaint();
			}
		})).play();
	}

	public void animateBetween(View from, View to, Callback c)
	{
		float duration = 0.5f;
		SLKeyframe temp = null;

		switch (to)
		{
			case LOGIN:
				temp = new SLKeyframe(loginCfg, duration).setStartSide(SLSide.BOTTOM, loginPanel).setDelay(duration, loginPanel).setStartSide(SLSide.BOTTOM, footer).setDelay(duration * 2f, footer);
				break;
			case MENU:
				temp = new SLKeyframe(menuCfg, duration).setStartSide(SLSide.BOTTOM, menuPanel).setDelay(duration, menuPanel);
				break;
			case SETTINGS:
				temp = new SLKeyframe(settingsCfg, duration).setStartSide(SLSide.BOTTOM, settingsPanel).setDelay(duration, settingsPanel).setStartSide(SLSide.TOP, settingsLogo).setDelay(duration, settingsLogo).setEndSide(SLSide.TOP, logo);
				break;
			case UPDATEAVAILABLE:
				temp = new SLKeyframe(updateCfg, duration).setStartSide(SLSide.RIGHT, updatePanel).setDelay(duration, updatePanel);
				break;
			case MISSINGFILES:
				temp = new SLKeyframe(missingFilesCfg, duration).setStartSide(SLSide.RIGHT, missingFilesPanel).setDelay(duration, missingFilesPanel);
				break;
			case UPTODATE:
				temp = new SLKeyframe(upToDateCfg, duration).setStartSide(SLSide.RIGHT, upToDatePanel).setDelay(duration, upToDatePanel);
				break;
			default:
				System.out.println("No animation defined between for to = " + to);
				return;
		}

		switch (from)
		{
			case LOGIN:
				temp.setEndSide(SLSide.LEFT, loginPanel).setEndSide(SLSide.LEFT, footer);
				break;
			case MENU:
				temp.setEndSide(SLSide.LEFT, menuPanel);
				break;
			case SETTINGS:
				temp.setEndSide(SLSide.TOP, settingsLogo).setEndSide(SLSide.LEFT, settingsPanel).setStartSide(SLSide.TOP, logo).setDelay(duration, logo);
				break;
			case UPDATEAVAILABLE:
				temp.setEndSide(SLSide.LEFT, updatePanel);
				break;
			case MISSINGFILES:
				temp.setEndSide(SLSide.LEFT, missingFilesPanel);
				break;
			case UPTODATE:
				temp.setEndSide(SLSide.LEFT, upToDatePanel);
				break;
			default:
				System.out.println("No animation defined between for from = " + from);
				return;
		}

		if (c != null)
			temp.setCallback(c);

		panel.createTransition().push(temp).play();
	}

	public void setButtonsEnabled(boolean f)
	{
		menuPanel.btnPlay.setEnabled(f);
		menuPanel.btnLogOut.setEnabled(f);
		settingsPanel.btnDeleteGameFiles.setEnabled(f);
		settingsPanel.btnForceUpdateGame.setEnabled(f);
		settingsPanel.btnForceDownload.setEnabled(f);
		if (f)
		{
			loginPanel.btnOffline.setEnabled(LauncherUtils.isGameInstalled());
			menuPanel.btnPlay.requestFocus();
		}
	}

	public static void main(String[] args)
	{
		if (OSUtils.getCurrentOS() == OS.MACOSX)
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Mace Swinger Launcher");
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.put("nimbusOrange", Resources.foreground);
		}
		catch (Exception e)
		{
			new ExceptionDisplayDialog(null, e);
		}
		Tween.registerAccessor(ImagePanel.class, new ImagePanelAccessor());
		SLAnimator.start();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					mainFrame = new MainFrame();
					mainFrame.setVisible(true);
				}
				catch (Exception e)
				{
					new ExceptionDisplayDialog(null, e);
				}
			}
		});
	}
}
