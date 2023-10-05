import gameplay.GamePlayTestSuite;
import gameutils.GameUtilsTestSuite;
import mapparser.MapParserTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    GamePlayTestSuite.class,
    GameUtilsTestSuite.class,
    MapParserTestSuite.class
})

/**
 * Test group for entire game project.
 */
public class RiskGameTestSuite {
}
