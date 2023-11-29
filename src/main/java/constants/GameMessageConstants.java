package constants;

/**
 * This class initializes all the constant messages that will be used in the application.
 */
public class GameMessageConstants {

    /**
     * Order Constants
     */
    public static final String D_DEPLOY = "Deploy";

    /**
     * Order Constants
     */
    public static final String D_ADVANCE = "Advance";

    /**
     * Order Constants
     */
    public static final String D_BOMB = "Bomb";

    /**
     * Order Constants
     */
    public static final String D_BLOCKADE = "Blockade";

    /**
     * Order Constants
     */
    public static final String D_DIPLOMACY = "Negotiate";

    /**
     * Order Constants
     */
    public static final String D_AIRLIFT = "Airlift";

    /**
     * Command Format Constants
     */
    public static final String D_EDITMAP = "editmap filename";

    /**
     * Command Format Constants
     */
    public static final String D_EDITCONTINENT = "editmap filename";

    /**
     * Command Format Constants
     */
    public static final String D_NEW_MAP = "Map Created";

    /**
     * Command Format Constants
     */
    public static final String D_LOADMAP = "loadmap filename";

    /**
     * Command Format Constants
     */
    public static final String D_PLAYER_COMMAND = "gameplayer -add playername -remove playername";

    /**
     * Command Format Constants
     */
    public static final String D_DEPLOY_COMMAND = "deploy countryID num";

    /**
     * Command Format Constants
     */
    public static final String D_ADVANCE_COMMAND = "advance countrynamefrom countynameto numarmies";

    /**
     * Command Format Constants
     */
    public static final String D_BOMB_COMMAND = "bomb countryID";

    /**
     * Command Format Constants
     */
    public static final String D_BLOCKADE_COMMAND = "blockade countryID";

    /**
     * Command Format Constants
     */
    public static final String D_DIPLOMACY_COMMAND = "negotiate playerID";

    /**
     * Command Format Constants
     */
    public static final String D_AIRLIFT_COMMAND = "airlift countrynamefrom countynameto numarmies";

    /**
     * Command Format Constants
     */
    public static final String D_ASSIGNCOUNTRIES_COMMAND = "assigncountries";

    /**
     * Command Format Constants
     */
    public static final String D_SHOWMAP = "showmap";

    /**
     * Command Format Constants
     */
    public static final String D_VALIDATEMAP = "validatemap";

    /**
     * Error Message Constants
     */
    public static final String D_INTERNAL_ERROR = "There seems to be some issue try restarting the Game";

    /**
     * Error Message Constants
     */
    public static final String D_COMMAND_INVALID = "Command seems to be invalid enter valid one";

    /**
     * Error Message Constants
     */
    public static final String D_COMMAND_NO_OPTION_SUPPORT = "Command format does not support option";
    /**
     * Error Message Constants
     */
    public static final String D_COMMAND_OPTION_INVALID = "Command option seems to be invalid";

    /**
     * Error Message Constants
     */
    public static final String D_COMMAND_PARAMETER_INVALID = "Command parameter seems to be invalid";

    /**
     * Error Message Constants
     */
    public static final String D_STARTUP_STEPS_INAVLID = "please loadmap and create players before assigning countries";

    /**
     * Error Message Constants
     */
    public static final String D_PLAYER_EXISTS = "Player you are trying to add already exists";

    /**
     * Error Message Constants
     */
    public static final String D_PLAYER_NOTFOUND = "Player your are trying to remove not found";

    /**
     * Error Message Constants
     */
    public static final String D_ARMIES_EXCEEDED = "Armies you are trying to deploy exceeded available limit";

    /**
     * Error Message Constants
     */
    public static final String D_COUNTRY_INVALID_FOR_PLAYER = "Player does'nt own the country your are trying to initiate order";

    /**
     * Error Message Constants
     */
    public static final String D_TO_COUNTRY_INVALID = "The destination country your are trying initiate order does'nt exists";

    /**
     * Error Message Constants
     */
    public static final String D_ATTACK_ARMIES_EXCEEDED = "Armies you are trying to move exceeded the available limit than in source country";

    /**
     * Error Message Constants
     */
    public static final String D_BOMB_DESTINATION_INVALID = "Destination country should not be owned by player";

    /**
     * Error Message Constants
     */
    public static final String D_BLOCKADE_DESTINATION_INVALID = "Destination country should be owned by player";

    /**
     * Error Message Constants
     */
    public static final String D_DIPLOMCY_DESTINATION_INVALID = "The destination player trying to negotiate seems to be invalid";

    /**
     * Error Message Constants
     */
    public static final String D_COUNTRY_NOT_ADJACENT = "The source and destination countries you are trying to initiate order are not adjacent";

    /**
     * Error Message Constants
     */
    public static final String D_DESTINATION_COUNTRY_NOT_ADJACENT = "The destination country you are trying to initiate order is not adjacent";

    /**
     * Error Message Constants
     */
    public static final String D_CARD_INAVLID = "Player does'nt own any card for order: ";

    /**
     * Error Message Constants
     */    
    public static final String D_INVALID_COUNTRY = "The country does not belong to you";

    /**
     * Error Message Constants
     */    
    public static final String D_MAP_LOAD_FAILED = "There seems to be an issue loading your map. Please check the structure of Continent, Country and Borders in you map.";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_LOAD_FAILED_EXIT = "Something is wrong with the map, type 'exit' to leave map editor menu  and choose another map file using editmap command";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_VALIDATION_ERROR = "There seems to be an issue with the map structure";

    /**
     * Error Message Constants
     */
    public static final String D_CRITICAL_MAP_NAME = "Please enter the correct map file name";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_PROBLEM = "Something is wrong with the map";

    /**
     * Error Message Constants
     */
    public static final String D_EDIT_CONTINENT_ERROR = "Please enter the continent name and its bonus value";

    /**
     * Error Message Constants
     */
    public static final String D_EDIT_CONTINENT_VALID_BONUS = "Please enter a valid continent value";

    /**
     * Error Message Constants
     */
    public static final String D_SKIPPED_COUNTRY_CONTINENT = "Please enter the country name and continent name";

    /**
     * Error Message Constants
     */
    public static final String D_MISSING_COUNTRY_NAME = "Please enter the Country name";

    /**
     * Error Message Constants
     */
    public static final String D_MISSING_CONTINENT_NAME = "Please enter the Continent name";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_EMPTY_CONTINENTS = "Continents seems to be empty in map";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_EMPTY_COUNTRIES = "Countries seems to be empty in map";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_EMPTY_BORDERS = "Borders seems to be empty in map";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_COUNTRY_EMPTY_BORDERS = "seems to be isolated with no borders defined";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_COUNTRY_INVALID_BORDERS = "does not have borders in same continent and is not reachable";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_NO_COUNTRY = "Country does not exist";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_NO_CONTINENT = "Continent does not exist";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_NO_NEIGHBOR = "Neighbor does not exist";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_DUPLICATE_COUNTRY  = "Country already exists";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_DUPLICATE_NEIGHBOR = "Neighbor already added";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_MISSING_COUNTRY_AND_NEIGHBOR = "Please enter the country name and the neighboring country name";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_INVALID_NEIGHBOR = "Country and neighboring country can not be the same";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_INVALID_COUNTRY_NEIGHBOR = "Invalid Countries enterd";

    /**
     * Error Message Constants
     */
    public static final String D_MAP_VALIDATION_PASS = "The map is valid";

    /**
     * Success Message Constants
     */
    public static final String D_GAMEMAP_LOADED = "Game Map loaded successfully";

    /**
     * Success Message Constants
     */
    public static final String D_VALID_MAP = "The Map is Valid";

    /**
     * Success Message Constants
     */
    public static final String D_SAVE_MAP = "Map file saved";

    /**
     * Success Message Constants
     */
    public static final String D_PLAYER_ADDED = "Player added successfully";

    /**
     * Success Message Constants
     */
    public static final String D_PLAYER_REMOVED = "Player removed successfully";

    /**
     * Success Message Constants
     */
    public static final String D_COUNTRIES_ASSIGNED = "Countries assigned successfully";

    /**
     * Success Message Constants
     */
    public static final String D_GAME_STARTUP_SUCCESS = "Game startup completed";

    /**
     * Success Message Constants
     */
    public static final String D_ORDER_ISSUED = "Order issued successfully";

    /**
     * Success Message Constants
     */    
    public static final String D_MAP_SAVED = "The map is saved";

    /**
     * Success Message Constants
     */
    public static final String D_CONTINENT_ADDED = "Continent added successfully ";

    /**
     * Success Message Constants
     */
    public static final String D_CONTINENT_REMOVED = "Removed Continent ";

    /**
     * Success Message Constants
     */
    public static final String D_COUNTRY_ADDED = "Added Country ";

    /**
     * Success Message Constants
     */
    public static final String D_COUNTRY_REMOVED = "Removed Country ";

    /**
     * Success Message Constants
     */
    public static final String D_RELATION_ADDED = "Added neighbor relation between ";

    /**
     * Success Message Constants
     */
    public static final String D_RELATION_REMOVED = "Removed neighbor relation between ";

    /**
     * Error while saving game
     */
    public static final String D_SAVE_GAME_ERROR = "Error while saving the game ";
<<<<<<< Updated upstream
    
    public static final String D_LOAD_GAME_ERROR = "Error while loading the game ";
    
=======

    /**
     * Successfully loaded the game
     */
>>>>>>> Stashed changes
    public static final String D_LOAD_GAME_SUCCESS = "Successfully loaded the game ";

    /**
     * Successfully saved the game
     */
    public static final String D_SAVE_GAME_SUCCESS = "Successfully saved the game ";
    
    
    
    

}