package com.jverkamp.chesslike;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class Frame extends JFrame {
	private static final long serialVersionUID = 8016004738680130887L;
	
	/**
	 * Run from the command line.
	 * @param args Command line parameters.
	 */
	public static void main(String[] args) throws IOException {
		new Frame().setVisible(true);
	}
	
	/**
	 * Create a new main frame.
	 */
	public Frame() {
		setTitle("ChessLike");
		setLayout(new BorderLayout());
		add(new MainPanel());
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
