package game.maingame.beings;

public class Being {
    public int owner;
    public int priority;

    public Being(){
        owner = 1;
        priority = 10;
    }

    public Being(Being another){
        this.owner = another.owner;
        this.priority = another.priority;
    }
}
