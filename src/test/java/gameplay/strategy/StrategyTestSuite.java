package gameplay.strategy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   AggressivePlayerStrategyTest.class,
   BenevolentPlayerStrategyTest.class,
   CheaterPlayerStrategyTest.class,
   RandomPlayerStrategyTest.class
})

/**
 * Test group for strategy.
 */
public class StrategyTestSuite {
}
