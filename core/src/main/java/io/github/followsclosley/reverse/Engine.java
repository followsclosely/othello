package io.github.followsclosley.reverse;

import io.github.followsclosley.reverse.impl.MutableBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class runs a game using ArtificialIntelligence to play the pieces.
 */
public class Engine {

    // The number of players. This is set in the constuctor.
    int playerCount;

    // A List of players.
    private List<ArtificialIntelligence> players = new ArrayList<>();

    // The state of the game is held in the MutableBoard.
    private MutableBoard board = new MutableBoard(8, 8);

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

        int winner = -1;
        //System.out.println(board.toMatrixString());

        //The total number of turns before the board is full
        int total = board.getWidth() * board.getHeight();
        for (int turn = firstIndex; turn < total; turn++) {
            ArtificialIntelligence player = players.get(turn % playerCount);

            //Pass an immutable board down as to not mess with this standard
            //int x = player.yourTurn(new ImmutableBoard(board));

            //int y = board.dropPiece(x, player.getColor());
            //System.out.println(board);
        }

        return winner;
    }
}