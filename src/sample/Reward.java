package sample;

public class Reward {

    public int productid;
    public String productName;
    public int cost;
    public int price;

    public Reward(){

    }

    public Reward(int productid, String productName, int cost, int price) {
        this.productid = productid;
        this.productName = productName;
        this.cost = cost;
        this.price = price;
    }

    public int getProductid() {
        return productid;
    }

    public String getProductName() {
        return productName;
    }

    public int getCost() {
        return cost;
    }

    public String toString(){
        return this.productName + ", " + this.cost + " points";
    }
}
