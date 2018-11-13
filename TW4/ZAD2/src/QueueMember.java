import java.util.concurrent.locks.Condition;

public class QueueMember {
    public int result;
    public Condition condition;

    public QueueMember( int result, Condition condition){
        this.result = result;
        this.condition = condition;
    }
}
