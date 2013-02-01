package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;
import java.util.*;

import trystans.asciiPanel.AsciiPanel;

/**
 * Display help information
 */
public class HelpScreen extends Screen {
	Screen Original;
	String[] HelpText;
	
	/**
	 * Create a new help screen
	 * @param original The screen to return to
	 */
	public HelpScreen(Screen original) {
		Original = original;
		
		Scanner s = new Scanner(HelpScreen.class.getResourceAsStream("Help.txt"));
		List<String> lines = new ArrayList<String>();
		while (s.hasNextLine())
			lines.add(s.nextLine());
		
		HelpText = new String[lines.size()];
		HelpText = lines.toArray(HelpText);
	}
	
	/**
	 * Close on any key.
	 * @param event The event to process.
	 * @return Close this screen.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		return Original;
	}

	/**
	 * Draw the help screen.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		for (int i = 0; i < HelpText.length; i++)
			terminal.write(HelpText[i], 0, i);
	}

}
