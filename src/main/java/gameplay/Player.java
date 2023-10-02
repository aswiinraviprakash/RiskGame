package gameplay;

import java.util.ArrayList;
import java.util.List;
import mapparser.Map;

public class Player {

    private String d_player_name;

    private int d_current_armies;

    private List<Order> d_orders_list = new ArrayList<>();

    public Order d_current_order = null;

    public List<Map.Country> d_conquered_countries = new ArrayList<>();

    public Player(String l_player_name) {
        this.d_player_name = l_player_name;
    }

    public void issue_order() {
        if (d_current_order != null) d_orders_list.add(this.d_current_order);
    }

    public void setCurrentArmies(int p_current_armies) {
        this.d_current_armies = p_current_armies;
    }   

    public void setConqueredCountry(Map.Country p_conquered_country) {
        this.d_conquered_countries.add(p_conquered_country);
    }

    public void setConqueredCountries(List<Map.Country> p_conquered_countries) {
        for (Map.Country l_conquered_country : p_conquered_countries) {
            this.d_conquered_countries.add(l_conquered_country);
        }
    }

    public String getPlayerName() {
        return this.d_player_name;
    }

    public int getCurrentArmies() {
        return this.d_current_armies;
    }

    public List<Map.Country> getConqueredCountries() {
        return this.d_conquered_countries;
    }

    public boolean checkIfCountryConquered(String p_country_name) {
        for (Map.Country l_conquered_country : d_conquered_countries) {
            if (l_conquered_country.d_country_name.equals(p_country_name)) return true;
        }
        return false;
    }

    public Order next_order() {
        if (d_orders_list.isEmpty()) return null;

        Order d_current_order = this.d_orders_list.get(0);
        this.d_orders_list.remove(0);
        return d_current_order;
    }

}