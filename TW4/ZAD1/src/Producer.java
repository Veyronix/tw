import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Producer implements Runnable {

    private Lock lock;
    private Condition ifProductIsReady;
    public Producer(Lock lock, Condition condition, Buffer buffer)
    {
        this.lock = lock;
        this.ifProductIsReady = condition;
    }

    public void run() {

        System.out.println("I am Producer and I am producing product.");
        try {
            lock.lock();
            ifProductIsReady.await();
            lock.unlock();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("I am Producer and I producted product.");

    }

}