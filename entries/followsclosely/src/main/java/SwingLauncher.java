import io.github.followsclosley.reverse.ai.ScoreStrategy;
import io.github.followsclosley.reverse.impl.MutableBoard;
import io.github.followsclosley.reverse.swing.SwingSupport;

/**
 * Class to launch a Swing interface to test your AI.
 */
public class SwingLauncher {
    public static void main(String[] args) {
        new SwingSupport()
                .setBoard(new MutableBoard(8, 8))
                .setArtificialIntelligence(new ScoreStrategy())
                .run();
    }
}