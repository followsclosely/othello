package io.github.followsclosley.reverse.impl.ai;

import io.github.followsclosley.reverse.ArtificialIntelligence;
import io.github.followsclosley.reverse.Board;
import io.github.followsclosley.reverse.Coordinate;
import io.github.followsclosley.reverse.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This AI will pick the move that flips the least (or most) pieces.
 */
public class Dummy implements ArtificialIntelligence {

    /**
     * Determines the mode for this Dummy AI:
     * <ul>
     *     <li><b>Integer.MIN_VALUE:</b> Will return the move that flips the fewest tiles.</li>
     *     <li><b>Integer.MAN_VALUE:</b> Will return the move that flips the most tiles.</li>
     * </ul>
     */
    private int DEFAULT_MODE = Integer.MIN_VALUE;

    private Random random = new Random();

    @Override
    public Coordinate yourTurn(Board board) {

        int maxFlips = (DEFAULT_MODE == Integer.MAX_VALUE) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        List<Coordinate> flips = new ArrayList<>();

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {

                if (board.getPiece(x, y) == 0) {
                    Turn context = board.getTurnContext(new Coordinate(x, y));
                    int counts = context.getFlips().size();

                    if ((DEFAULT_MODE == Integer.MAX_VALUE && (counts > 0 && counts >= maxFlips))
                            || (DEFAULT_MODE == Integer.MIN_VALUE && (counts > 0 && counts <= maxFlips))) {
                        maxFlips = counts;
                        flips.add(context.getMove());
                    }
                }
            }
        }

        return flips.isEmpty() ? null : flips.get(random.nextInt(flips.size()));
    }

    public Dummy setMode(Integer mode) {
        DEFAULT_MODE = mode;
        return this;
    }
}