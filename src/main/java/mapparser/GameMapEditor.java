package mapparser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import common.LogEntryBuffer;
import common.Phase;
import constants.GameMessageConstants;
import gameutils.GameCommandParser;
import gameutils.GameException;

/**
 * The class is used to initialize map editing phase - which consists of
 * editing, or creating a new map.
 */
public class GameMapEditor {

    private static LogEntryBuffer d_logger = LogEntryBuffer.getInstance();

    /**
     * Function is used for the initial map editing phase.
     * @throws Exception If there is an error in the execution or validation.
     */
    public void initialiseAndRunMapEditor() throws Exception {

        BufferedReader l_reader = new BufferedReader(new InputStreamReader(System.in));

        // validate user input map commands commands
        System.out.println("Map Menu - Type editmap filename or exit to Exit");

        String l_map_command = l_reader.readLine();
        while (!l_map_command.equals("exit")) {

            try {

                GameCommandParser l_command_parser = new GameCommandParser(l_map_command);
                String l_primary_command = l_command_parser.getPrimaryCommand();
                List<GameCommandParser.CommandDetails> l_command_details = l_command_parser.getParsedCommandDetails();

                if (l_primary_command.equals("editmap")) {

                    if (l_command_details.isEmpty()) throw new GameException(GameMessageConstants.D_COMMAND_INVALID + "\nExample Format: " + GameMessageConstants.D_EDITMAP);

                    GameCommandParser.CommandDetails l_command_detail = l_command_details.get(0);
                    if (l_command_detail.getHasCommandOption()) throw new GameException(GameMessageConstants.D_COMMAND_NO_OPTION_SUPPORT + "\nExample Format: " + GameMessageConstants.D_EDITMAP);

                    List<String> l_command_parameters = l_command_detail.getCommandParameters();
                    if (!(l_command_parameters.size() == 1)) throw new GameException(GameMessageConstants.D_COMMAND_PARAMETER_INVALID + "\nExample Format: " + GameMessageConstants.D_EDITMAP);
                    String l_map_file_name = l_command_parameters.get(0);

                    // Initializing loadmap phase
                    Phase l_current_phase = new LoadMapPhase(l_map_file_name, true);
                    l_current_phase.executePhase();
                    GameMap l_game_map = ((LoadMapPhase) l_current_phase).getLoadedMap();

                    // Initializing editmap phase
                    l_current_phase = new EditMapPhase(l_game_map, l_map_file_name);
                    l_current_phase.executePhase();

                } else {
                    throw new GameException(GameMessageConstants.D_COMMAND_INVALID);
                }

            } catch (GameException e) {
                System.out.println(e.getMessage());
                d_logger.addLogger(e.getMessage());
            } catch (Exception e) {
                throw e;
            }

            System.out.println("Map Menu - Type editmap filename or exit to Exit");
            l_map_command = l_reader.readLine();
        }

    }

}
