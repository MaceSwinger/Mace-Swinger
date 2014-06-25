package com.moomoohk.MaceSwingerLauncher.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 *	@since Dec 25, 2013
 */
public class Test
{
	public static void main(String[] args)
	{
		JFrame f = new JFrame("ey bb");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(200, 500);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.getContentPane().setLayout(null);
		JTextArea l = new JTextArea(args.length + " args \nIf you're seeing this, it worked. In the future this will be the game not some crappy window.\nThis file was downloaded from the server and then executed by the launcher. Cool huh?\n\n<3 u -moomoohk\n\n(thanxs fur testin)\n\n\n\n\n\n\n\nIs this enough room pd");
		l.setWrapStyleWord(true);
		l.setLineWrap(true);
		l.setEditable(false);
		l.setBounds(0, 0, 200, 500);
		f.getContentPane().add(l, BorderLayout.CENTER);
		f.setVisible(true);
	}
}
