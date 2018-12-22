package sample;

public class Product {
    public int productid;
    public String name;
    public String description;
    public int price;
    public int categoryid;
    public String categoryName;

    public Product(){

    }

    public Product(int productid, String name, int price, String description, int categoryid, String categoryName) {
        this.productid = productid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryid = categoryid;
        this.categoryName = categoryName;
    }

    public Product(int productid, String name, int price, String description, int categoryid) {
        this.productid = productid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryid = categoryid;
    }

    public int getProductid() {
        return productid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String toString(){
        return this.name + ", $" + this.price;
    }
}