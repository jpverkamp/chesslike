package com.jverkamp.chesslike;

import java.util.prefs.Preferences;

/**
 * Record statistics.
 */
public class Statistics {
	static Preferences Pref;
	static {
		try {
			Pref = Preferences.userNodeForPackage(Statistics.class); 
		} catch(Exception e) {
		}
	}
	
	private Statistics() {}
	
	/**
	 * Increment a given key (start at 0).
	 * @param key The key.
	 */
	private static void increment(String key) {
		try {
			Pref.putInt("Stats/Game/" + key, 1 + Pref.getInt("Stats/Game/" + key, 0));
			Pref.putInt("Stats/Overall/" + key, 1 + Pref.getInt("Stats/Game/" + key, 0));
			Pref.flush();
		} catch(Exception e) {
		}
	}
	
	/**
	 * Lookup statistics
	 * @param key The key to lookup
	 * @return The statistics
	 */
	private static int[] lookup(String key) {
		try {
			return new int[]{
				Pref.getInt("Stats/Game/" + key, 0),
				Pref.getInt("Stats/Best/" + key, 0),
				Pref.getInt("Stats/Overall/" + key, 0),
			};
		} catch(Exception e) {
			return new int[]{0, 0, 0};
		}
	}
	
	/**
	 * Updated current game stats.
	 */
	public static void newGame() {
		// Potentially update the best scores
		// Reset this game's stats to zero
		try {
			for (String key : Pref.keys()) {
				if (key.startsWith("Stats/Game/")) {
					Pref.putInt(key, 0);
				}
			}
		} catch(Exception ex) {
		}
		
		try { Pref.flush(); } catch (Exception e) {}
	}
	
	
	/**
	 * Record how many times we've won/lost the game.
	 * @param won If the player has won the game.
	 */
	public static void recordGameOver(boolean won) {
		// Increment scores
		if (won)
			increment("GameOver/Win");
		else
			increment("GameOver/Loss");
		
		// Potentially update the best scores
		// Reset this game's stats to zero
		try {
			for (String key : Pref.keys()) {
				if (key.startsWith("Stats/Game/")) {
					String bestKey = key.replace("Stats/Game/", "Stats/Best/");
					Pref.putInt(bestKey, Math.max(Pref.getInt(key, 0), Pref.getInt(bestKey, 0)));
					Pref.putInt(key, 0);
				}
			}
		} catch(Exception ex) {
		}
		
		try { Pref.flush(); } catch (Exception e) {}
	}
	
	/**
	 * Get win games
	 * @return Wins (this game, best, overall)
	 */
	public static int getWins() {
		return lookup("GameOver/Win")[2];
	}
	
	/**
	 * Get lost games
	 * @return Losses (this game, best, overall)
	 */
	public static int getLosses() {
		return lookup("GameOver/Loss")[2];
	}
	
	/**
	 * Record the number of levels played
	 * @param name The name of the level
	 */
	public static void recordLevel(String name) {
		increment("Levels/" + name);
	}
	
	/**
	 * Get the number of levels played.
	 * @param name The name of the level to look up
	 * @return Level count
	 */
	public static int getLevels(String name) {
		return lookup("Levels/" + name)[2];
	}
	
	/**
	 * Record the number of rounds played
	 */
	public static void recordRound() {
		increment("Rounds");
	}
	
	/**
	 * Get the number of rounds played.
	 * @return Round count (this game, best, overall)
	 */
	public static int[] getRounds() {
		return lookup("Rounds");
	}
	
	/**
	 * Record a piece captured
	 * @param name The name of piece getting captured
	 * @param player If the piece was owned by the player
	 */
	public static void recordCaptured(String name, boolean player) {
		if (player)
			increment("Captured/Player/" + name);
		else
			increment("Captured/Computer/" + name);
	}
	
	/**
	 * Get the count for pieces captured
	 * @param name The name of piece getting captured
	 * @param player If the piece was owned by the player
	 * @return How many we've captured (this game, best, overall)
	 */
	public static int[] getCaptured(String name, boolean player) {
		if (player)
			return lookup("Captured/Player/" + name);
		else
			return lookup("Captured/Computer/" + name);
	}

	/**
	 * Check if the preferences loaded successfully.
	 * @return If it loaded
	 */
	public static boolean isLoaded() {
		return Pref != null;
	}
}
