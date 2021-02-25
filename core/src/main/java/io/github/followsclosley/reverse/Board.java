package io.github.followsclosley.reverse;

public interface Board {
    int getTurn();

    int getWidth();

    int getHeight();

    int getPiece(int x, int y);

    Turn getTurnContext(Coordinate coordinate);
}