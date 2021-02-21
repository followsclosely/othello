package io.github.followsclosley.reverse.impl;

import io.github.followsclosley.reverse.Board;

public class ImmutableBoard extends AbstractBoard implements Board {

    public ImmutableBoard(Board board) {
        this.turn = board.getTurn();
        this.state = new int[this.width = board.getWidth()][this.height = board.getHeight()];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.state[x][y] = board.getPiece(x, y);
            }
        }
    }
}