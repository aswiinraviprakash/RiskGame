package gameplay;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    GameStartUpPhaseTest.class,
    ReinforcementPhaseTest.class,
    IssueOrderPhaseTest.class
})

/**
 *  Groups together the test classes for different phase of the game.
 */
public class GamePlayTestSuite {
}
