package gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private HashMap<Card, Integer> d_available_cards = new HashMap();

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
     * Method to get a list of orders.
     * @return order list.
     */
    public List<Order> getOrders() {
        return this.d_orders_list;
    }

    /**
     *
     * @param p_target_player target player object.
     * @return
     */
    public boolean checkDiplomacyRelation(Player p_target_player) {
        for (Order l_order_obj : d_orders_list) {
            if (l_order_obj instanceof DiplomacyOrder) {
                Player l_target_player = ((DiplomacyOrder) l_order_obj).getTargetPlayer();
                if (p_target_player.getPlayerName().equals(l_target_player.getPlayerName())) return true;
            }
        }

        return false;
    }

    /**
     * Gets list of countries conquered by the player.
     * @return Conquered countries.
     */
    public List<GameMap.Country> getConqueredCountries() {
        return this.d_conquered_countries;
    }

    /**
     * Method returns the cards that are distributes to the players.
     * @return alloted cards.
     */
    public HashMap<Card, Integer> getAvailableCards() {
        return this.d_available_cards;
    }

    public void addAvailableCard(Card l_card_obj) {
        if (!d_available_cards.containsKey(l_card_obj)) {
            d_available_cards.put(l_card_obj, 1);
        } else {
            int l_card_count = d_available_cards.get(l_card_obj);
            d_available_cards.put(l_card_obj, l_card_count + 1);
        }
    }

    /**
     * Method removes the card allowed to the player after use.
     * @param l_card_obj card that is to be removed.
     */
    public void removeAvailableCard(Card l_card_obj) {
        if (d_available_cards.containsKey(l_card_obj)) {
            int l_card_count = d_available_cards.get(l_card_obj);
            d_available_cards.put(l_card_obj, l_card_count - 1);
        }
    }

    /**
     * Method is used for listing all the cards currently available to the player.
     * @return cards that are allotted.
     */
    public String printAvailableCards() {
        String l_available_cards = "";
        for (Map.Entry<Card, Integer> l_entry : d_available_cards.entrySet()) {
            l_available_cards = l_available_cards + l_entry.getKey() + ": " + l_entry.getValue() + " ";
        }

        return l_available_cards;
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