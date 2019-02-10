package sample.Model.objects;

public class Reward {

    public int productid;
    public String productName;
    public int points;
    public int price;

    public Reward(){

    }

    public Reward(int productid, String productName, int cost, int price) {
        this.productid = productid;
        this.productName = productName;
        this.points = cost;
        this.price = price;
    }

    public int getProductid() {
        return productid;
    }

    public String getProductName() {
        return productName;
    }

    public int getPoints() {
        return points;
    }

    public String toString(){
        return this.productName + ", " + this.points + " points";
    }

    public int getPrice() {
        return price;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
