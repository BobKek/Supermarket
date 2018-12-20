package sample;

public class Branch {
    public int branchid;
    public String country;
    public String city;

    public Branch(int id , String city, String country){
        this.branchid = id;
        this.city = city;
        this.country = country;
    }

    public String toString(){
        return this.city + ", " + this.country;
    }
}
