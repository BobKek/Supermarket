package sample.Model.objects;

public class Category {

    public int categoryid;
    public String name;

    public Category(String name){
        this.name = name;
    }
    public Category(int id , String name){
        this.categoryid = id;
        this.name = name;
    }

    public String toString(){
        return this.name;
    }

}
