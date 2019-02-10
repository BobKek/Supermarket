package sample.Model.objects;

import java.util.ArrayList;

public class Order {
    private int orderid;
    private String date;
    private int employeeid = 1;
    private int customerid;
    private ArrayList<OrderProduct> orderProductsArrayList = new ArrayList<>();

    public Order() {

    }

    public Order(int employeeid, int customerid, ArrayList<OrderProduct> orderProductsArrayList) {
        this.employeeid = employeeid;
        this.customerid = customerid;
        this.orderProductsArrayList = orderProductsArrayList;
    }

    public Order(int orderid, String date, int employeeid, int customerid) {
        this.orderid = orderid;
        this.date = date;
        this.employeeid = employeeid;
        this.customerid = customerid;
    }

    public Order(int employeeid, int customerid) {
        this.employeeid = employeeid;
        this.customerid = customerid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public ArrayList<OrderProduct> getOrderProductsArrayList() {
        return orderProductsArrayList;
    }

    public void setOrderProductsArrayList(ArrayList<OrderProduct> orderProductsArrayList) {
        this.orderProductsArrayList = orderProductsArrayList;
    }
}
