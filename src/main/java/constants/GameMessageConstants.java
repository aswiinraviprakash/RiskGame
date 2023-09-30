package constants;

public class GameMessageConstants {

    // Command Format Constants
    public static final String D_PLAYER_COMMAND = "gameplayer -add playername -remove playername";

    public static final String D_DEPLOY_COMMAND = "deploy countryID num";

    // Error Message Constants
    public static final String D_INTERNAL_ERROR = "There seems to be some issue try restarting the Game";

    public static final String D_COMMAND_INVALID = "Command seems to be invalid enter valid one";

    public static final String D_COMMAND_NO_OPTION_SUPPORT = "Command format does not support option";

    public static final String D_COMMAND_OPTION_INVALID = "Command option seems to be invalid";

    public static final String D_COMMAND_PARAMETER_INVALID = "Command parameter seems to be invalid";

    public static final String D_STARTUP_STEPS_INAVLID = "please loadmap and create players before assigning countries";

    public static final String D_PLAYER_EXISTS = "Player you are trying to add already exists";

    public static final String D_PLAYER_NOTFOUND = "Player your are trying to remove not found";

    public static final String D_ARMIES_EXCEEDED = "Armies you are trying to deploy exceeded available limit";
    
    public static final String D_MAP_LOAD_FAILED = "There seems to be an issue loading your map";
    
    public static final String D_MAP_VALIDATION_ERROR = "There seems to be an issue with the map structure";
    
    // Success Message Constants
    public static final String D_PLAYER_ADDED = "Player Added Successfully";

    public static final String D_PLAYER_REMOVED = "Player Removed Successfully";

    public static final String D_GAME_STARTUP_SUCCESS = "Game Startup Completed";

    public static final String D_ORDER_ISSUED = "Order Issued Successfully";

}
