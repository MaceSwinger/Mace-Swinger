package com.moomoohk.updatemanager;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class MainFrame extends JFrame
{
	public static FTPClient client = new FTPClient();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField pwdPassword;
	private JToggleButton btnLogin;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JSeparator separator;
	private JButton btnRefresh;
	private JTree tree;
	private JTextField txtVersionString;
	private JButton btnSubmit;
	private Document doc;
	private JButton btnSelectGameFile;
	private File gameFile;
	private JLabel lblGameFileName;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
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
		SSLUtils.installCerts();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(380, 480));
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		sl_contentPane.putConstraint(SpringLayout.NORTH, tabbedPane, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, tabbedPane, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, tabbedPane, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, tabbedPane, -10, SpringLayout.EAST, contentPane);
		contentPane.add(tabbedPane);

		JPanel panelBrowse = new JPanel();
		tabbedPane.addTab("Browse", null, panelBrowse, null);

		JPanel panelAdd = new JPanel();
		tabbedPane.addTab("Add", null, panelAdd, null);
		SpringLayout sl_panelAdd = new SpringLayout();
		panelAdd.setLayout(sl_panelAdd);

		JLabel lblVersionString = new JLabel("Version string:");
		sl_panelAdd.putConstraint(SpringLayout.NORTH, lblVersionString, 10, SpringLayout.NORTH, panelAdd);
		sl_panelAdd.putConstraint(SpringLayout.WEST, lblVersionString, 10, SpringLayout.WEST, panelAdd);
		panelAdd.add(lblVersionString);

		JLabel lblChanges = new JLabel("Changes:");
		sl_panelAdd.putConstraint(SpringLayout.WEST, lblChanges, 0, SpringLayout.WEST, lblVersionString);
		panelAdd.add(lblChanges);

		JScrollPane scrollPane_1 = new JScrollPane();
		sl_panelAdd.putConstraint(SpringLayout.NORTH, scrollPane_1, 6, SpringLayout.SOUTH, lblChanges);
		sl_panelAdd.putConstraint(SpringLayout.WEST, scrollPane_1, 10, SpringLayout.WEST, panelAdd);
		sl_panelAdd.putConstraint(SpringLayout.EAST, scrollPane_1, -10, SpringLayout.EAST, panelAdd);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelAdd.add(scrollPane_1);

		final JTextArea txtrChanges = new JTextArea();
		txtrChanges.setFont(new Font("Monospaced", txtrChanges.getFont().getStyle(), txtrChanges.getFont().getSize()));
		txtrChanges.setLineWrap(true);
		txtrChanges.setWrapStyleWord(true);
		scrollPane_1.setViewportView(txtrChanges);

		txtVersionString = new JTextField();
		sl_panelAdd.putConstraint(SpringLayout.NORTH, lblChanges, 6, SpringLayout.SOUTH, txtVersionString);
		sl_panelAdd.putConstraint(SpringLayout.NORTH, txtVersionString, 6, SpringLayout.SOUTH, lblVersionString);
		sl_panelAdd.putConstraint(SpringLayout.WEST, txtVersionString, 10, SpringLayout.WEST, panelAdd);
		sl_panelAdd.putConstraint(SpringLayout.SOUTH, txtVersionString, 34, SpringLayout.SOUTH, lblVersionString);
		sl_panelAdd.putConstraint(SpringLayout.EAST, txtVersionString, 319, SpringLayout.WEST, panelAdd);
		panelAdd.add(txtVersionString);
		txtVersionString.setColumns(10);

		btnSubmit = new JButton("Submit");
		sl_panelAdd.putConstraint(SpringLayout.EAST, btnSubmit, -10, SpringLayout.EAST, panelAdd);
		btnSubmit.setEnabled(false);
		sl_panelAdd.putConstraint(SpringLayout.NORTH, btnSubmit, -39, SpringLayout.SOUTH, panelAdd);
		sl_panelAdd.putConstraint(SpringLayout.WEST, btnSubmit, 10, SpringLayout.WEST, panelAdd);
		sl_panelAdd.putConstraint(SpringLayout.SOUTH, btnSubmit, -10, SpringLayout.SOUTH, panelAdd);
		panelAdd.add(btnSubmit);

		lblGameFileName = new JLabel("[No file selected]");
		lblGameFileName.setHorizontalAlignment(SwingConstants.CENTER);
		sl_panelAdd.putConstraint(SpringLayout.WEST, lblGameFileName, 10, SpringLayout.WEST, panelAdd);
		sl_panelAdd.putConstraint(SpringLayout.SOUTH, lblGameFileName, -6, SpringLayout.NORTH, btnSubmit);
		sl_panelAdd.putConstraint(SpringLayout.EAST, lblGameFileName, 319, SpringLayout.WEST, panelAdd);
		panelAdd.add(lblGameFileName);

		btnSelectGameFile = new JButton("Select game file");
		sl_panelAdd.putConstraint(SpringLayout.SOUTH, scrollPane_1, -6, SpringLayout.NORTH, btnSelectGameFile);
		sl_panelAdd.putConstraint(SpringLayout.NORTH, btnSelectGameFile, -35, SpringLayout.NORTH, lblGameFileName);
		sl_panelAdd.putConstraint(SpringLayout.WEST, btnSelectGameFile, 0, SpringLayout.WEST, lblVersionString);
		sl_panelAdd.putConstraint(SpringLayout.SOUTH, btnSelectGameFile, -6, SpringLayout.NORTH, lblGameFileName);
		sl_panelAdd.putConstraint(SpringLayout.EAST, btnSelectGameFile, -10, SpringLayout.EAST, panelAdd);
		panelAdd.add(btnSelectGameFile);

		SpringLayout sl_panelBrowse = new SpringLayout();
		panelBrowse.setLayout(sl_panelBrowse);

		lblUsername = new JLabel("Username");
		sl_panelBrowse.putConstraint(SpringLayout.NORTH, lblUsername, 16, SpringLayout.NORTH, panelBrowse);
		sl_panelBrowse.putConstraint(SpringLayout.WEST, lblUsername, 10, SpringLayout.WEST, panelBrowse);
		panelBrowse.add(lblUsername);

		lblPassword = new JLabel("Password");
		sl_panelBrowse.putConstraint(SpringLayout.NORTH, lblPassword, 20, SpringLayout.SOUTH, lblUsername);
		sl_panelBrowse.putConstraint(SpringLayout.WEST, lblPassword, 10, SpringLayout.WEST, panelBrowse);
		sl_panelBrowse.putConstraint(SpringLayout.EAST, lblPassword, 72, SpringLayout.WEST, panelBrowse);
		panelBrowse.add(lblPassword);

		txtUsername = new JTextField();
		sl_panelBrowse.putConstraint(SpringLayout.NORTH, txtUsername, -6, SpringLayout.NORTH, lblUsername);
		sl_panelBrowse.putConstraint(SpringLayout.EAST, txtUsername, -10, SpringLayout.EAST, panelBrowse);
		panelBrowse.add(txtUsername);
		txtUsername.setColumns(10);

		pwdPassword = new JPasswordField();
		sl_panelBrowse.putConstraint(SpringLayout.WEST, txtUsername, 0, SpringLayout.WEST, pwdPassword);
		sl_panelBrowse.putConstraint(SpringLayout.NORTH, pwdPassword, -6, SpringLayout.NORTH, lblPassword);
		sl_panelBrowse.putConstraint(SpringLayout.WEST, pwdPassword, 6, SpringLayout.EAST, lblPassword);
		sl_panelBrowse.putConstraint(SpringLayout.EAST, pwdPassword, -10, SpringLayout.EAST, panelBrowse);
		panelBrowse.add(pwdPassword);

		btnLogin = new JToggleButton("Login");
		sl_panelBrowse.putConstraint(SpringLayout.NORTH, btnLogin, 4, SpringLayout.SOUTH, pwdPassword);
		sl_panelBrowse.putConstraint(SpringLayout.WEST, btnLogin, 10, SpringLayout.WEST, panelBrowse);
		sl_panelBrowse.putConstraint(SpringLayout.EAST, btnLogin, -10, SpringLayout.EAST, panelBrowse);
		panelBrowse.add(btnLogin);

		separator = new JSeparator();
		sl_panelBrowse.putConstraint(SpringLayout.NORTH, separator, 6, SpringLayout.SOUTH, btnLogin);
		sl_panelBrowse.putConstraint(SpringLayout.WEST, separator, 10, SpringLayout.WEST, panelBrowse);
		sl_panelBrowse.putConstraint(SpringLayout.SOUTH, separator, 22, SpringLayout.SOUTH, btnLogin);
		sl_panelBrowse.putConstraint(SpringLayout.EAST, separator, -10, SpringLayout.EAST, panelBrowse);
		panelBrowse.add(separator);

		btnRefresh = new JButton("Refresh");
		btnRefresh.setEnabled(false);
		sl_panelBrowse.putConstraint(SpringLayout.NORTH, btnRefresh, -39, SpringLayout.SOUTH, panelBrowse);
		sl_panelBrowse.putConstraint(SpringLayout.WEST, btnRefresh, 10, SpringLayout.WEST, panelBrowse);
		sl_panelBrowse.putConstraint(SpringLayout.SOUTH, btnRefresh, -10, SpringLayout.SOUTH, panelBrowse);
		sl_panelBrowse.putConstraint(SpringLayout.EAST, btnRefresh, -10, SpringLayout.EAST, panelBrowse);
		panelBrowse.add(btnRefresh);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sl_panelBrowse.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, separator);
		sl_panelBrowse.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panelBrowse);
		sl_panelBrowse.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, panelBrowse);
		sl_panelBrowse.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, btnRefresh);
		panelBrowse.add(scrollPane);

		tree = new JTree();
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		tree.setRootVisible(true);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("No changelog")));
		scrollPane.setViewportView(tree);

		btnLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (btnLogin.getModel().isSelected())
				{
					btnLogin.setEnabled(false);
					btnLogin.setText("Logging in..");
					txtUsername.setEnabled(false);
					pwdPassword.setEnabled(false);
					new Thread(new Runnable()
					{
						public void run()
						{
							try
							{
								client.connect("37.139.18.125");
								client.login(txtUsername.getText(), new String(pwdPassword.getPassword()));
								btnLogin.setText("Log out");
								btnLogin.setEnabled(true);
								btnRefresh.setEnabled(true);
								btnSubmit.setEnabled(true);

								client.changeDirectory("assets");
								//								for (String s : client.listNames())
								//									System.out.println(s);
								getChangelog();
								updateGUI();
							}
							catch (Exception e1)
							{
								e1.printStackTrace();
								reset();
							}
						}
					}).start();
				}
				else
				{
					btnLogin.setEnabled(false);
					btnLogin.setText("Logging out...");
					new Thread(new Runnable()
					{
						public void run()
						{
							reset();
						}
					}).start();
				}
			}
		});

		btnRefresh.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				getChangelog();
				updateGUI();
			}
		});

		btnSubmit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				addBuild(txtVersionString.getText(), txtrChanges.getText());
				txtVersionString.setText("");
				txtrChanges.setText("");
				updateGUI();
				save();
			}
		});

		btnSelectGameFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				FileDialog fd = new FileDialog(MainFrame.this, "Choose the game file", FileDialog.LOAD);
				fd.setFile("*.jar");
				fd.setVisible(true);
				String filename = fd.getFile();
				if (filename != null)
				{
					gameFile = new File(fd.getDirectory() + "/" + fd.getFile());
					lblGameFileName.setText(fd.getFile().substring(fd.getFile().lastIndexOf('/') + 1));
				}
			}
		});

		txtUsername.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if (ke.getKeyCode() == 10)
					pwdPassword.requestFocus();
			}
		});
		pwdPassword.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if (ke.getKeyCode() == 10)
				{
					btnLogin.requestFocus();
					btnLogin.doClick();
				}
			}
		});
		btnLogin.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if (ke.getKeyCode() == 10)
					btnLogin.doClick();
			}
		});

		KeyListener clear = new KeyAdapter()
		{
			public void keyPressed(KeyEvent ke)
			{
				if (ke.getKeyCode() == 27)
					((JTextComponent) ke.getSource()).setText("");
			}
		};

		txtUsername.addKeyListener(clear);
		pwdPassword.addKeyListener(clear);
		txtrChanges.addKeyListener(clear);
		txtVersionString.addKeyListener(clear);
	}

	private void reset()
	{
		try
		{
			client.disconnect(true);
			tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("No changelog")));
			btnLogin.getModel().setSelected(false);
			btnLogin.setEnabled(true);
			btnLogin.setText("Log in");
			txtUsername.setEnabled(true);
			pwdPassword.setEnabled(true);
			btnSubmit.setEnabled(false);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void getChangelog()
	{
		try
		{
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new URL("https://maceswinger.com/utils/assets/changelog.xml").openStream()));
			doc.getDocumentElement().normalize();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void updateGUI()
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Changelog");
		DefaultTreeModel treeModel = new DefaultTreeModel(root);

		NodeList nList = doc.getElementsByTagName("build");
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement = (Element) nNode;
				Integer buildNumber = Integer.parseInt(eElement.getAttribute("buildNumber"));
				String verString = eElement.getElementsByTagName("buildVersionString").item(0).getTextContent();
				Scanner reader = new Scanner(eElement.getElementsByTagName("changes").item(0).getTextContent());
				ArrayList<String> changes = new ArrayList<String>();
				while (reader.hasNextLine())
				{
					String line = reader.nextLine().trim();
					if (line.length() > 0)
						changes.add(line);
				}
				reader.close();
				DefaultMutableTreeNode node = new DefaultMutableTreeNode("buildNumber[" + buildNumber + "]");
				root.add(node);
				DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("buildVersionString[" + verString + "]");
				node.add(node2);
				DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("changes");
				node.add(node3);
				for (String change : changes)
					node3.add(new DefaultMutableTreeNode(change));
			}
		}
		tree.setRootVisible(false);
		tree.setModel(treeModel);
	}

	public void addBuild(String verString, String changes)
	{
		int remote = Integer.parseInt(doc.getElementsByTagName("remoteBuildNumber").item(0).getTextContent());
		Element newBuild = doc.createElement("build");
		newBuild.setAttribute("buildNumber", "" + ++remote);
		doc.getElementsByTagName("remoteBuildNumber").item(0).setTextContent("" + remote);
		Element builds = (Element) doc.getElementsByTagName("changeLog").item(0);
		builds.appendChild(newBuild);

		Element verStringElement = doc.createElement("buildVersionString");
		verStringElement.setTextContent(verString);
		newBuild.appendChild(verStringElement);
		Element changesElement = doc.createElement("changes");
		changesElement.setTextContent("\n" + changes);
		newBuild.appendChild(changesElement);
	}

	private void save()
	{
		try
		{
			doc.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			final File changelogFile = new File("changelog.xml");
			StreamResult result = new StreamResult(changelogFile);
			transformer.transform(source, result);

			client.upload(changelogFile, new FTPDataTransferListener()
			{
				@Override
				public void transferred(int arg0)
				{
					System.out.println(arg0);
				}

				@Override
				public void started()
				{
					System.out.println("Upload started (xml)");
				}

				@Override
				public void failed()
				{
					System.out.println("Upload failed");
				}

				@Override
				public void completed()
				{
					System.out.println("Upload completed");
					if (gameFile != null)
						try
						{
							client.upload(gameFile, new FTPDataTransferListener()
							{
								@Override
								public void transferred(int arg0)
								{
									System.out.println(arg0);
								}

								@Override
								public void started()
								{
									System.out.println("Upload started (game)");
								}

								@Override
								public void failed()
								{
									System.out.println("Upload failed");
								}

								@Override
								public void completed()
								{
									System.out.println("Upload completed");
									changelogFile.delete();
									getChangelog();
									updateGUI();
								}

								@Override
								public void aborted()
								{
									System.out.println("Upload aborted");
								}
							});
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
				}

				@Override
				public void aborted()
				{
					System.out.println("Upload aborted");
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
