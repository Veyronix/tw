import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer implements IBuffer{
    Lock lock = new ReentrantLock();
    Condition needMoreFreeElements = lock.newCondition();
    Condition needMoreAddedElements = lock.newCondition();
    private int sizeOfBuffer;
    private int actualTail = 0;

    public NaiveBuffer(int M){
        this.sizeOfBuffer = 2*M;
    }


    public void put(int numberOfElements){
        lock.lock();
        try{
            while(sizeOfBuffer - actualTail < numberOfElements){
                needMoreFreeElements.await();
            }
            actualTail += numberOfElements;
            needMoreAddedElements.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public void take(int numberOfElements){
        lock.lock();
        try{
            while(actualTail < numberOfElements){
                needMoreAddedElements.await();
            }
            actualTail -= numberOfElements;
            needMoreFreeElements.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }

    }

}
