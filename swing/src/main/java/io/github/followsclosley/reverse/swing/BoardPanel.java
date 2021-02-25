package io.github.followsclosley.reverse.swing;


import io.github.followsclosley.reverse.Board;
import io.github.followsclosley.reverse.Coordinate;
import io.github.followsclosley.reverse.impl.BoardChangedListener;
import io.github.followsclosley.reverse.impl.MutableBoard;
import io.github.followsclosley.reverse.impl.ReverseUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * This panel draws the connect four board given a Board
 *
 * @see Board
 */
public class BoardPanel extends JPanel implements BoardChangedListener, MouseMotionListener {

    private int lastx = -1, lasty = -1;
    private ReverseUtils.TurnContext flips = null;

    private Dimension defaultDimension;
    private Color DEFAULR_BOARD_COLOR = Color.GREEN.darker().darker().darker();

    private Color[] COLORS = {Color.WHITE, Color.GRAY, Color.BLACK};
    private Color[] COLORS_ALPHA = {
            new Color(COLORS[0].getRed(), COLORS[0].getGreen(), COLORS[0].getBlue(), 100),
            Color.GRAY,
            new Color(COLORS[2].getRed(), COLORS[2].getGreen(), COLORS[2].getBlue(), 100)
    };

    private MutableBoard board;

    public BoardPanel(MutableBoard board) {
        this.board = board;
        this.setBackground(Color.DARK_GRAY);
        this.defaultDimension = new Dimension(board.getWidth() * 50 - 5, board.getHeight() * 50 - 5);
    }

    public void setBoard(MutableBoard board) {
        if (this.board != null) {
            this.board.setBoardChangedListener(null);
        }
        this.board = board;
        this.board.setBoardChangedListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0, width = board.getWidth(); x < width; x++) {
            for (int y = 0, height = board.getHeight(); y < height; y++) {

                g.setColor(DEFAULR_BOARD_COLOR);
                g.fillRoundRect(x * 50, y * 50, 45, 45, 5, 5);

                int color = board.getPiece(x, y);
                if (color != 0) {
                    g.setColor(COLORS[color + 1]);
                    g.fillRoundRect(x * 50 + 3, y * 50 + 3, 39, 39, 39, 39);

                    //g.setColor(Color.RED);
                    //g.drawString(String.valueOf(color), x * 50 + 15, y * 50 + 25);
                }
            }
        }

        if (flips != null) {//&& flips.getFlips().size() > 0) {
            g.setColor(COLORS_ALPHA[board.getTurn() + 1]);
            for (Coordinate c : flips.getFlips()) {
                g.fillRoundRect(c.getX() * 50 + 3, c.getY() * 50 + 3, 39, 39, 39, 39);
            }

            g.setColor((flips.getFlips().size() > 0) ? COLORS[board.getTurn() + 1] : COLORS_ALPHA[board.getTurn() + 1]);
            g.fillRoundRect(flips.getMove().getX() * 50 + 3, flips.getMove().getY() * 50 + 3, 39, 39, 39, 39);
        }
    }

    @Override
    public void boardChanged(Coordinate coordinate) {
        this.flips = null;
        SwingUtilities.invokeLater(() -> repaint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX() / 50;
        int y = e.getY() / 50;

        if (x != lastx || y != lasty) {
            flips = ( board.getPiece(lastx = x, lasty = y) == 0 ) ? ReverseUtils.getTurnContext(board, new Coordinate(x, y)) : null;
            SwingUtilities.invokeLater(() -> repaint());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) { }
}