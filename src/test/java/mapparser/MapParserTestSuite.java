package mapparser;

import gameutils.GameCommandParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test group for the entire mapparser module.
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
    GameMapTest.class,
    EditMapPhaseTest.class
})

public class MapParserTestSuite {
}
