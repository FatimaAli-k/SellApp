package turathalanbiaa.app.myapplication.Model;

public class Customer {
    private Integer id,type;
    private String name,note,f1,f2,f3,f4;

    public Customer(){}
    public Customer(Integer id,String name,String note,Integer type,String f1, String f2, String f3, String f4){

        this.id=id;
        this.name=name;
        this.note=note;
        this.type=type;
        this.f1=f1;
        this.f2=f2;
        this.f3=f3;
        this.f4=f4;
    }

    public Customer(Integer id,String name,String note,Integer type){

        this.id=id;
        this.name=name;
        this.note=note;
        this.type=type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
