package io.github.followsclosley.reverse.swing;

import io.github.followsclosley.reverse.ArtificialIntelligence;
import io.github.followsclosley.reverse.Coordinate;
import io.github.followsclosley.reverse.impl.MutableBoard;
import io.github.followsclosley.reverse.impl.ReverseUtils;
import io.github.followsclosley.reverse.impl.ai.Dummy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class uses a builder patter to launch a swing UI to
 * test your AI.
 *
 * @see
 */
public class SwingSupport {

    private ArtificialIntelligence bot;

    private MutableBoard board;
    private BoardPanel boardPanel;

    public static void main(String[] args) {
        new SwingSupport()
                .setArtificialIntelligence(new Dummy())
                .run();
    }

    /**
     * You can pass in your own board. This allows you to:
     * <ol>
     *     <li>Set up the state of the board</li>
     *     <li>To configure the size of the board</li>
     * </ol>
     *
     * @param board
     * @return this to keep the builder going
     */
    public SwingSupport setBoard(MutableBoard board) {
        this.board = board;
        return this;
    }

    public SwingSupport setArtificialIntelligence(ArtificialIntelligence bot) {
        this.bot = bot;
        return this;
    }

    /**
     * Launches the JFrame that contains the BoardPanel to display the game.
     */
    public void run() {
        if (board == null) {
            board = new MutableBoard(8, 8);
            int centerX = board.getWidth() / 2 - 1;
            int centerY = board.getHeight() / 2 - 1;

            board.setPiece(new Coordinate(centerX, centerY), -1);
            board.setPiece(new Coordinate(centerX + 1, centerY + 1), -1);
            board.setPiece(new Coordinate(centerX, centerY + 1), 1);
            board.setPiece(new Coordinate(centerX + 1, centerY), 1);
        }

        this.boardPanel = new BoardPanel(board);
        this.boardPanel.addMouseMotionListener(boardPanel);

        //Register a listener to capture when a piece is to be played.
        this.boardPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                int x = e.getX() / 50;
                int y = e.getY() / 50;

                Coordinate coordinate = new Coordinate(x, y);
                ReverseUtils.TurnContext context = ReverseUtils.canMove(board, coordinate, board.getTurn());
                if (context.getFlips().size() > 0) {

                    SwingUtilities.invokeLater(() -> playPiece(coordinate));

                    //The bots turn...
                    new Thread(() -> {
                        try { Thread.sleep(500); } catch (InterruptedException ignore) { }
                        SwingUtilities.invokeLater(() -> playPiece(bot.yourTurn(board)));
                    }).start();
                }
            }
        });

        JPanel statusPanel = new JPanel(new BorderLayout());
        JTextField status = new JTextField("...");
        status.setEditable(false);
        statusPanel.add(status, BorderLayout.CENTER);
        JButton undo = new JButton("<");
        undo.addActionListener((ActionEvent e) -> SwingUtilities.invokeLater(() -> {
            MutableBoard previous = board.getPrevious();
            if (previous != null) {
                boardPanel.setBoard(board = board.getPrevious());
                boardPanel.repaint();
            }
        }));
        statusPanel.add(undo, BorderLayout.EAST);

        JFrame frame = new JFrame("Connect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private void playPiece(Coordinate coordinate) {
        if (coordinate != null) {
            ReverseUtils.TurnContext context = ReverseUtils.canMove(board, coordinate, board.getTurn());
            if (context.getFlips().size() > 0) {
                //This craziness is here to support undo.
                boardPanel.setBoard(board = board.backup());
                board.setPiece(coordinate, board.getTurn());
                for (Coordinate c : context.getFlips()) {
                    board.setPiece(c, board.getTurn());
                }
                board.incrementTurn();
            }
        } else {
            board.incrementTurn();
        }
    }
}