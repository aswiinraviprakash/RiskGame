package gameplay;

public class Order {

    // Order Types
    public static final String D_DEPLOY_ORDER = "deploy_order";

    private String d_order_type;

    private int d_country_id;

    private int d_armies_number;

    public Order(String p_order_type) {
        this.d_order_type = p_order_type;
    }

    public void setCountryId(int p_country_id) {
        this.d_country_id = p_country_id;
    }

    public void setArmiesNumber(int d_armies_number) {
        this.d_armies_number = d_armies_number;
    }

    public int getCountryId() {
        return this.d_country_id;
    }

    public int getArmiesNumber() {
        return this.d_armies_number;
    }

}
