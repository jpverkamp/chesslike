package com.jverkamp.chesslike.screen;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.jverkamp.chesslike.actor.Actor;
import com.jverkamp.chesslike.actor.King;

import trystans.asciiPanel.AsciiPanel;

/**
 * Display the main menu.
 */
public class MainMenuScreen extends Screen {
	/**
	 * Draw this screen.
	 * @param terminal The terminal to draw to.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		int code = event.getKeyCode();
		
		if (code == KeyEvent.VK_ENTER) {
			List<Actor> yourHumbleArmy = new ArrayList<Actor>();
			yourHumbleArmy.add(new King(null, 1));
			
			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, null, 1));
		} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			return null;
		} else if (event.getKeyCode() == KeyEvent.VK_F1){
			return new HelpScreen(this);
		}
		
		return this;
	}
	
	/**
	 * Draw the panel
	 * @param terminal The event to respond to.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		terminal.writeCenter("ChessLike", 7);
		terminal.writeCenter("Roguelike + Chess!", 8);
		
		terminal.writeCenter("Press [F1] for help", 10);
		
		terminal.writeCenter("Press [Enter] to play", 12);
		terminal.writeCenter("Press [Esc] to quit", 13);
	}
}
