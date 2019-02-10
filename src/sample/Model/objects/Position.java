package sample.Model.objects;

public class Position {
    public int positionid;
    public String name;

    public Position(int id , String name){
        this.positionid = id;
        this.name = name;
    }

    public String toString(){
        return this.name;
    }
}
