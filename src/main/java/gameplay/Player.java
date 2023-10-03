package gameplay;

import java.util.ArrayList;
import java.util.List;
import mapparser.Map;

/**
 * Contains a players name,current armies, and issued orders.
 */
public class Player {

    private String d_player_name;

    private int d_current_armies;

    private List<Order> d_orders_list = new ArrayList<>();

    public Order d_current_order = null;

    public List<Map.Country> d_conquered_countries = new ArrayList<>();

    /**
     * Creates a new player with specified name.
     * @param l_player_name
     */
    public Player(String l_player_name) {
        this.d_player_name = l_player_name;
    }

    /**
     * Adds current order to the players list of orders.
     */
    public void issue_order() {
        if (d_current_order != null) d_orders_list.add(this.d_current_order);
    }

    /**
     * Sets current number of armies owned by player.
     * @param p_current_armies Current number of armies.
     */
    public void setCurrentArmies(int p_current_armies) {
        this.d_current_armies = p_current_armies;
    }

    /**
     * Adds conquered country to the list of countries by the player.
     * @param p_conquered_country
     */
    public void setConqueredCountry(Map.Country p_conquered_country) {
        this.d_conquered_countries.add(p_conquered_country);
    }

    /**
     *  Sets the list of conquered countries by copying from the list.
     * @param p_conquered_countries List of conquered countries to set.
     */
    public void setConqueredCountries(List<Map.Country> p_conquered_countries) {
        for (Map.Country l_conquered_country : p_conquered_countries) {
            this.d_conquered_countries.add(l_conquered_country);
        }
    }

    /**
     * Method to get player name.
     * @return Player name.
     */
    public String getPlayerName() {
        return this.d_player_name;
    }

    /**
     * Gets the current armies owned by the player.
     * @return nNumber of current armies.
     */
    public int getCurrentArmies() {
        return this.d_current_armies;
    }

    /**
     * Gets list of countries conquered by the player.
     * @return Conquered countries.
     */
    public List<Map.Country> getConqueredCountries() {
        return this.d_conquered_countries;
    }

    /**
     *  Checks if a player conquered a specific country by its name
     * @param p_country_name Name of the country to be checked
     * @return True if conquered, false otherwise.
     */
    public boolean checkIfCountryConquered(String p_country_name) {
        for (Map.Country l_conquered_country : d_conquered_countries) {
            if (l_conquered_country.d_country_name.equals(p_country_name)) return true;
        }
        return false;
    }

}
