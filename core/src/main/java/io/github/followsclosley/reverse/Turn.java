package io.github.followsclosley.reverse;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private Board board;
    private Coordinate move;
    private List<Coordinate> flips;

    public Turn(Board board, Coordinate move) {
        this.board = board;
        this.move = move;
        this.flips = new ArrayList<>();
    }

    public Board getBoard() {
        return board;
    }

    public List<Coordinate> getFlips() {
        return flips;
    }

    public Coordinate getMove() {
        return move;
    }
}
