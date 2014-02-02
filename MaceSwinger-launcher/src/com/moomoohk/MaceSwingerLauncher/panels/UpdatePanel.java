package com.moomoohk.MaceSwingerLauncher.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import aurelienribon.slidinglayout.SLKeyframe.Callback;

import com.moomoohk.MaceSwingerLauncher.MainFrame;
import com.moomoohk.MaceSwingerLauncher.MainFrame.View;
import com.moomoohk.MaceSwingerLauncher.utils.LauncherUtils;
import com.moomoohk.MaceSwingerLauncher.utils.Resources;
import com.moomoohk.Mootilities.ExceptionHandling.ExceptionDisplayDialog;

public class UpdatePanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JEditorPane dtrpnResponse;

	public UpdatePanel()
	{
		super();
		setBorder(BorderFactory.createLineBorder(Resources.foreground));
		setBackground(Resources.background.brighter());
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JLabel lblUpdateAvailable = new JLabel("A new update is available!");
		springLayout.putConstraint(SpringLayout.SOUTH, lblUpdateAvailable, 57, SpringLayout.NORTH, this);
		lblUpdateAvailable.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 20));
		lblUpdateAvailable.setForeground(Resources.foreground);
		lblUpdateAvailable.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.NORTH, lblUpdateAvailable, 13, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblUpdateAvailable, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblUpdateAvailable, -10, SpringLayout.EAST, this);
		add(lblUpdateAvailable);

		JLabel lblDownloadNow = new JLabel("Would you like to download it now?");
		springLayout.putConstraint(SpringLayout.WEST, lblDownloadNow, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblDownloadNow, -11, SpringLayout.EAST, this);
		lblDownloadNow.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 16));
		lblDownloadNow.setForeground(Resources.foreground);
		lblDownloadNow.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblDownloadNow);

		JButton btnYes = new JButton("Yes");
		springLayout.putConstraint(SpringLayout.SOUTH, lblDownloadNow, -6, SpringLayout.NORTH, btnYes);
		btnYes.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.WEST, btnYes, -100, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.EAST, btnYes, -10, SpringLayout.EAST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnYes, -10, SpringLayout.SOUTH, this);
		add(btnYes);

		JButton btnNo = new JButton("No");
		btnNo.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.WEST, btnNo, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, btnNo, 100, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, btnNo, -10, SpringLayout.SOUTH, this);
		add(btnNo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(5);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 6, SpringLayout.SOUTH, lblUpdateAvailable);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -95, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.NORTH, lblDownloadNow, 6, SpringLayout.SOUTH, scrollPane);
		scrollPane.setBorder(BorderFactory.createLineBorder(Resources.foreground));
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, this);
		add(scrollPane);

		dtrpnResponse = new JEditorPane("text/html", null);
		((HTMLDocument) dtrpnResponse.getDocument()).getStyleSheet().addRule("a{color:#" + Integer.toHexString(new Color(132, 170, 187).brighter().getRGB()).substring(2) + "}");
		dtrpnResponse.addHyperlinkListener(Resources.hyperlinkListener);
		dtrpnResponse.putClientProperty("Nimbus.Overrides", Resources.defaults);
		dtrpnResponse.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		dtrpnResponse.setBackground(Resources.background.brighter().brighter());
		dtrpnResponse.setBorder(new EmptyBorder(0, 5, 0, 5));
		dtrpnResponse.setForeground(new Color(132, 170, 187));
		dtrpnResponse.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 14));
		dtrpnResponse.setEditable(false);
		scrollPane.setViewportView(dtrpnResponse);

		btnYes.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				MainFrame.mainFrame.animateBetween(View.UPDATEAVAILABLE, View.MENU, new Callback()
				{
					public void done()
					{
						LauncherUtils.downloadGame();
					}
				});
			}
		});

		btnNo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				MainFrame.mainFrame.setButtonsEnabled(true);
				MainFrame.mainFrame.menuPanel.btnPlay.setText("Play");
				MainFrame.mainFrame.animateBetween(View.UPDATEAVAILABLE, View.MENU, null);
			}
		});
	}

	public void updateChangelog()
	{
		dtrpnResponse.setText("Loading changes...");
		try
		{
			Document d = LauncherUtils.bootstrap.getChangelog();
			NodeList nList = d.getElementsByTagName("build");
			StringBuilder entries = new StringBuilder();
			for (int temp = nList.getLength() - 1; temp >= 0; temp--)
			{
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					Integer buildNumber = Integer.parseInt(eElement.getAttribute("buildNumber"));
					String verString = eElement.getElementsByTagName("buildVersionString").item(0).getTextContent();
					Scanner reader = new Scanner(eElement.getElementsByTagName("changes").item(0).getTextContent());
					StringBuilder entry = new StringBuilder("v" + verString + " (build " + buildNumber + "):");
					while (reader.hasNextLine())
						entry.append(" " + reader.nextLine().trim() + "\n");
					entries.append(entry.toString() + "\n");
					reader.close();
				}
			}
			dtrpnResponse.setText(LauncherUtils.htmlify(d.getElementsByTagName("headerText").item(0).getTextContent().trim() + "\n\n" + entries.toString().trim()));
			dtrpnResponse.setCaretPosition(0);
		}
		catch (Exception e)
		{
			new ExceptionDisplayDialog(MainFrame.mainFrame, e);
		}
	}
}
