package io.github.followsclosley.reverse;

import io.github.followsclosley.reverse.impl.ImmutableBoard;
import io.github.followsclosley.reverse.impl.MutableBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class runs a game using ArtificialIntelligence to play the pieces.
 */
public class Engine {

    // The number of players.
    int playerCount;

    // A List of players.
    private List<ArtificialIntelligence> players = new ArrayList<>();

    /**
     * Constructs and new Engine with a default board.
     *
     * @param ais The players in the game. Can be 2-N.
     */
    public Engine(ArtificialIntelligence... ais) {
        for (ArtificialIntelligence ai : ais) {
            players.add(ai);
        }
        playerCount = players.size();
    }

    /**
     * Runs a simulation of one game.
     */
    public int startGame(int firstIndex) {

        MutableBoard board = new MutableBoard(8, 8);
        int winner = 0;
        int total = board.getWidth() * board.getHeight();
        //System.out.println(board);

        //The total number of turns before the board is full, ignore passes for now.
        for (int piecesPlayed = 0, turn = firstIndex; piecesPlayed < total; turn++, piecesPlayed++) {
            ArtificialIntelligence player = players.get(turn % playerCount);

            Coordinate coordinate = player.yourTurn(new ImmutableBoard(board));
            int flip = playPiece(board, coordinate);
        }

        //System.out.println(board);

        Map<Integer, AtomicInteger> counts = new HashMap<Integer, AtomicInteger>() {
            @Override
            public AtomicInteger get(Object key) {
                AtomicInteger value = super.get(key);
                if (value == null) {
                    super.put((Integer) key, value = new AtomicInteger(0));
                }
                return value;
            }
        };

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                counts.get(board.getPiece(x, y)).incrementAndGet();
            }
        }

        //Tie goes to -1 for now.
        return (counts.get(-1).get() > counts.get(1).get()) ? -1 : 1;
    }

    private int playPiece(MutableBoard board, Coordinate coordinate) {
        int flips = 0;

        if (coordinate != null) {
            Turn context = board.getTurnContext(coordinate);
            if (context.getFlips().size() > 0) {
                board.setPiece(coordinate, board.getTurn());
                for (Coordinate c : context.getFlips()) {
                    board.setPiece(c, board.getTurn());
                }
                flips = context.getFlips().size();
            }
        }

        board.incrementTurn();
        return flips;
    }
}