package gameplay;

import common.Phase;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Game Engine class.
 */
public class GameEngineTest {

    List<Phase> l_phases = new ArrayList<Phase>();

    /**
     * Initialising test data.
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
     * Test for the various game phases.
     */
    @Test
    public void gameStartUpPhaseTest() {
        
        int l_index = 0;
        
        try{
            
        Assert.assertTrue(l_phases.get(l_index) instanceof GameStartUpPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof ReinforcementPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof IssueOrderPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof ExecuteOrderPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof ReinforcementPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof IssueOrderPhase); 
        Assert.assertTrue(l_phases.get(l_index++).nextPhase() instanceof ExecuteOrderPhase);
        }catch(Exception e){  }
        
    }

}
