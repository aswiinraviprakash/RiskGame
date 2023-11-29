package gameutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for parsing user input commands into primary commands and their details.
 * It splits an input command into its primary command and
 */
public class GameCommandParser {

    /**
     * member to store entire command
     */
    private final String d_entire_command;

    /**
     *  Creates a new instance with the provided input.
     * @param p_entire_command The entire command to be parsed.
     */
    public GameCommandParser(String p_entire_command) {
       this.d_entire_command = p_entire_command.trim();
       
    }

    /**
     * Retrieves primary command from the input command.
     * @return The primary command.
     */
    public String getPrimaryCommand() {
        return this.d_entire_command.split(" ")[0];
    }

    /**
     * Parses the input into a list, whic may include command options and parameters.
     * @return A list of commanddetails representing the parsed command.
     */
    public List<CommandDetails> getParsedCommandDetails() {
        List<CommandDetails> l_command_details = new ArrayList<>();

        String l_entire_command = d_entire_command;
        l_entire_command = l_entire_command.replace(getPrimaryCommand(), "");

        if (l_entire_command.isEmpty()) return l_command_details;

        List<String> l_command_parameters;
        if (l_entire_command.startsWith(" -")) {
            String[] l_sub_commands = l_entire_command.split(" -");

            for (String l_sub_command : l_sub_commands) {
                if (l_sub_command.isEmpty()) continue;

                l_command_parameters = Arrays.asList(l_sub_command.split(" "));
                CommandDetails l_command_details_obj = new CommandDetails();
                l_command_details_obj.setHasCommandOption(true);

                l_command_details_obj.setCommandOption(l_command_parameters.get(0));
                l_command_details_obj.setCommandParameters(l_command_parameters.subList(1, l_command_parameters.size()));
                l_command_details.add(l_command_details_obj);
            }

        } else {
            l_entire_command = l_entire_command.trim();
            l_command_parameters = Arrays.asList(l_entire_command.split(" "));
            CommandDetails l_command_details_obj = new CommandDetails();
            l_command_details_obj.setHasCommandOption(false);
            l_command_details_obj.setCommandParameters(l_command_parameters);
            l_command_details.add(l_command_details_obj);
        }

        return l_command_details;
    }

    /**
     * Contains the details of a parsed command
     */
    public class CommandDetails {

        /**
<<<<<<< Updated upstream
         * Boolean to check for command option
=======
         * contains boolean value for command options
>>>>>>> Stashed changes
         */
        private boolean d_has_command_option;

        /**
<<<<<<< Updated upstream
         * String containing command option
=======
         * contains command option
>>>>>>> Stashed changes
         */
        private String d_command_option;

        /**
<<<<<<< Updated upstream
         * List of String with parameters
=======
         * List for command parameters
>>>>>>> Stashed changes
         */
        private List<String> d_command_parameters;

        /**
         * Sets whether the command has an option
         * @param p_has_command_option True if the command has an option, false otherwise.
         */
        public void setHasCommandOption(boolean p_has_command_option) {
            this.d_has_command_option = p_has_command_option;
        }

        /**
         * Sets command option
         * @param p_command_option Command option
         */
        public void setCommandOption(String p_command_option) {
            this.d_command_option = p_command_option;
        }

        /**
         * Sets the list of command parameters
         * @param p_command_parameters List of command parameters
         */
        public void setCommandParameters(List<String> p_command_parameters) {
            this.d_command_parameters = p_command_parameters;
        }

        /**
         * Retrieves whether the command has an option
         * @return True if the command has an option, false otherwise
         */
        public boolean getHasCommandOption() {
            return this.d_has_command_option;
        }

        /**
         * Retrieves whether the command has an option.
         * @return True if the command has an option.
         */
        public String getCommandOption() {
            return this.d_has_command_option ? this.d_command_option : "";
        }

        /**
         * Retrieves the list of command parameters.
         * @return List of the command parameters.
         */
        public List<String> getCommandParameters() {
            return this.d_command_parameters;
        }

    }

}
