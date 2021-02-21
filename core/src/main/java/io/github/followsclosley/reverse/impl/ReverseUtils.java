package io.github.followsclosley.reverse.impl;

import io.github.followsclosley.reverse.Board;
import io.github.followsclosley.reverse.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class ReverseUtils {

    public static MutableBoard initialize(MutableBoard board){

        int centerX = board.getWidth() / 2 - 1;
        int centerY = board.getHeight() / 2 - 1;

        board.setPiece(new Coordinate(centerX, centerY), -1);
        board.setPiece(new Coordinate(centerX + 1, centerY + 1), -1);
        board.setPiece(new Coordinate(centerX, centerY + 1), 1);
        board.setPiece(new Coordinate(centerX + 1, centerY), 1);

        return board;
    }
    public static TurnContext canMove(Board board, Coordinate coordinate) {

        TurnContext context = new TurnContext(board, coordinate);

        if (board.getPiece(coordinate.getX(), coordinate.getY()) == 0) {
            getFlipCount(context, 1, 0);
            getFlipCount(context, -1, 0);

            getFlipCount(context, 0, 1);
            getFlipCount(context, 0, -1);

            getFlipCount(context, 1, 1);
            getFlipCount(context, -1, -1);

            getFlipCount(context, 1, -1);
            getFlipCount(context, -1, 1);
        }

        return context;
    }

    private static int getFlipCount(TurnContext c, int deltaX, int deltaY) {

        ArrayList<Coordinate> flips = new ArrayList();

        for (int x = c.getMove().getX() + deltaX, y = c.getMove().getY() + deltaY; x < c.getBoard().getWidth() && x >= 0 && y < c.getBoard().getHeight() && y >= 0; x += deltaX, y += deltaY) {

            int color = c.getBoard().getPiece(x, y);

            if (color == 0) {
                break;
            } else if (color == c.getBoard().getTurn()) {
                if (flips.size() > 0) {
                    c.getFlips().addAll(flips);
                }
                break;
            } else if (color != c.getBoard().getTurn()) {
                flips.add(new Coordinate(x, y));
            }
        }

        return flips.size();
    }

    public static class TurnContext {

        private Board board;
        private Coordinate move;
        private List<Coordinate> flips;

        public TurnContext(Board board, Coordinate move) {
            this.board = board;
            this.move = move;
            this.flips = new ArrayList<>();
        }

        public List<Coordinate> getFlips() {
            return flips;
        }

        public Board getBoard() {
            return board;
        }

        public Coordinate getMove() {
            return move;
        }
    }
}