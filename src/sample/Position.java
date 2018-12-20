package sample;

public class Position {
    public int positionid;
    public String position_name;

    public Position(int id , String name){
        this.positionid = id;
        this.position_name = name;
    }

    public String toString(){
        return this.position_name;
    }
}
