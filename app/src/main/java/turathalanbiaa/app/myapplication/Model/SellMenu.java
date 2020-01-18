package turathalanbiaa.app.myapplication.Model;

import java.sql.Date;
import java.sql.Time;

public class SellMenu {
    private Integer id,user_id,discount,user_finished_id,status;
    private String f1,f2,f3,f4;
    private Date date;
    private Time time;

    public SellMenu(){}
    public SellMenu(Integer id, Integer user_id, Date date,Time time,Integer discount,Integer user_finished_id,
                    Integer status,String f1,String f2,String f3,String f4){

        this.id=id;
        this.user_id=user_id;
        this.date=date;
        this.time=time;
        this.discount=discount;
        this.user_finished_id=user_finished_id;
        this.status=status;
        this.f1=f1;
        this.f2=f2;
        this.f3=f3;
        this.f4=f4;

    }
    public SellMenu(Integer id, Integer user_id, Date date,Time time,Integer discount,Integer user_finished_id,
                    Integer status){

        this.id=id;
        this.user_id=user_id;
        this.date=date;
        this.time=time;
        this.discount=discount;
        this.user_finished_id=user_finished_id;
        this.status=status;


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getUser_finished_id() {
        return user_finished_id;
    }

    public void setUser_finished_id(Integer user_finished_id) {
        this.user_finished_id = user_finished_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
