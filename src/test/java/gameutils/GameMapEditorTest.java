package gameutils;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author USER
 */
public class GameMapEditorTest {
    
    
    @Test
    public void testLoadContinent() {
        GameCommandParser l_gamecommand_parser = new GameCommandParser("gameplayer -add player1");
        Assert.assertEquals(l_gamecommand_parser.getPrimaryCommand(), "gameplayer");
        List<GameCommandParser.CommandDetails> l_parsed_command_details = l_gamecommand_parser.getParsedCommandDetails();

        // checking if option and parameters are extracted correctly
        Assert.assertEquals(l_parsed_command_details.size(), 1);

        GameCommandParser.CommandDetails l_command_detail = l_parsed_command_details.get(0);
        Assert.assertEquals(l_command_detail.getHasCommandOption(), true);
        Assert.assertEquals(l_command_detail.getCommandOption(), "add");
        Assert.assertEquals(l_command_detail.getCommandParameters().size(), 1);
        Assert.assertEquals(l_command_detail.getCommandParameters().get(0), "player1");
    }
    
    @Test
    public void testLoadCountry(){
        
    }
    
    @Test
    public void testLoadBorder(){
        
    }
}
