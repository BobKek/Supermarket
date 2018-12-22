package sample;

public class Supplier {

    public int supplierid;
    public String name;
    public String phone;

    public Supplier(){

    }

    public Supplier(int supplierid, String name, String phone) {
        this.supplierid = supplierid;
        this.name = name;
        this.phone = phone;
    }

    public int getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(int supplierid) {
        this.supplierid = supplierid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString(){
        return this.name;
    }

}
