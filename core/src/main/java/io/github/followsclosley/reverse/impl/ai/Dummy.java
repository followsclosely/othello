package io.github.followsclosley.reverse.impl.ai;

import io.github.followsclosley.reverse.ArtificialIntelligence;
import io.github.followsclosley.reverse.Board;
import io.github.followsclosley.reverse.Coordinate;

import java.util.Random;

/**
 * A totally random impl of AI.
 */
public class Dummy implements ArtificialIntelligence {

    private Random random = new Random();

    private int color;

    public Dummy(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public Coordinate yourTurn(Board board) {
        return null;
    }
}