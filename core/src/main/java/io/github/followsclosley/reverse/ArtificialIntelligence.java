package io.github.followsclosley.reverse;

import io.github.followsclosley.reverse.impl.ai.Dummy;

/**
 * This interface is all you need to create your own AI.
 *
 * @see Dummy
 */
public interface ArtificialIntelligence {

    /**
     * This method is called by the Engine when it is "your" turn to play.
     * It should return the column to drop the piece down.
     *
     * @param board The current state of the game.
     * @return The column (x) to drop the piece.
     */
    Coordinate yourTurn(Board board);
}
