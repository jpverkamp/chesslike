package com.jverkamp.chesslike.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.jverkamp.chesslike.world.*;

import trystans.asciiPanel.AsciiPanel;

/**
 * Display the main menu.
 */
public class MainMenuScreen extends Screen {
	// Possible screen states.
	enum Mode {
		FirstMenu,
		ChooseLandscape,
		ChoosePieces
	};
	Mode CurrentMode = Mode.FirstMenu;
	
	// Selected options
	int MenuOption = 0;
	String SelectedLandscape;
	String SelectedPieces;
	
	/**
	 * Draw this screen.
	 * @param terminal The terminal to draw to.
	 */
	@Override
	protected Screen input(KeyEvent event) {
		int code = event.getKeyCode();
		
		if (code == KeyEvent.VK_ENTER) {
			return new WorldIntroScreen(new WorldScreen(null, 1));
		} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			return null;
		}
		
		return this;
	}
	
	/**
	 * Draw the panel
	 * @param terminal The event to respond to.
	 */
	@Override
	protected void draw(AsciiPanel terminal) {
		// Display the first layer of menu.
		if (CurrentMode == Mode.FirstMenu) {
			terminal.writeCenter("ChessLike", 8);
			terminal.writeCenter("Roguelike + Chess!", 9);
			terminal.writeCenter("Press [Enter] to play", 11);
			terminal.writeCenter("Press [Esc] to quit", 12);
		}
		
		// Choose a landscape
		else if (CurrentMode == Mode.ChooseLandscape) {
			int offset = terminal.getHeightInCharacters() / 2 - (LandscapeFactory.Names.length + 6) / 2;
			terminal.writeCenter("Choose a landscape", offset);
			for (int i = 0; i < LandscapeFactory.Names.length; i++)
				terminal.writeCenter(
					LandscapeFactory.Names[i][1], 
					offset + i + 2, 
					MenuOption == i ? Color.BLACK : Color.WHITE, 
					MenuOption == i ? Color.WHITE : Color.BLACK
				);
			terminal.writeCenter("[Up/Down] to change", offset + LandscapeFactory.Names.length + 3);
			terminal.writeCenter("[Enter] to select", offset + LandscapeFactory.Names.length + 4);
			terminal.writeCenter("[Esc] to go back", offset + LandscapeFactory.Names.length + 5);
		}
		
		// Choose pieces
		else if (CurrentMode == Mode.ChoosePieces) {
			int offset = terminal.getHeightInCharacters() / 2 - (PiecesFactory.Names.length + 6) / 2;
			terminal.writeCenter("Choose the kinds of pieces", offset);
			for (int i = 0; i < PiecesFactory.Names.length; i++)
				terminal.writeCenter(
					PiecesFactory.Names[i][1], 
					offset + i + 2, 
					MenuOption == i ? Color.BLACK : Color.WHITE, 
					MenuOption == i ? Color.WHITE : Color.BLACK
				);
			terminal.writeCenter("[Up/Down] to change", offset + PiecesFactory.Names.length + 3);
			terminal.writeCenter("[Enter] to select", offset + PiecesFactory.Names.length + 4);
			terminal.writeCenter("[Esc] to go back", offset + PiecesFactory.Names.length + 5);
		}
	}
}
