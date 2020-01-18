package turathalanbiaa.app.myapplication.Model;

import java.io.Serializable;

public class SellMenuItem implements Serializable {
    private Integer id,sell_menu_id,item_price,item_type,item_count,user_sell_it_id;
    private String item_name,f1,f2,f3,f4;

    public  SellMenuItem(){}
    public SellMenuItem(Integer id,Integer sell_menu_id, String item_name,Integer item_price, Integer item_type,
                        Integer item_count, Integer user_sell_it_id,String f1,String f2, String f3, String f4){

        this.id=id;
        this.sell_menu_id=sell_menu_id;
        this.item_name=item_name;
        this.item_price=item_price;
        this.item_type=item_type;
        this.item_count=item_count;
        this.user_sell_it_id=user_sell_it_id;
        this.f1=f1;
        this.f2=f2;
        this.f3=f3;
        this.f4=f4;
    }
    public SellMenuItem(Integer id,Integer sell_menu_id, String item_name,Integer item_price, Integer item_type,
                        Integer item_count, Integer user_sell_it_id){

        this.id=id;
        this.sell_menu_id=sell_menu_id;
        this.item_name=item_name;
        this.item_price=item_price;
        this.item_type=item_type;
        this.item_count=item_count;
        this.user_sell_it_id=user_sell_it_id;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSell_menu_id() {
        return sell_menu_id;
    }

    public void setSell_menu_id(Integer sell_menu_id) {
        this.sell_menu_id = sell_menu_id;
    }

    public Integer getItem_price() {
        return item_price;
    }

    public void setItem_price(Integer item_price) {
        this.item_price = item_price;
    }

    public Integer getItem_type() {
        return item_type;
    }

    public void setItem_type(Integer item_type) {
        this.item_type = item_type;
    }

    public Integer getItem_count() {
        return item_count;
    }

    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }

    public Integer getUser_sell_it_id() {
        return user_sell_it_id;
    }

    public void setUser_sell_it_id(Integer user_sell_it_id) {
        this.user_sell_it_id = user_sell_it_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    public String getF4() {
        return f4;
    }

    public void setF4(String f4) {
        this.f4 = f4;
    }
}
