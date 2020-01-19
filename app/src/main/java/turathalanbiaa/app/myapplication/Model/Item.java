package turathalanbiaa.app.myapplication.Model;

import java.sql.Date;

public class Item {
    private Integer id,type,cost,price,store_id,count;
    private String barcode,name,f1,f2,f3,f4,place;
    private Date detail,last_update;

    public Item(){};
    public Item(Integer id,String barcode,String name,Integer type,Integer cost,Integer price,String f1,String f2,
                String f3,String f4,Date detail,Integer store_id,String place,Date last_update,Integer count){
        this.id=id;
        this.barcode=barcode;
        this.name=name;
        this.type=type;
        this.cost=cost;
        this.price=price;
        this.f1=f1;
        this.f2=f2;
        this.f3=f3;
        this.f4=f4;
        this.detail=detail;
        this.store_id=store_id;
        this.place=place;
        this.last_update=last_update;
        this.count=count;

    }
    public Item(Integer id,String barcode,String name,Integer type,Integer cost,Integer price,
               Date detail,Integer store_id,String place,Date last_update){
        this.id=id;
        this.barcode=barcode;
        this.name=name;
        this.type=type;
        this.cost=cost;
        this.price=price;
        this.detail=detail;
        this.store_id=store_id;
        this.place=place;
        this.last_update=last_update;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDetail() {
        return detail;
    }

    public void setDetail(Date detail) {
        this.detail = detail;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }
}
