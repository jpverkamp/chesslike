package com.jverkamp.chesslike;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import com.jverkamp.chesslike.screen.*;

import trystans.asciiPanel.AsciiPanel;

public class MainPanel extends JPanel implements KeyListener {
	private static final long serialVersionUID = -2378686147116641155L;
	
	boolean Initialized = false;
	AsciiPanel Terminal = new AsciiPanel();
	Screen BaseScreen = new MainMenuScreen();

	/**
	 * Create a new main frame.
	 */
	public MainPanel() {
		// Initialize.
		init();
		
		// Set up the UI
		setFocusable(true);
		setLayout(new BorderLayout());
		add(Terminal);
		
		// Add the key listener
		requestFocus();
		addKeyListener(this);
		
		// Force the first repaint.
		repaint();
	}

	/**
	 * Initialize.
	 * @return
	 */
	public void init() {
		Terminal = new AsciiPanel();
		BaseScreen = new MainMenuScreen();

		Initialized = true;
	}
	
	/**
	 * Redraw the screen on repaint.
	 */
	public void repaint() {
		super.repaint();
		requestFocus();
		
		if (!Initialized) return;
			
		Terminal.clear();
		
		if (BaseScreen == null)
			System.exit(0);
		else
			BaseScreen.doDraw(Terminal);
    }

	@Override public void keyTyped(KeyEvent e) {}
	
	@Override public void keyPressed(KeyEvent e) {}
	
	@Override public void keyReleased(KeyEvent e) {
		if (!Initialized) return;
		
		if (BaseScreen != null)
			BaseScreen = BaseScreen.doInput(e);
		
		repaint();
	}
}
