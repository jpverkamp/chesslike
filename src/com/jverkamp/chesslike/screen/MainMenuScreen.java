package com.jverkamp.chesslike.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.jverkamp.chesslike.Statistics;
import com.jverkamp.chesslike.actor.*;

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
			yourHumbleArmy.add(new King(null, 0));
			yourHumbleArmy.add(new Queen(null, 0));
			
			Statistics.newGame();
			
			// Start level
			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, Color.WHITE, 1)); // Forest
//			
//			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, Color.WHITE, 2)); // Caves
//			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, Color.GREEN, 2)); // Underground Lake
//			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, Color.BLUE, 2)); // Underground Forest
//			
//			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, Color.WHITE, 6)); // Dungeon
//			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, new Color(255, 215, 0) /* GOLD */, 6)); // Cathedral
//			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, new Color(183, 65, 14) /* RUST */, 6)); // Foundry
//			
//			return new WorldIntroScreen(new WorldScreen(yourHumbleArmy, Color.WHITE, 10)); // Throne Room

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
		terminal.writeCenter("ChessLike", 8);
		terminal.writeCenter("Roguelike + Chess!", 9);
		
		terminal.writeCenter("Press [Enter] to play", 11);
		terminal.writeCenter("Press [Esc] to quit", 12);
	}
}
