package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;

import trystans.asciiPanel.AsciiPanel;

/**
 * Represent layered screens.
 */
public abstract class Screen {
	Screen Parent;
	boolean Recur;
	
	public static AsciiPanel Terminal;
	public static Screen Current;
	
	/**
	 * Create a new screen.
	 */
	public Screen() {
		this(false);
	}
	
	/**
	 * Create a new nested screen.
	 * @param recur Recur to parent screen. 
	 */
	public Screen(boolean recur) {
		Recur = recur;
	}
	
	/**
	 * Respond to user input
	 * @param The event to respond to.
	 */
	public final void doInput(KeyEvent event) {
		System.out.println("input " + "(" + event.getKeyCode() + ")" + ": " + getClass().getSimpleName());
		
		if (Recur && Parent != null)
			Parent.doInput(event);
		
		input(event);
	}
	
	/**
	 * Update this screen.
	 */
	public final void doUpdate() {
		System.out.println("update: " + getClass().getSimpleName());
		
		if (Recur && Parent != null)
			Parent.doUpdate();
		
		update();
	}
	
	/**
	 * Add a new screen to the current stack.
	 * @param newScreen The new screen to add.
	 */
	public final static void push(Screen newScreen) {
		newScreen.Parent = Current;
		Current = newScreen;
		Terminal.clear();
	}
	
	/**
	 * Remove the current screen.
	 */
	public final static void pop() {
		Current = Current.Parent;
		Terminal.clear();
	}
	
	
	/**
	 * Respond to user input
	 * @param The event to respond to.
	 */
	protected abstract void input(KeyEvent event);
	
	/**
	 * Update this screen.
	 */
	protected abstract void update();
}
