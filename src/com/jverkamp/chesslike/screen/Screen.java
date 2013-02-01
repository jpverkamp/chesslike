package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;

import trystans.asciiPanel.AsciiPanel;

/**
 * Represent layered screens.
 */
public abstract class Screen {
	Screen Parent;
	boolean Recur;
	
	/**
	 * Create a new screen.
	 */
	public Screen() {
		this(null, false);
	}
	
	/**
	 * Create a new screen.
	 * @param parent The parent screen to remember.
	 */
	public Screen(Screen parent) {
		this(parent, false);
	}
	
	/**
	 * Create a new nested screen.
	 * @param recur Recur to parent screen. 
	 */
	public Screen(boolean recur) {
		this(null, recur);
	}
	
	/**
	 * Create a new nested screen.
	 * @param parent The parent screen to remember.
	 * @param recur Recur to parent screen. 
	 */
	public Screen(Screen parent, boolean recur) {
		Parent = parent;
		Recur = recur;
	}
	
	/**
	 * Respond to user input
	 * @param The event to respond to.
	 */
	public final Screen doInput(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_F1)
			return new HelpScreen(this);
		else if (event.getKeyCode() == KeyEvent.VK_F2)
			return new StatScreen(this);
			
		if (Recur && Parent != null)
			Parent = Parent.doInput(event);
		
		return input(event);
	}
	
	/**
	 * Draw this screen.
	 */
	public final void doDraw(AsciiPanel terminal) {
		if (Recur && Parent != null)
			Parent.doDraw(terminal);
		
		draw(terminal);
	}
	
	/**
	 * Respond to user input
	 * @param The event to respond to.
	 */
	protected abstract Screen input(KeyEvent event);
	
	/**
	 * Draw this screen.
	 * @param terminal The panel to draw to.
	 */
	protected abstract void draw(AsciiPanel terminal);
}
