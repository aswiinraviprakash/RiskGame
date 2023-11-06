package constants;

/**
 * This class initializes all the constant messages that will be used in the application.
 */
public class GameMessageConstants {

    // Order Types

    public static final String D_DEPLOY = "Deploy";

    public static final String D_ADVANCE = "Advance";

    public static final String D_BOMB = "Bomb";

    public static final String D_BLOCKADE = "Blockade";

    public static final String D_DIPLOMACY = "Negotiate";

    public static final String D_AIRLIFT = "Airlift";

    // Command Format Constants
    public static final String D_EDITMAP = "editmap filename";

    public static final String D_EDITCONTINENT = "editmap filename";

    public static final String D_LOADMAP = "loadmap filename";

    public static final String D_PLAYER_COMMAND = "gameplayer -add playername -remove playername";

    public static final String D_DEPLOY_COMMAND = "deploy countryID num";

    public static final String D_ADVANCE_COMMAND = "advance countrynamefrom countynameto numarmies";

    public static final String D_BOMB_COMMAND = "bomb countryID";

    public static final String D_BLOCKADE_COMMAND = "blockade countryID";

    public static final String D_DIPLOMACY_COMMAND = "negotiate playerID";

    public static final String D_AIRLIFT_COMMAND = "airlift countrynamefrom countynameto numarmies";

    public static final String D_ASSIGNCOUNTRIES_COMMAND = "assigncountries";

    public static final String D_SHOWMAP = "showmap";

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

    public static final String D_COUNTRY_INVALID_FOR_PLAYER = "Player does'nt own the country your are trying to initiate order";

    public static final String D_TO_COUNTRY_INVALID = "The destination country your are trying initiate order does'nt exists";

    public static final String D_ATTACK_ARMIES_EXCEEDED = "Armies you are trying to move exceeded the available limit than in source country";

    public static final String D_BOMB_DESTINATION_INVALID = "Destination country should not be owned by player";

    public static final String D_BLOCKADE_DESTINATION_INVALID = "Destination country should be owned by player";

    public static final String D_DIPLOMCY_DESTINATION_INVALID = "The destination player trying to negotiate seems to be invalid";

    public static final String D_COUNTRY_NOT_ADJACENT = "The source and destination countries you are trying to initiate order are not adjacent";

    public static final String D_DESTINATION_COUNTRY_NOT_ADJACENT = "The destination country you are trying to initiate order is not adjacent";

    public static final String D_CARD_INAVLID = "Player does'nt own any card for order: ";
    
    public static final String D_INVALID_COUNTRY = "The country does not belong to you";
    
    public static final String D_MAP_LOAD_FAILED = "There seems to be an issue loading your map. Please check the structure of Continent, Country and Borders in you map.";
    
    public static final String D_MAP_VALIDATION_ERROR = "There seems to be an issue with the map structure";
    
    
    public static final String D_MAP_EMPTY_CONTINENTS = "Continents seems to be empty in map";

    public static final String D_MAP_EMPTY_COUNTRIES = "Countries seems to be empty in map";

    public static final String D_MAP_EMPTY_BORDERS = "Borders seems to be empty in map";

    public static final String D_MAP_COUNTRY_EMPTY_BORDERS = "seems to be isolated with no borders defined";

    public static final String D_MAP_COUNTRY_INVALID_BORDERS = "does not have borders in same continent and is not reachable";
    
    public static final String D_MAP_NO_COUNTRY = "Country does not exist";

    public static final String D_MAP_NO_CONTINENT = "Continent does not exist";
    
    public static final String D_MAP_VALIDATION_PASS = "The map is valid";
    
    // Success Message Constants
    public static final String D_GAMEMAP_LOADED = "Game Map loaded successfully";

    public static final String D_PLAYER_ADDED = "Player added successfully";

    public static final String D_PLAYER_REMOVED = "Player removed successfully";

    public static final String D_COUNTRIES_ASSIGNED = "Countries assigned successfully";

    public static final String D_GAME_STARTUP_SUCCESS = "Game startup completed";

    public static final String D_ORDER_ISSUED = "Order issued successfully";
    
    public static final String D_MAP_SAVED = "The map is saved";

}
