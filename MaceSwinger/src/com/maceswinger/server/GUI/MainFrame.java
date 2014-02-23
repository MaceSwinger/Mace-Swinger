package com.maceswinger.server.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.maceswinger.net.ServerShell;
import com.maceswinger.server.Client;
import com.maceswinger.server.GameServer;
import com.maceswinger.server.GUI.commands.BanCommand;
import com.maceswinger.server.GUI.commands.ClearCommand;
import com.maceswinger.server.GUI.commands.HelpCommand;
import com.maceswinger.server.GUI.commands.KickCommand;
import com.maceswinger.server.GUI.commands.SayCommand;
import com.maceswinger.server.GUI.commands.StartCommand;
import com.maceswinger.server.GUI.commands.StopCommand;
import com.maceswinger.server.GUI.commands.UnbanCommand;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooCommands.CommandsManager;

public class MainFrame extends JFrame implements ServerShell
{
	public static MainFrame frame;

	public GameServer server = new GameServer(this);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField txtInput;
	private StyledDocument consoleDoc;
	private SimpleAttributeSet consoleAttributeSet;
	private JScrollPane scrlConsole;
	private Color defaultColor;
	private ArrayList<String> log = new ArrayList<String>();
	private int lastCommandSelector;
	private DefaultListModel userListModel;
	private JLabel lblIpWillGo;
	public JTextPane consoleTextPane;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					frame = new MainFrame();
					frame.setVisible(true);

					new SayCommand();
					new HelpCommand();
					new StartCommand();
					new StopCommand();
					new KickCommand();
					new BanCommand();
					new UnbanCommand();
					new ClearCommand();

					frame.processCommand("/start 2650");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame()
	{
		this(Color.black);
	}

	public MainFrame(Color defaultColor)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(450, 400));
		setLocationRelativeTo(null);

		this.defaultColor = defaultColor;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JPanel panelUserList = new JPanel();
		SpringLayout sl_panelUserList = new SpringLayout();
		panelUserList.setLayout(sl_panelUserList);

		JScrollPane scrollPane = new JScrollPane();
		sl_panelUserList.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, panelUserList);
		sl_panelUserList.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panelUserList);
		sl_panelUserList.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, panelUserList);
		sl_panelUserList.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, panelUserList);
		panelUserList.add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		final JList listUsers = new JList();
		userListModel = new DefaultListModel();
		listUsers.setModel(userListModel);
		listUsers.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent me)
			{
				check(me);
			}

			public void mouseReleased(MouseEvent me)
			{
				check(me);
			}

			private void check(MouseEvent me)
			{
				if (listUsers.getSelectedValues().length == 0)
					listUsers.setSelectedIndex(listUsers.locationToIndex(me.getPoint()));
				if (me.isPopupTrigger() && listUsers.getSelectedIndex() > -1)
				{
					JPopupMenu popup = new JPopupMenu();
					if (listUsers.getSelectedIndices().length == 1 || listUsers.getSelectedIndices().length == 0)
					{
						listUsers.setSelectedIndex(listUsers.locationToIndex(me.getPoint()));
						JMenuItem kick = new JMenuItem("Kick " + listUsers.getSelectedValue());
						kick.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								processCommand("/kick" + (String) listUsers.getSelectedValue());
							}
						});
						popup.add(kick);
						JMenuItem ban = new JMenuItem("Ban " + listUsers.getSelectedValue());
						ban.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								processCommand("/ban" + (String) listUsers.getSelectedValue());
							}
						});
						popup.add(ban);
						JMenuItem kickban = new JMenuItem("Kickban " + listUsers.getSelectedValue());
						kickban.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								processCommand("/kick" + (String) listUsers.getSelectedValue());
								processCommand("/ban" + (String) listUsers.getSelectedValue());
							}
						});
						popup.add(kickban);
					}
					else
					{
						JMenuItem kick = new JMenuItem("Kick " + listUsers.getSelectedValues().length + " players");
						kick.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent arg0)
							{
								for (Object username : listUsers.getSelectedValues())
									processCommand("/kick" + username);
							}
						});
						popup.add(kick);
						JMenuItem ban = new JMenuItem("Ban" + listUsers.getSelectedValues().length + " players");
						ban.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent arg0)
							{
								for (Object username : listUsers.getSelectedValues())
									processCommand("/ban" + username);
							}
						});
						popup.add(ban);
						JMenuItem kickban = new JMenuItem("Kickban" + listUsers.getSelectedValues().length + " players");
						kickban.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent arg0)
							{
								for (Object username : listUsers.getSelectedValues())
								{
									processCommand("/kick" + username);
									processCommand("/ban" + username);
								}
							}
						});
						popup.add(kick);
						popup.add(ban);
						popup.add(kickban);
					}
					popup.show(listUsers, me.getX(), me.getY());
				}
			}
		});
		scrollPane.setViewportView(listUsers);

		JSplitPane splitPane = new JSplitPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, splitPane, 50, SpringLayout.NORTH, contentPane);
		splitPane.setDividerLocation(130);
		sl_contentPane.putConstraint(SpringLayout.WEST, splitPane, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, splitPane, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, splitPane, -10, SpringLayout.EAST, contentPane);
		contentPane.add(splitPane);
		JPanel panelConsole = new JPanel();
		panelConsole.setMinimumSize(new Dimension(200, 100));
		splitPane.setRightComponent(panelConsole);
		SpringLayout sl_panelConsole = new SpringLayout();
		panelConsole.setLayout(sl_panelConsole);
		splitPane.setLeftComponent(panelUserList);

		txtInput = new JTextField();
		sl_panelConsole.putConstraint(SpringLayout.SOUTH, txtInput, -10, SpringLayout.SOUTH, panelConsole);
		sl_panelConsole.putConstraint(SpringLayout.EAST, txtInput, -10, SpringLayout.EAST, panelConsole);
		panelConsole.add(txtInput);
		txtInput.setColumns(10);
		this.txtInput.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent ke)
			{
				if (ke.getKeyCode() == 38)
				{
					if (log.size() > 0)
					{
						if (lastCommandSelector != log.size() - 1)
							lastCommandSelector++;
						if (lastCommandSelector == log.size())
							lastCommandSelector = log.size() - 1;
						txtInput.setText(log.get(lastCommandSelector));
					}
				}
				else
					if (ke.getKeyCode() == 40)
					{
						if (lastCommandSelector != -1)
							lastCommandSelector--;
						if (lastCommandSelector == -1)
							txtInput.setText("");
						if (log.size() > 0 && lastCommandSelector >= 0)
							txtInput.setText(log.get(lastCommandSelector));
					}
					else
						lastCommandSelector = -1;
				if (ke.getKeyCode() == 10)
				{
					if (txtInput.getText().trim().length() != 0 && log.indexOf(txtInput.getText()) != 0)
						log.add(0, txtInput.getText());
					lastCommandSelector = -1;
					if (txtInput.getText().trim().length() == 0)
					{
						txtInput.setText("");
						return;
					}
					processCommand(txtInput.getText());
					txtInput.setText("");
				}
				if (ke.getKeyCode() == 27)
					txtInput.setText("");
			}

			public void keyReleased(KeyEvent ke)
			{
				if (ke.getKeyCode() == 40 || ke.getKeyCode() == 38)
					txtInput.setCaretPosition(txtInput.getText().length());
			}
		});

		JPanel nowrapPanel = new JPanel();
		nowrapPanel.setLayout(new BorderLayout(0, 0));

		scrlConsole = new JScrollPane(nowrapPanel);
		sl_panelConsole.putConstraint(SpringLayout.WEST, scrlConsole, 0, SpringLayout.WEST, panelConsole);
		scrlConsole.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sl_panelConsole.putConstraint(SpringLayout.NORTH, txtInput, 10, SpringLayout.SOUTH, scrlConsole);
		sl_panelConsole.putConstraint(SpringLayout.WEST, txtInput, 0, SpringLayout.WEST, scrlConsole);
		sl_panelConsole.putConstraint(SpringLayout.EAST, scrlConsole, -10, SpringLayout.EAST, panelConsole);
		sl_panelConsole.putConstraint(SpringLayout.NORTH, scrlConsole, 10, SpringLayout.NORTH, panelConsole);
		sl_panelConsole.putConstraint(SpringLayout.SOUTH, scrlConsole, -48, SpringLayout.SOUTH, panelConsole);
		panelConsole.add(scrlConsole);

		JPanel panelPerformance = new JPanel();
		panelPerformance.setBorder(new LineBorder(Color.LIGHT_GRAY));
		sl_contentPane.putConstraint(SpringLayout.NORTH, panelPerformance, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panelPerformance, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panelPerformance, -10, SpringLayout.NORTH, splitPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panelPerformance, 0, SpringLayout.EAST, splitPane);
		contentPane.add(panelPerformance);
		SpringLayout sl_panelPerformance = new SpringLayout();
		panelPerformance.setLayout(sl_panelPerformance);

		lblIpWillGo = new JLabel();
		sl_panelPerformance.putConstraint(SpringLayout.NORTH, lblIpWillGo, 0, SpringLayout.NORTH, panelPerformance);
		sl_panelPerformance.putConstraint(SpringLayout.WEST, lblIpWillGo, 0, SpringLayout.WEST, panelPerformance);
		sl_panelPerformance.putConstraint(SpringLayout.SOUTH, lblIpWillGo, 28, SpringLayout.NORTH, panelPerformance);
		sl_panelPerformance.putConstraint(SpringLayout.EAST, lblIpWillGo, 418, SpringLayout.WEST, panelPerformance);
		panelPerformance.add(lblIpWillGo);

		consoleTextPane = new JTextPane();
		consoleTextPane.setEditable(false);
		this.consoleDoc = consoleTextPane.getStyledDocument();
		this.consoleAttributeSet = new SimpleAttributeSet();
		this.consoleDoc.setParagraphAttributes(0, this.consoleDoc.getLength(), this.consoleAttributeSet, false);
		DefaultCaret caret = (DefaultCaret) consoleTextPane.getCaret();
		caret.setUpdatePolicy(1);
		consoleTextPane.setFont(new Font("Monospaced", consoleTextPane.getFont().getStyle(), consoleTextPane.getFont().getSize()));
		nowrapPanel.add(consoleTextPane, "Center");

		addWindowFocusListener(new WindowFocusListener()
		{
			@Override
			public void windowLostFocus(WindowEvent arg0)
			{
			}

			@Override
			public void windowGainedFocus(WindowEvent arg0)
			{
				txtInput.requestFocus();
			}
		});
	}

	public void addLine(String line)
	{
		addText(line + "\n");
	}

	public void addLine(String line, Color c)
	{
		addText(line + "\n", c);
	}

	public void addText(String text)
	{
		addText(text, defaultColor);
	}

	public void addText(String text, Color color)
	{
		if (color != null)
			StyleConstants.setForeground(this.consoleAttributeSet, color);
		else
			StyleConstants.setForeground(this.consoleAttributeSet, Color.white);
		try
		{
			final JScrollBar vbar = this.scrlConsole.getVerticalScrollBar();
			boolean atBottom = vbar.getMaximum() == vbar.getValue() + vbar.getVisibleAmount();
			this.consoleDoc.insertString(this.consoleDoc.getLength(), text, this.consoleAttributeSet);
			if (atBottom)
			{
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						vbar.setValue(vbar.getMaximum());
					}
				});
			}
		}
		catch (BadLocationException e)
		{
			addText("[ERROR]: " + e.getStackTrace().toString() + "/n", Color.red);
		}
	}

	public void setDefaultColor(Color c)
	{
		this.defaultColor = c;
	}

	public Color getDefaultColor()
	{
		return this.defaultColor;
	}

	public void processCommand(String input)
	{
		if (!input.startsWith("/"))
		{
			ArrayList<String> message = new ArrayList<String>();
			Scanner s = new Scanner(input);
			while (s.hasNext())
				message.add(s.next());
			s.close();
			CommandsManager.findCommand("/say").checkAndExecute(message.toArray(new String[message.size()]));
		}
		else
			try
			{
				Command command = CommandsManager.findCommand(input);
				if (command == null)
					addLine("Command not found!", Color.red);
				else
				{
					String output = command.checkAndExecute(CommandsManager.parseParams(txtInput.getText()));
					if (output != null && output.trim() != "")
						addLine(output, command.getOutputColor() == null ? defaultColor : command.getOutputColor());
				}
			}
			catch (NoClassDefFoundError e)
			{
				addText("Problem! Are you sure you have MooCommands installed? Get the latest version here: https://github.com/moomoohk/MooCommands/raw/master/Build/MooCommands.jar\n", Color.red);
			}
	}

	@Override
	public void clientConnected(Client c)
	{
		userListModel.addElement(c);
		addLine(c.getUsername() + " connected");
	}

	@Override
	public void clientDisconnected(Client c)
	{
		userListModel.removeElement(c);
		addLine(c.getUsername() + " disconnected");
	}

	@Override
	public void serverStarted(int port)
	{
		lblIpWillGo.setText(GameServer.getIP());
		addLine("Server started on port " + port);
	}

	@Override
	public void serverStopped()
	{
		addLine("Server stopped");
	}

	@Override
	public void message(Client c, String message)
	{
		addLine(c.getUsername() + ": " + message);
	}

	@Override
	public void portInUse(int port)
	{
		addLine("Unable to lock port: " + port + ", is another server running?");
	}
}