package gameplay;

import mapparser.GameMap;

import java.util.List;

public class DeployOrder extends Order {

    private String d_country_name;

    private int d_armies_number;

    public DeployOrder(String p_country_name, int p_armies_number) {
        this.d_country_name = p_country_name;
        this.d_armies_number = p_armies_number;
    }

    public String getCountryName() {
        return this.d_country_name;
    }

    public int getArmiesNumber() {
        return this.d_armies_number;
    }

    @Override
    public void execute(Player p_player_obj) {
        List<GameMap.Country> l_conquered_countries = p_player_obj.getConqueredCountries();
        for (GameMap.Country l_country_obj : l_conquered_countries) {
            if (l_country_obj.getCountryName().equals(getCountryName())) {
                l_country_obj.setArmyCount(l_country_obj.getArmyCount() + getArmiesNumber());
                break;
            }
        }
    }

}

