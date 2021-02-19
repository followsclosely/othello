package io.github.followsclosley.reverse.impl.ai;

import io.github.followsclosley.reverse.impl.MutableBoard;
import org.junit.Test;

public class DummyTest {

    @Test
    public void yourTurnHasNoSpacesLeft() {
        Dummy dummy = new Dummy(7);
        MutableBoard board = new MutableBoard(3, 3);

        //Fill the board.
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                //board.dropPiece(x, 1);
            }
        }

        //int x = dummy.yourTurn(board);
        //assertEquals(-1, x);
    }

    @Test
    public void alwaysInBounds() {
        Dummy dummy = new Dummy(7);
        MutableBoard board = new MutableBoard(8, 8);

        //Fill the board.
        for (int i = 0; i < 100; i++) {
            //int x = dummy.yourTurn(board);
            //assertTrue("Value can not be less than zero.", x >= 0);
            //assertTrue("Value can not be greater than ten.", x < 10);
        }
    }
}