package io.github.followsclosley.reverse;

import io.github.followsclosley.reverse.impl.ai.Dummy;

/**
 * This interface is all you need to create your own AI.
 *
 * @see Dummy
 */
public interface ArtificialIntelligence {

    /**
     * Gets the color that the AI is playing for.
     *
     * @return color of the AI player
     */
    public int getColor();

    /**
     * This method is called by the Engine when it is "your" turn to play.
     * It should return the column to drop the piece down.
     *
     * @param board The current state of the game.
     * @return The column (x) to drop the piece.
     */
    abstract public Coordinate yourTurn(Board board);
}
