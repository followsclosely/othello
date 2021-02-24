package io.github.followsclosley.reverse.ai;

import io.github.followsclosley.reverse.impl.MutableBoard;
import junit.framework.TestCase;

public class ScoreStrategyTest extends TestCase {
    public void testInit(){
        ScoreStrategy strategy = new ScoreStrategy();
        strategy.init(new MutableBoard());

        System.out.println(strategy);
    }
}