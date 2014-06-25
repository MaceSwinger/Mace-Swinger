package com.moomoohk.MaceSwingerLauncher.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;

import com.moomoohk.MaceSwingerLauncher.utils.Resources;
import com.moomoohk.MaceSwingerLauncher.utils.LauncherUtils;

/**
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @since Dec 25, 2013
 */
public class ResponseDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnClose;

	public ResponseDialog(Component parent, String title, String error)
	{
		super((JFrame) parent);
		setSize(400, 200);
		setResizable(false);
		setUndecorated(true);
		setModal(true);
		setLocationRelativeTo(parent);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(Resources.background.brighter());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblTheServerHas = new JLabel(title);
		lblTheServerHas.setBounds(10, 10, 380, 16);
		lblTheServerHas.setForeground(Resources.foreground);
		lblTheServerHas.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 14));
		contentPanel.add(lblTheServerHas);

		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				dispose();
			}
		});
		btnClose.setFont(new Font(Resources.PTSans.getName(), Font.PLAIN, 14));
		getRootPane().setDefaultButton(btnClose);
		btnClose.setBounds(10, 160, 380, 30);
		contentPanel.add(btnClose);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createLineBorder(Resources.foreground));
		scrollPane.setBounds(10, 36, 380, 114);
		contentPanel.add(scrollPane);

		JEditorPane dtrpnResponse = new JEditorPane("text/html", LauncherUtils.htmlify(error));
		((HTMLDocument) dtrpnResponse.getDocument()).getStyleSheet().addRule("a{color:#" + Integer.toHexString(new Color(132, 170, 187).brighter().getRGB()).substring(2) + "}");
		dtrpnResponse.addHyperlinkListener(Resources.hyperlinkListener);
		dtrpnResponse.putClientProperty("Nimbus.Overrides", Resources.defaults);
		dtrpnResponse.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		dtrpnResponse.setBackground(Resources.background.brighter().brighter());
		dtrpnResponse.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Resources.foreground), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
		dtrpnResponse.setForeground(new Color(132, 170, 187));
		dtrpnResponse.setFont(new Font(Resources.PTSans.getName(), Font.BOLD, 14));
		dtrpnResponse.setEditable(false);
		scrollPane.setViewportView(dtrpnResponse);
	}
}
