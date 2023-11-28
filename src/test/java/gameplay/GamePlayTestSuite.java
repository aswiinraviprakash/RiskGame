package gameplay;

import gameplay.order.OrderTestSuite;

import gameplay.strategy.StrategyTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    OrderTestSuite.class,
    StrategyTestSuite.class,
    GameEngineTest.class,
    GameStartUpPhaseTest.class,
    ReinforcementPhaseTest.class,
    IssueOrderPhaseTest.class,
    ExecuteOrderPhaseTest.class,
    SaveLoadGameTest.class
})

/**
 *  Groups together the test classes for different phase of the game.
 */
public class GamePlayTestSuite {
}
