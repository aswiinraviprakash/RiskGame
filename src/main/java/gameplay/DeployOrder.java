package gameplay;

import mapparser.Map;

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
        List<Map.Country> l_conquered_countries = p_player_obj.getConqueredCountries() ;
        for (Map.Country l_country_obj : l_conquered_countries) {
            if (l_country_obj.d_country_name.equals(getCountryName())) {
                l_country_obj.d_army_count = l_country_obj.d_army_count + getArmiesNumber();
                break;
            }
        }

    }

}

