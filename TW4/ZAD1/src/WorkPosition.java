import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class WorkPosition {

    private Integer position;
    private Lock positionLock;
    private Condition positionCondition;
    public WorkPosition(
            Integer position,
            Lock positionLock,
            Condition positionCondition
    ){
        this.position = position;
        this.positionLock = positionLock;
        this.positionCondition = positionCondition;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int newPosition){
        this.position = newPosition;
    }
    public void lockPosition(){
        positionLock.lock();
    }

    public void unlockPosition(){
        positionLock.unlock();
    }

    public Condition getPositionCondition(){
        return positionCondition;
    }

    public void signal(){
        positionCondition.signal();
    }
}
