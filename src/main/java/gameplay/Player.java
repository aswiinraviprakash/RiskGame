package gameplay;

import java.util.ArrayList;
import java.util.List;
import mapparser.GameMap;

/**
 * Contains a players name,current armies, and issued orders.
 */
public class Player {

    /**
     * Used for storing name of the player.
     */
    private String d_player_name;

    /**
     * Used for storing current armies.
     */
    private int d_current_armies;

    /**
     * Contains orders in a list.
     */
    private List<Order> d_orders_list = new ArrayList<>();

    public Order d_current_order = null;

    public List<GameMap.Country> d_conquered_countries = new ArrayList<>();

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
    public void setConqueredCountry(GameMap.Country p_conquered_country) {
        this.d_conquered_countries.add(p_conquered_country);
    }

    /**
     *  Sets the list of conquered countries by copying from the list.
     * @param p_conquered_countries List of conquered countries to set.
     */
    public void setConqueredCountries(List<GameMap.Country> p_conquered_countries) {
        for (GameMap.Country l_conquered_country : p_conquered_countries) {
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
    public List<GameMap.Country> getConqueredCountries() {
        return this.d_conquered_countries;
    }

    /**
     * This method checks if a country with a given ID has been conquered by the player.
     * @param p_country_id Id of the country to check if it has been conquered.
     * @return True if the given ID has been conquered by the player.
     */
    public boolean checkIfCountryConquered(int p_country_id) {
        for (GameMap.Country l_conquered_country : d_conquered_countries) {
            if (l_conquered_country.getCountryID() == p_country_id) return true;
        }
        return false;
    }

    /**
     * This method checks if a country with a given name has been conquered by the player.
     * @param p_country_name The name of the country to check if it has been conquered.
     * @return True if the given country name has been conquered.
     */
    public boolean checkIfCountryConquered(String p_country_name) {
        for (GameMap.Country l_conquered_country : d_conquered_countries) {
            if (l_conquered_country.getCountryName().equals(p_country_name)) return true;
        }
        return false;
    }

    /**
     * This method returns next order in players list of orders.
     * @return Next order of player's order.
     */
    public Order next_order() {
        if (d_orders_list.isEmpty()) return null;

        Order d_current_order = this.d_orders_list.get(0);
        this.d_orders_list.remove(0);
        return d_current_order;
    }

}