package gameplay;

/**
 * This class contains orders in the game, Including order type, country name and number of armies
 */
public class Order {

    // Order Types
    public static final String D_DEPLOY_ORDER = "deploy_order";

    /**
     *  The order type.
     */
    private String d_order_type;

    /**
     * Name of the country associated with the order.
     */
    private String d_country_name;

    /**
     * Number of armies to be used in the order.
     */
    private int d_armies_number;

    /**
     *  Creates a new order with specified type.
     * @param p_order_type The type of order
     */
    public Order(String p_order_type) {
        this.d_order_type = p_order_type;
    }

    /**
     * Sets the name of the country associated with the order.
     * @param p_country_name Returns name of the country.
     */
    public void setCountryName(String p_country_name) {
        this.d_country_name = p_country_name;
    }

    /**
     * Sets number of armies to be used.
     * @param d_armies_number Number of armies.
     */
    public void setArmiesNumber(int d_armies_number) {
        this.d_armies_number = d_armies_number;
    }

    /**
     * Gets name of country associated with the order.
     * @return Name of the country.
     */
    public String getCountryName() {
        return this.d_country_name;
    }

    /**
     * Gets the number of armies used in the order
     * @return Number of armies
     */
    public int getArmiesNumber() {
        return this.d_armies_number;
    }

}
