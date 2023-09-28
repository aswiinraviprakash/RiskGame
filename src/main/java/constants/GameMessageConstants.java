package constants;

public class GameMessageConstants {

    // Command Format Constants
    public static final String D_PLAYER_COMMAND = "gameplayer -add playername -remove playername";

    public static final String D_DEPLOY_COMMAND = "deploy countryID num";

    // Error Message Constants
    public static final String D_INTERNAL_ERROR = "There seems to be some issue try restarting the Game";

    public static final String D_COMMAND_INVALID = "Command seems to be invalid enter valid one";

    public static final String D_COMMAND_INCOMPLETE = "Command seems to incomplete";

    public static final String D_COMMAND_NO_OPTION_SUPPORT = "Command format does not support option";

    public static final String D_COMMAND_OPTION_NOTFOUND = "Command option not found";

    public static final String D_COMMAND_OPTION_INVALID = "Command option seems to be invalid";

    public static final String D_COMMAND_PARAMETERS_EXCEEDS = "Command parameter exceeds the limit needed";

    public static final String D_COMMAND_PARAMETER_NOTFOUND = "Command parameter not found";

    public static final String D_COMMAND_PARAMETER_INVALID = "Command parameter seems to be invalid";

    public static final String D_PLAYER_EXISTS = "Player you are trying to add already exists";

    public static final String D_PLAYER_NOTFOUND = "Player your are trying to remove not found";

    public static final String D_ARMIES_EXCEEDED = "Armies you are trying to deploy exceeded available limit";

    // Success Message Constants
    public static final String D_PLAYER_ADDED = "Player Added Successfully";

    public static final String D_PLAYER_REMOVED = "Player Removed Successfully";

    public static final String D_ORDER_ISSUED = "Order Issued Successfully";

}
