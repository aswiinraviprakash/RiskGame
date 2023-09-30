package gameplay;

public class Order {

    // Order Types
    public static final String D_DEPLOY_ORDER = "deploy_order";

    private String d_order_type;

    private String d_country_name;

    private int d_armies_number;

    public Order(String p_order_type) {
        this.d_order_type = p_order_type;
    }

    public void setCountryName(String p_country_name) {
        this.d_country_name = p_country_name;
    }

    public void setArmiesNumber(int d_armies_number) {
        this.d_armies_number = d_armies_number;
    }

    public String getCountryName() {
        return this.d_country_name;
    }

    public int getArmiesNumber() {
        return this.d_armies_number;
    }

}
