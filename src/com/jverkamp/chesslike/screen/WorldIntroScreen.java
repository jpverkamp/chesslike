package com.jverkamp.chesslike.screen;

public class WorldIntroScreen extends MessageScreen {
	/**
	 * A screen to introduce a world screen.
	 * @param world The world to introduce.
	 */
	public WorldIntroScreen(WorldScreen worldScreen) {
		super(worldScreen, "Level " + worldScreen.CurrentDepth + " - " + worldScreen.World.Title + "\n\n" + worldScreen.World.Description);
	}
}
