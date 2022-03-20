package turathalanbiaa.app.myapplication.Model;

public class TheMenu {
    private Integer menu_id, customer_id , status, discount;
    private String customer_name;

    public TheMenu() {
    }

    public TheMenu(Integer menu_id,Integer customer_id,Integer status,Integer discount, String customer_name) {
        this.menu_id = menu_id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.status = status;
        this.discount = discount;

    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}


