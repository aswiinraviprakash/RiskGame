package gameplay;

import common.Phase;
import java.util.ArrayList;
import java.util.List;

import constants.GameMessageConstants;
import gameutils.GameCommandParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Game Engine
 */
public class GameEngineTest {

    /**
     * Contains all the Phases
     */
    List<Phase> l_phases = new ArrayList<Phase>();

    /**
     * Initializing Data for Tests
     */
    @Before
    public void initialiseTestData() {

        l_phases.add(new GameStartUpPhase());
        l_phases.add(new ReinforcementPhase());
        l_phases.add(new IssueOrderPhase());
        l_phases.add(new ExecuteOrderPhase());
        l_phases.add(new ReinforcementPhase());
        l_phases.add(new IssueOrderPhase());
        l_phases.add(new ExecuteOrderPhase());
    }

    /**
     * Test for game startup phase
     */
    @Test
    public void testGameStartUpPhase() {
        int l_index = 0;
        
        try {
            
        Assert.assertTrue(l_phases.get(l_index) instanceof GameStartUpPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof ReinforcementPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof IssueOrderPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof ExecuteOrderPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof ReinforcementPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof IssueOrderPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof ExecuteOrderPhase);
        } catch (Exception e){ }
    }

    /**
     * Test for checking tournament mode command invalid.
     */
    @Test
    public void testTournamentModeInValidCommand() {

       try {
           GameCommandParser l_command_parser = new GameCommandParser("tournament valid-testmap.map -P aggressive,benevolent -G 4 -D 20");
           List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();
           GameEngine l_game_engine = new GameEngine();
           l_game_engine.handleTournamentMode(l_command_details);
       } catch (Exception e) {
           Assert.assertEquals(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_TOURNAMENT_MODE, e.getMessage());
       }

    }

    /**
     * Test for checking tournament mode command valid.
     */
    @Test
    public void testTournamentModeInValidCommandOption() {

        try {
            GameCommandParser l_command_parser = new GameCommandParser("tournament -O valid-testmap.map -P aggressive,benevolent -G 4 -D 20");
            List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();
            GameEngine l_game_engine = new GameEngine();
            l_game_engine.handleTournamentMode(l_command_details);
            GameInformation l_game_information = GameInformation.getInstance();
        } catch (Exception e) {
            Assert.assertEquals(GameMessageConstants.D_COMMAND_OPTION_INVALID + "\nExample Format: " + GameMessageConstants.D_TOURNAMENT_MODE, e.getMessage());
        }

    }

}
