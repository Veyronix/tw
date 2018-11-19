import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition ifProductIsReady  = lock.newCondition();
        Condition ifProductIsReady2  = lock.newCondition();
        int numberOfConverters = 5;
        int sizeOfBuffer = 100;
        Thread[] converters = new Thread[numberOfConverters];
        Buffer buffer = new Buffer(sizeOfBuffer);
        Thread consumer = new Thread(new Consumer(lock,ifProductIsReady,buffer));
        consumer.start();
        Thread producer = new Thread(new Producer(lock,ifProductIsReady2,buffer));
        producer.start();
        WorkPosition[] workPositions = new WorkPosition[numberOfConverters+1];
        Lock lock1 = new ReentrantLock();
        Condition lock2 = lock1.newCondition();
        WorkPosition emptyWorkPositions = new WorkPosition(new Integer(sizeOfBuffer+2),lock1,lock2);
        for(int i = 0;i < numberOfConverters; i++){
            Lock tmpLock = new ReentrantLock();
            Condition tmpCondition = tmpLock.newCondition();
            workPositions[i] = new WorkPosition(new Integer(0),tmpLock,tmpCondition);
        }
        converters[0] = new Thread(new Converter(buffer,workPositions[0],emptyWorkPositions,0));
        for(int i = 1; i < numberOfConverters; i++){
            converters[i] = new Thread(new Converter(buffer,workPositions[i],workPositions[i-1],i));
        }
        converters[0].start();
        for(int i = 1; i < numberOfConverters; i++){
            converters[i].start();
        }
        try {
            for(int i = 0; i < numberOfConverters; i++){
                converters[i].join();
            }
            lock.lock();
            ifProductIsReady2.signal();
            lock.unlock();
            producer.join();
            lock.lock();
            ifProductIsReady.signal();
            lock.unlock();
            consumer.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }



    }
}
