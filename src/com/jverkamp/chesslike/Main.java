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
	
	AsciiPanel Terminal;
	Screen BaseScreen;
	
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
		Terminal = new AsciiPanel();
		BaseScreen = new MainMenuScreen();
		
		// Set up the UI
		setLayout(new BorderLayout());
		add(Terminal);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 200);
		
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
		Terminal.clear();
		
		if (BaseScreen == null)
			System.exit(0);
		else
			BaseScreen.doDraw(Terminal);
    }

	@Override public void keyTyped(KeyEvent e) {}
	
	@Override public void keyPressed(KeyEvent e) {}
	
	@Override public void keyReleased(KeyEvent e) {
		if (BaseScreen != null)
			BaseScreen = BaseScreen.doInput(e);
		
		repaint();
	}
}
