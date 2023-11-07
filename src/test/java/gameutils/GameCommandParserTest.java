package gameutils;

import org.junit.Test;
import org.junit.Assert;

import java.util.List;

/**
 * Test for Game command Parser.
 */
public class GameCommandParserTest {

    /**
     * Test to check commands present which have options.
     */
    @Test
    public void testCommandWithOption() {
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

    /**
     * Test to check commands that are without options.
     */
    @Test
    public void testCommandWithoutOption() {
        GameCommandParser l_gamecommand_parser = new GameCommandParser("loadmap filename");
        Assert.assertEquals(l_gamecommand_parser.getPrimaryCommand(), "loadmap");
        List<GameCommandParser.CommandDetails> l_parsed_command_details = l_gamecommand_parser.getParsedCommandDetails();

        // checking if option and parameters are extracted correctly
        Assert.assertEquals(l_parsed_command_details.size(), 1);

        GameCommandParser.CommandDetails l_command_detail = l_parsed_command_details.get(0);
        Assert.assertEquals(l_command_detail.getHasCommandOption(), false);
        Assert.assertEquals(l_command_detail.getCommandParameters().size(), 1);
        Assert.assertEquals(l_command_detail.getCommandParameters().get(0), "filename");
    }

    /**
     * Test command without option and parameter.
     */
    @Test
    public void testCommandWithoutOptionAndParmeter() {
        GameCommandParser l_gamecommand_parser = new GameCommandParser("assigncountries");
        Assert.assertEquals(l_gamecommand_parser.getPrimaryCommand(), "assigncountries");
        List<GameCommandParser.CommandDetails> l_parsed_command_details = l_gamecommand_parser.getParsedCommandDetails();

        Assert.assertEquals(l_parsed_command_details.size(), 0);
    }

}
