package io.github.followsclosley.reverse.impl;

import io.github.followsclosley.reverse.Board;
import io.github.followsclosley.reverse.Coordinate;

public class MutableBoard extends AbstractBoard {

    private MutableBoard previous;
    private BoardChangedListener listener;

    public MutableBoard() {
        this(8, 8);
    }

    public MutableBoard(int width, int height) {
        this.state = new int[this.width = width][this.height = height];
    }

    public MutableBoard(Board board) {
        this.turn = board.getTurn();
        this.state = new int[this.width = board.getWidth()][this.height = board.getHeight()];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.state[x][y] = board.getPiece(x, y);
            }
        }
    }

    /**
     * This method clones the current board and sets the
     * previous. This craziness is here to support undo.
     *
     * @return A cloned board with the previous set.
     */
    public MutableBoard backup() {
        MutableBoard board = new MutableBoard(this);
        board.previous = this;
        return board;
    }

    public void incrementTurn() {
        turn = turn * -1;
    }

    public void setPiece(Coordinate coordinate, int color) {
        state[coordinate.getX()][coordinate.getY()] = color;
        fireBoardChanged(coordinate);
    }

    public MutableBoard getPrevious() {
        return previous;
    }

    private void fireBoardChanged(Coordinate coordinate) {
        if (listener != null) {
            listener.boardChanged(coordinate);
        }
    }

    public synchronized void setBoardChangedListener(BoardChangedListener listener) {
        this.listener = listener;
    }
}