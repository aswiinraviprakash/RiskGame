package gameplay;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    GameStartUpPhaseTest.class,
    ReinforcementPhaseTest.class,
    IssueOrderPhaseTest.class
})

public class GamePlayTestSuite {
}
