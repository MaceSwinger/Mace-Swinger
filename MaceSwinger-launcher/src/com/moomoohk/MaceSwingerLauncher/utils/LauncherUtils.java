package com.moomoohk.MaceSwingerLauncher.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aurelienribon.slidinglayout.SLKeyframe.Callback;

import com.moomoohk.MaceSwingerLauncher.MainFrame;
import com.moomoohk.MaceSwingerLauncher.MainFrame.View;
import com.moomoohk.MaceSwingerLauncher.dialogs.ResponseDialog;
import com.moomoohk.MooBootstrap.Bootstrap;
import com.moomoohk.Mootilities.ExceptionHandling.ExceptionDisplayDialog;
import com.moomoohk.Mootilities.MooDownloader.gui.DownloaderShell;
import com.moomoohk.Mootilities.OSUtils.OSUtils;
import com.moomoohk.Mootilities.Swing.FrameDragger;

/**
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @since Dec 20, 2013
 */
public class LauncherUtils
{
	public static Bootstrap bootstrap = new Bootstrap("https://maceswinger.com/utils/assets/changelog.xml");

	public static String htmlify(String message)
	{
		String regex = "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum|travel|[a-z]{2}))(:[\\d]{1,5})?(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(message);
		while (m.find())
		{
			String url = m.group();
			if (url.startsWith("(") && url.endsWith(")"))
				url = url.substring(1, url.length() - 1);
			message = message.replace(url, "<a href=" + url + ">" + url + "</a>");
		}
		return message.replace("\n", "<br>");
	}

	public static void initiateBootstrap(final boolean manualUpdate)
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				MainFrame.mainFrame.settingsPanel.btnDeleteGameFiles.setEnabled(false);
				MainFrame.mainFrame.settingsPanel.btnForceUpdateGame.setEnabled(false);
				MainFrame.mainFrame.menuPanel.btnPlay.setEnabled(false);
				MainFrame.mainFrame.menuPanel.btnPlay.setText("Checking for update...");
				System.out.println("Initiating bootstrap process...");
				File game = new File(Resources.gamePath);
				if (!game.exists() || !Resources.versionFile.exists()) //TODO: Check LWJGL files
				{
					game.getParentFile().mkdirs();
					try
					{
						game.createNewFile();
					}
					catch (IOException e)
					{
						new ExceptionDisplayDialog(MainFrame.mainFrame, e);
					}
					if (manualUpdate)
						MainFrame.mainFrame.animateBetween(View.SETTINGS, View.MISSINGFILES, null);
					else
						MainFrame.mainFrame.animateBetween(View.LOGIN, View.MISSINGFILES, null);
				}
				else
				{
					System.out.println("Checking for update...");
					try
					{
						Scanner reader = new Scanner(Resources.versionFile);
						int localBuildNumber = reader.nextInt();
						reader.close();
						if (bootstrap.check(localBuildNumber))
						{
							System.out.println("Found update");
							MainFrame.mainFrame.updatePanel.updateChangelog();
							if (manualUpdate)
								MainFrame.mainFrame.animateBetween(View.SETTINGS, View.UPDATEAVAILABLE, null);
							else
								MainFrame.mainFrame.animateBetween(View.LOGIN, View.UPDATEAVAILABLE, null);
						}
						else
						{
							System.out.println("Up to date");
							if (manualUpdate)
							{
								MainFrame.mainFrame.upToDatePanel.updateChangelog();
								MainFrame.mainFrame.animateBetween(View.SETTINGS, View.UPTODATE, null);
							}
							MainFrame.mainFrame.setButtonsEnabled(true);
							MainFrame.mainFrame.menuPanel.btnPlay.setText("Play");
							if (!manualUpdate)
							{
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
								MainFrame.mainFrame.animateBetween(View.LOGIN, View.MENU, new Callback()
								{
									public void done()
									{
										MainFrame.mainFrame.loginPanel.reset(false);
									}
								});
							}
						}
					}
					catch (Exception e)
					{
						new ExceptionDisplayDialog(MainFrame.mainFrame, e);
					}
				}
				System.out.println();
			}
		}).start();
	}

	public static void downloadGame()
	{
		MainFrame.mainFrame.setButtonsEnabled(false);
		System.out.println("Downloading game...");
		DownloaderShell downloader = new DownloaderShell();
		new FrameDragger().applyTo(downloader);
		downloader.setVisible(true);
		MainFrame.mainFrame.menuPanel.btnPlay.setText("Downloading...");
		try
		{
			int remoteBuildNumber = bootstrap.getRemoteBuildNumber();
			if (!Resources.versionFile.exists())
			{
				Resources.versionFile.getParentFile().mkdirs();
				Resources.versionFile.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(Resources.versionFile.getAbsoluteFile()));
			bw.write("" + remoteBuildNumber);
			bw.close();
			System.out.println("- Downloaded (remote version: " + remoteBuildNumber + ")");
			System.out.println("Donwloading LWJGL natives...");
			ArrayList<String> lwjglFiles = new ArrayList<String>();
			switch (OSUtils.getCurrentOS())
			{
				case MACOSX:
					lwjglFiles.add("macosx/libjinput-osx.jnilib");
					lwjglFiles.add("macosx/liblwjgl.jnilib");
					lwjglFiles.add("macosx/openal.dylib");
					break;
				case WINDOWS:
					lwjglFiles.add("windows/OpenAL32.dll");
					lwjglFiles.add("windows/OpenAL64.dll");
					lwjglFiles.add("windows/jinput-dx8.dll");
					lwjglFiles.add("windows/jinput-dx8_64.dll");
					lwjglFiles.add("windows/jinput-raw.dll");
					lwjglFiles.add("windows/jinput-raw_64.dll");
					lwjglFiles.add("windows/lwjgl.dll");
					lwjglFiles.add("windows/lwjgl64.dll");
					break;
				case UNIX:
					lwjglFiles.add("unix/libjinput-linux.so");
					lwjglFiles.add("unix/libjinput-linux64.so");
					lwjglFiles.add("unix/liblwjgl.so");
					lwjglFiles.add("unix/liblwjgl64.so");
					lwjglFiles.add("unix/libopenal.so");
					lwjglFiles.add("unix/libopenal64.so");
					break;
				case OTHER:
					break;
				default:
					break;
			}
			downloader.setCallback(new Runnable()
			{
				public void run()
				{
					MainFrame.mainFrame.setButtonsEnabled(true);
					MainFrame.mainFrame.menuPanel.btnPlay.setText("Play");
				}
			});
			downloader.download("https://maceswinger.com/utils/assets/MaceSwinger.jar", Resources.gamePath, false, Resources.foreground);
			for (String file : lwjglFiles)
			{
				System.out.println("Downloading " + file);
				downloader.download("https://maceswinger.com/utils/assets/lwjgl/" + file, OSUtils.getDynamicStorageLocation() + "Mace Swinger/lwjgl/" + file, false);
			}
		}
		catch (SocketException e)
		{
			downloader.dispose();
			new ResponseDialog(MainFrame.mainFrame, "Error:", "You are not connected to the Internet!").setVisible(true);
			MainFrame.mainFrame.setButtonsEnabled(true);
			MainFrame.mainFrame.menuPanel.btnPlay.setText("Play");
		}
		catch (Exception e)
		{
			downloader.dispose();
			new ExceptionDisplayDialog(MainFrame.mainFrame, e).setVisible(true);
			MainFrame.mainFrame.animateBetween(View.UPDATEAVAILABLE, View.LOGIN, null);
		}
	}

	public static void launch()
	{
		System.out.println("Launching...");
		if (new File(Resources.gamePath).exists())
		{
			MainFrame.mainFrame.setVisible(false);
			try
			{
				List<String> args = new ArrayList<String>(Arrays.asList("java", "-jar", Resources.gamePath));
				args.add("-eatshitpirates");
				if (MainFrame.prefs.getBoolean("fullscreen", true))
					args.add("-fullscreen");
				StringBuilder b = new StringBuilder();
				for (String arg : args)
					b.append(arg + " ");
				new ProcessBuilder(args).start();
			}
			catch (IOException e)
			{
				new ExceptionDisplayDialog(MainFrame.mainFrame, e);
			}
			System.exit(0);
		}
		else
			new ResponseDialog(MainFrame.mainFrame, "Error:", "The game file appears to be missing/corrupt!\nRedownload it via the settings menu.").setVisible(true);
	}

	public static boolean isGameInstalled()
	{
		return new File(Resources.gamePath).exists();
	}
}
