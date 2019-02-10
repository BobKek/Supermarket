package sample.Model.objects;

import sample.Model.Model;

public class Customer extends Person {
    private int customerid;
    private int points;

    public Customer(String name, String username, String password, String phone, String email, int customerid, int points) {
        super(name, username, password, phone, email);
        this.customerid = customerid;
        this.points = points;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    Model model = Model.getInstanceOfModel();

    public void buyProduct(int productid, int quantity) {
        model.buyProduct(customerid, productid, quantity);
    }
}
