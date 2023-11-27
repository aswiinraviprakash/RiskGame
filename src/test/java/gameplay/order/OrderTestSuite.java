package gameplay.order;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        BombOrderTest.class,
        BlockadeOrderTest.class,
        AirliftOrderTest.class,
        DiplomacyOrderTest.class,
        AdvanceOrderTest.class
})

public class OrderTestSuite {
}
