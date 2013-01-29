package com.jverkamp.chesslike;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import javax.swing.*;

import com.jverkamp.chesslike.screen.*;

import trystans.asciiPanel.AsciiPanel;

public class Main extends JFrame implements KeyListener {
	private static final long serialVersionUID = -2378686147116641155L;

	/**
	 * Run from the command line.
	 * @param args Command line parameters.
	 */
	public static void main(String[] args) throws IOException {
		new Main().setVisible(true);
	}
	
	/**
	 * Create a new main frame.
	 */
	public Main() {
		super("ChessLike");
		
		// Set up the terminal.
		Screen.Terminal = new AsciiPanel();
		Screen.Current = new MainMenuScreen();
		
		// Set up the UI
		setLayout(new BorderLayout());
		add(Screen.Terminal);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add the key listener
		addKeyListener(this);
		
		// Force the first repaint.
		repaint();
	}
	
	
	/**
	 * Redraw the screen on repaint.
	 */
	public void repaint() {
		super.repaint();
		Screen.Terminal.clear();
		
		if (Screen.Current != null)
			Screen.Current.update();
    }

	@Override public void keyTyped(KeyEvent e) {}
	
	@Override public void keyPressed(KeyEvent e) {}
	
	@Override public void keyReleased(KeyEvent e) {
		if (Screen.Current != null)
			Screen.Current.doInput(e);
		
		Screen.Terminal.clear();
		
		if (Screen.Current == null)
			System.exit(0);
		else
			Screen.Current.update();
		
		repaint();
	}
}
