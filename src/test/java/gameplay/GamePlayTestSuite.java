package gameplay;

import gameplay.order.OrderTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    OrderTestSuite.class,
    GameEngineTest.class,
    GameStartUpPhaseTest.class,
    ReinforcementPhaseTest.class,
    IssueOrderPhaseTest.class,
    ExecuteOrderPhaseTest.class
})

/**
 *  Groups together the test classes for different phase of the game.
 */
public class GamePlayTestSuite {
}
