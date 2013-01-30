package com.jverkamp.chesslike.world;

import java.awt.Point;

import com.jverkamp.chesslike.actor.Bishop;
import com.jverkamp.chesslike.actor.King;
import com.jverkamp.chesslike.actor.Knight;
import com.jverkamp.chesslike.actor.Pawn;
import com.jverkamp.chesslike.actor.Queen;
import com.jverkamp.chesslike.actor.Rook;

/**
 * A factory that will populate the world with pieces.
 */
public class PiecesFactory {
	/** Hide the constructor. */
	private PiecesFactory() { }
	
	/**
	 * A map of names to their descriptions.
	 */
	public static String[][] Names = new String[][]{
		{"Pawn hunt", "Your King versus eight enemy pawns"},
		{"Full 1v1", "A full set of chess pieces for the player and one opponent"},
		{"Free for all", "Four players each with four pawns"},
	};
	
	/**
	 * Run a factory function.
	 * @param world The world to add the pieces to.
	 * @param name The name of function to use.
	 */
	public static void run(World world, String name) {
		// You get a king, the enemy gets eight enemy pawns
		if (name.equals("Pawn hunt")) {
			world.Actors.add(new King(world, 0));
			for (int i = 0; i < 8; i++)
				world.Actors.add(new Pawn(world, 1));
		}
		
		// Add a full set of chess pieces for the player and a single enemy.
		else if (name.equals("Full 1v1")) {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 8; j++) world.Actors.add(new Pawn(world, i));
				for (int j = 0; j < 2; j++) {
					world.Actors.add(new Bishop(world, i));
					world.Actors.add(new Knight(world, i));
					world.Actors.add(new Rook(world, i));
				}
				world.Actors.add(new Queen(world, i));
				world.Actors.add(new King(world, i));
			}
		}
		
		// Four players each with four pawns
		else if (name.equals("Free for all")) {
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++)
					world.Actors.add(new Pawn(world, i));
		}
		
		// Unknown type
		else {
			throw new IllegalArgumentException("Unknown type name");
		}
		
		// Fix the active actor if it hasn't been chosen.
		world.ActiveActor = world.Actors.get(0);
		world.CurrentMove = new Point(world.ActiveActor.Location.x, world.ActiveActor.Location.y);
	}
}
