package gameplay;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    GameEngineTest.class,
    GameStartUpPhaseTest.class,
    ReinforcementPhaseTest.class,
    IssueOrderPhaseTest.class,
    BombOrderTest.class,
    BlockadeOrderTest.class,
    AirliftOrderTest.class,
    DiplomacyOrderTest.class,
    AdvanceOrderTest.class,
})

/**
 *  Groups together the test classes for different phase of the game.
 */
public class GamePlayTestSuite {
}
