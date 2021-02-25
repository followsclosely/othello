package io.github.followsclosley.reverse.ai;

import io.github.followsclosley.reverse.ArtificialIntelligence;
import io.github.followsclosley.reverse.Board;
import io.github.followsclosley.reverse.Coordinate;
import io.github.followsclosley.reverse.impl.ReverseUtils;

import java.util.Random;

/**
 * A very simple score base strategy.
 */
public class ScoreStrategy implements ArtificialIntelligence {

    private Random random = new Random();
    private int[][] scores;

    public synchronized void init(Board board){
        if( scores == null){
            scores = new int[board.getWidth()][board.getHeight()];

            int template[][] = {
                    { 100, -100, 10,  8},
                    {-100, -100, -5, -3},
                    {  10,   -5, 10,  0},
                    {   8,   -3,  0,  0}
            };

            for(int x=0, width = board.getWidth()-1, tWidth = template.length; x<tWidth; x++){
                for(int y=0, height = board.getHeight()-1, tHeight = template[0].length; y<tHeight; y++){
                    // copy to top left
                    scores[x][y] = template[x][y];
                    // copy to top right
                    scores[width-x][y] = template[x][y];
                    // copy to bottom left
                    scores[x][height-y] = template[x][y];
                    //copy to bottom right
                    scores[width-x][height-y] = template[x][y];
                }
            }
        }
    }

    @Override
    public Coordinate yourTurn(Board board) {
        init(board);

        int[][] myScores = new int[board.getWidth()][board.getHeight()];
        ReverseUtils.TurnContext[][] flips = new ReverseUtils.TurnContext[board.getWidth()][board.getHeight()];

        for(int x=0, width = board.getWidth(); x<width; x++) {
            for (int y = 0, height = board.getHeight(); y < height; y++) {

                if( x == 2 && y == 5){
                    System.out.println("Crap");
                }
                flips[x][y] = ReverseUtils.getTurnContext(board, new Coordinate(x,y));
                myScores[x][y] = scores[x][y] - flips[x][y].getFlips().size();
            }
        }

        int max = Integer.MIN_VALUE;
        Coordinate maxCoordinate = null;

        for(int x=0, width = board.getWidth(); x<width; x++) {
            for (int y = 0, height = board.getHeight(); y < height; y++) {
                if( flips[x][y].getFlips().size() > 0) {
                    if (scores[x][y] > max) {
                        max = scores[x][y];
                        maxCoordinate = flips[x][y].getMove();
                    }

                }
            }
        }

//        for(int x=0, width = board.getWidth(); x<width; x++) {
//            for (int y = 0, height = board.getHeight(); y < height; y++) {
//                System.out.print("\t" + scores[x][y]);
//            }
//            System.out.println();
//        }
//        System.out.println();System.out.println();

        return maxCoordinate;
    }
}