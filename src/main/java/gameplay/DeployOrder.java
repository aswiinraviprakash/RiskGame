package gameplay;

import mapparser.GameMap;

import java.util.List;

/**
 * Class represents an order to deploy a certain number of armies to a specific country.
 */
public class DeployOrder extends Order {

    private String d_country_name;

    private int d_armies_number;

    /**
     * Initializes d_country_name and d_armies_number
     * @param p_country_name Name of country where the army will be deployed.
     * @param p_armies_number The number of armies to be deployed.
     */
    public DeployOrder(String p_country_name, int p_armies_number) {
        this.d_country_name = p_country_name;
        this.d_armies_number = p_armies_number;
    }

    /**
     * Returns the name of the country where the army will be deployed.
     * @return Name of the country.
     */
    public String getCountryName() {
        return this.d_country_name;
    }

    /**
     * This method returns the number of armies to be deployed.
     * @return Integer value representing the number of armies.
     */
    public int getArmiesNumber() {
        return this.d_armies_number;
    }

    /**
     * {@inheritDoc}
     * Responsible for deploying armies to a specific country owned by a player.
     * @param p_player_obj The player object to whom the armies will be deployed.
     */
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

