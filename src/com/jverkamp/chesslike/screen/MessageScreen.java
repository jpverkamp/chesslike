package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;

import trystans.asciiPanel.AsciiPanel;

/**
 * Display a modal message dialog.
 */
public class MessageScreen extends Screen {
	Screen Original;
	String Message;
	
	/**
	 * Create the screen.
	 * @param message The message to display.
	 */
	public MessageScreen(Screen original, String message) {
		Original = original;
		Message = message;
	}
	
	/**
	 * On enter, remove this screen and return to the one below.
	 * @param event Keyboard parameters
	 * @return Either the original screen or this one
	 */
	@Override
	protected Screen input(KeyEvent event) {
		return Original;
	}
	
	/**
	 * Display the dialog.
	 * @param terminal The terminal to draw to.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		String[] lines = (Message + "\n\nPress any key to continue").split("\n");
		for (int i = 0; i < lines.length; i++)
			terminal.writeCenter(lines[i], terminal.getHeightInCharacters() / 2 - lines.length / 2 + i);
	}
}
