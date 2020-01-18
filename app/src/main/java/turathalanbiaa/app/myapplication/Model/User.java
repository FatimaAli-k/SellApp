package turathalanbiaa.app.myapplication.Model;

public class User {
    private Integer id,type;
    private String name,secret_word,phone,f1,f2,f3,f4;

    public User(){}
    public User(Integer id, String name, Integer type, String secret_word, String phone,String f1, String f2, String f3, String f4){
        this.id=id;
        this.name=name;
        this.type=type;
        this.secret_word=secret_word;
        this.phone=phone;
        this.f1=f1;
        this.f2=f2;
        this.f3=f3;
        this.f4=f4;
    }
    public User(Integer id, String name, Integer type, String secret_word, String phone){
        this.id=id;
        this.name=name;
        this.type=type;
        this.secret_word=secret_word;
        this.phone=phone;

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

    public String getSecret_word() {
        return secret_word;
    }

    public void setSecret_word(String secret_word) {
        this.secret_word = secret_word;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
