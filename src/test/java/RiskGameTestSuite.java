import gameplay.GamePlayTestSuite;
import gameutils.GameUtilsTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    GamePlayTestSuite.class,
    GameUtilsTestSuite.class
})

public class RiskGameTestSuite {
}
