package sample;

public class Reward {

    public int productid;
    public String productname;
    public int cost;

    public Reward(){

    }

    public String toString(){
        return this.productname + ", " + this.cost + " points";
    }
}
