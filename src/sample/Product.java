package sample;

public class Product {
    public int productid;
    public String name;
    public String description;
    public int price;
    public int categoryid;

    public Product(){

    }

    public Product(int productid, String name, int price, String description, int categoryid) {
        this.productid = productid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryid = categoryid;
    }

    public String toString(){
        return this.name + ", $" + this.price;
    }
}
