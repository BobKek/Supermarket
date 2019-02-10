package sample.Model.objects;

public class Product {
    public int productid;
    public String name;
    public String description;
    public float price;
    public int categoryid;
    public String categoryName;

    public Product(){

    }

    public Product(int productid, String name, float price, String description, int categoryid, String categoryName) {
        this.productid = productid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryid = categoryid;
        this.categoryName = categoryName;
    }

    public Product(int productid, String name, float price, String description, int categoryid) {
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

    public float getPrice() {
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

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
