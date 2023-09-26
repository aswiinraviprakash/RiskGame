package gameutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameCommandParser {

    private final String d_entire_command;

    public GameCommandParser(String p_entire_command) {
        this.d_entire_command = p_entire_command;
    }

    public String getPrimaryCommand() {
        return this.d_entire_command.split(" ")[0];
    }

    public List<CommandDetails> getParsedCommandDetails() {
        List<CommandDetails> l_command_details = new ArrayList<>();

        String l_entire_command = d_entire_command;
        l_entire_command = l_entire_command.replace(getPrimaryCommand(), "").trim();

        if (l_entire_command.isEmpty()) {
            return l_command_details;
        }

        List<String> l_command_parameters;
        if (l_entire_command.startsWith("-")) {
            String[] l_sub_commands = l_entire_command.split("-");

            for (String l_sub_command : l_sub_commands) {
                if (l_sub_command.isEmpty()) {
                    continue;
                }

                l_command_parameters = Arrays.asList(l_sub_command.split(" "));
                CommandDetails l_command_details_obj = new CommandDetails();
                l_command_details_obj.setHasCommandOption(true);

                l_command_details_obj.setCommandOption(l_command_parameters.get(0));
                l_command_details_obj.setCommandParameters(l_command_parameters.subList(1, l_command_parameters.size()));
                l_command_details.add(l_command_details_obj);
            }

        } else {
            l_command_parameters = Arrays.asList(l_entire_command.split(" "));
            CommandDetails l_command_details_obj = new CommandDetails();
            l_command_details_obj.setHasCommandOption(false);
            l_command_details_obj.setCommandParameters(l_command_parameters);
            l_command_details.add(l_command_details_obj);
        }

        return l_command_details;
    }

    public class CommandDetails {

        private boolean d_has_command_option;
        private String d_command_option;
        private List<String> d_command_parameters;

        public void setHasCommandOption(boolean p_has_command_option) {
            this.d_has_command_option = p_has_command_option;
        }

        public void setCommandOption(String p_command_option) {
            this.d_command_option = p_command_option;
        }

        public void setCommandParameters(List<String> p_command_parameters) {
            this.d_command_parameters = p_command_parameters;
        }

        public boolean getHasCommandOption() {
            return this.d_has_command_option;
        }

        public String getCommandOption() {
            return this.d_has_command_option ? this.d_command_option : "";
        }

        public List<String> getCommandParameters() {
            return this.d_command_parameters;
        }

    }

}
