package io.github.followsclosley.reverse;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private Coordinate move;
    private List<Coordinate> flips;

    public Turn(Coordinate move) {
        this.move = move;
        this.flips = new ArrayList<>();
    }


    public List<Coordinate> getFlips() {
        return flips;
    }

    public Coordinate getMove() {
        return move;
    }
}
