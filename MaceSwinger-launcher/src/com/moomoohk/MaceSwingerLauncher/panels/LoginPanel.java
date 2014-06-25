package com.moomoohk.MaceSwingerLauncher.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.net.ssl.SSLHandshakeException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.moomoohk.MaceSwingerLauncher.MainFrame;
import com.moomoohk.MaceSwingerLauncher.dialogs.ResponseDialog;
import com.moomoohk.MaceSwingerLauncher.utils.CryptoUtils;
import com.moomoohk.MaceSwingerLauncher.utils.LauncherUtils;
import com.moomoohk.MaceSwingerLauncher.utils.Resources;
import com.moomoohk.MaceSwingerLauncher.utils.SSLUtils;
import com.moomoohk.Mootilities.ExceptionHandling.ExceptionDisplayDialog;

/**
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @since Dec 23, 2013
 */
public class LoginPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	public JTextField txtUser;
	public JPasswordField txtPassword;
	public JButton btnLogin, btnOffline;

	public LoginPanel()
	{
		super(null);

		setBorder(BorderFactory.createLineBorder(Resources.foreground));
		setBackground(Resources.background.brighter().brighter());

		JLabel lblUser = new JLabel("Username:");
		lblUser.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 20));
		lblUser.setForeground(Resources.foreground);
		lblUser.setBounds(10, 10, 280, 30);
		add(lblUser);

		txtUser = new JTextField();
		txtUser.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 14));
		txtUser.setBackground(Resources.background.brighter());
		txtUser.setForeground(Resources.foreground);
		txtUser.setSelectedTextColor(Resources.background.brighter().brighter());
		txtUser.setBounds(10, 45, 280, 30);
		txtUser.addFocusListener(new FocusListener()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				txtUser.setSelectionStart(0);
				txtUser.setSelectionEnd(0);
				txtUser.setCaretPosition(txtUser.getText().length());
			}

			@Override
			public void focusGained(FocusEvent e)
			{
				txtUser.setSelectionStart(0);
				txtUser.setSelectionEnd(txtUser.getText().length());
			}
		});
		add(txtUser);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 20));
		lblPassword.setForeground(Resources.foreground);
		lblPassword.setBounds(10, 85, 280, 30);
		add(lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setEchoChar('\u2022');
		txtPassword.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 14));
		txtPassword.setBackground(Resources.background.brighter());
		txtPassword.setForeground(Resources.foreground);
		txtPassword.setSelectedTextColor(Resources.background.brighter().brighter());
		txtPassword.setBounds(10, 120, 280, 30);
		txtPassword.addFocusListener(new FocusListener()
		{
			public void focusLost(FocusEvent arg0)
			{
				txtPassword.setSelectionStart(0);
				txtPassword.setSelectionEnd(0);
				txtPassword.setCaretPosition(txtPassword.getPassword().length);
			}

			@Override
			public void focusGained(FocusEvent arg0)
			{
				txtPassword.setSelectionStart(0);
				txtPassword.setSelectionEnd(txtPassword.getPassword().length);
			}
		});
		add(txtPassword);

		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		btnLogin.setBounds(10, 160, 280, 30);
		add(btnLogin);
		btnLogin.setEnabled(false);

		btnOffline = new JButton("Play offline");
		btnOffline.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		btnOffline.setBounds(10, 200, 280, 30);
		add(btnOffline);
		btnOffline.setEnabled(LauncherUtils.isGameInstalled());
		btnOffline.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				LauncherUtils.launch(false);
			}
		});

		KeyListener keyListener = new KeyAdapter()
		{
			public void keyPressed(KeyEvent arg0)
			{
				if (arg0.getKeyCode() == 10)
					if (arg0.getSource().equals(txtUser))
						txtPassword.requestFocus();
					else
					{
						btnLogin.doClick();
						txtUser.requestFocus();
					}
				if (arg0.getKeyCode() == 27)
					((JTextField) arg0.getSource()).setText("");
			}

			@Override
			public void keyReleased(final KeyEvent arg0)
			{
				if (arg0.getKeyCode() == 10)
					return;
				if (txtUser.getText().trim().length() > 0 && new String(txtPassword.getPassword()).trim().length() > 0)
					btnLogin.setEnabled(true);
				else
					btnLogin.setEnabled(false);
			}
		};
		txtUser.addKeyListener(keyListener);
		txtPassword.addKeyListener(keyListener);

		btnLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				btnLogin.setEnabled(false);
				txtUser.setEnabled(false);
				txtPassword.setEnabled(false);
				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						try
						{
							btnLogin.setText("Connecting...");
							if (!SSLUtils.doneCerts && !SSLUtils.installCerts())
								return;

							btnLogin.setText("Resolving request...");
							System.out.println("Connecting to the Mace Swinger A.R.I.S.T.O.T.L.E. login API...");

							String response = LauncherUtils.connect("login", new String[] { "user", "pass" }, new String[] { txtUser.getText().trim(), CryptoUtils.toSHA512(new String(txtPassword.getPassword()).getBytes()).trim() });
							Scanner s = new Scanner(response);
							s.useDelimiter(":");

							if (s.hasNext() && s.next().equals("true"))
							{
								MainFrame.sid = s.next();
								System.out.println("sid:" + MainFrame.sid);
								btnLogin.setText("Logged in!");
								LauncherUtils.initiateBootstrap(false);
							}
							else
							{
								reset(false);
								new ResponseDialog(MainFrame.mainFrame, "Server returned:", response).setVisible(true);
							}
						}
						catch (UnknownHostException e)
						{
							reset(false);
							new ResponseDialog(MainFrame.mainFrame, "Error:", "You are not connected to the Internet!").setVisible(true);
						}
						catch (SocketTimeoutException e)
						{
							reset(false);
							new ResponseDialog(MainFrame.mainFrame, "Error:", "It took too long to connect to our login service.\nIs your Internet connection healthy?").setVisible(true);
						}
						catch (SSLHandshakeException e)
						{
							reset(false);
							e.printStackTrace();
							new ResponseDialog(MainFrame.mainFrame, "Error:", "There was a problem connecting using SSL.\nIf the problem persists, kindly restart the launcher and try again.").setVisible(true);
						}
						catch (ConnectException e)
						{
							e.printStackTrace();
							reset(false);
							new ResponseDialog(MainFrame.mainFrame, "Error:", "There was a problem establishing a connection to our servers. Please try again later.").setVisible(true);
						}
						catch (Exception e)
						{
							reset(false);
							new ExceptionDisplayDialog(MainFrame.mainFrame, e).setVisible(true);
						}
						System.out.println();
					}
				});
				t.start();
			}
		});

		if (MainFrame.prefs.getBoolean("save login", false) && !MainFrame.prefs.get("pass", "").equals(""))
		{
			try
			{
				txtUser.setText(MainFrame.prefs.get("user", null));
				txtPassword.setText(CryptoUtils.AESdecrypt(MainFrame.prefs.get("pass", "")));
				btnLogin.setEnabled(true);
			}
			catch (Exception e)
			{
				new ExceptionDisplayDialog(MainFrame.mainFrame, e).setVisible(true);
			}
		}
	}

	public void reset(boolean clearPasswordField)
	{
		txtUser.setEnabled(true);
		txtPassword.setEnabled(true);
		btnLogin.setText("Login");
		if (clearPasswordField)
			txtPassword.setText("");
		btnLogin.setEnabled(!clearPasswordField);
	}
}
