import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

    private Lock lock;
    private Condition ifProductIsReady;
    private Buffer buffer;
    public Consumer(Lock lock, Condition condition, Buffer buffer)
    {
        this.lock = lock;
        this.ifProductIsReady = condition;
        this.buffer = buffer;
    }

    public void run() {

        System.out.println("I am Consumer and I am waiting for product.");
        try {
            lock.lock();
            ifProductIsReady.await();
            lock.unlock();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("I am Consumer and I received product.");

    }

}