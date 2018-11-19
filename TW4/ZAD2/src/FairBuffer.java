import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairBuffer implements IBuffer{
    private boolean iCanDoSth = true;
    private Lock firstLock = new ReentrantLock();
    private Condition firstCondition = firstLock.newCondition();

    private Lock lock = new ReentrantLock();
    private int sizeOfBuffer;
    private int actualTail = 0;
    private ArrayList<QueueMember> producersArrayList = new ArrayList<QueueMember>();
    private ArrayList<QueueMember> consumersArrayList = new ArrayList<QueueMember>();

    public FairBuffer(int M){
        this.sizeOfBuffer = 2*M;
    }

    private void passFirstLock(){
        try{
            firstLock.lock();
            while(!iCanDoSth){
                firstCondition.await();
            }
            iCanDoSth = false;
            lock.lock();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            firstLock.unlock();
        }
    }
    public void put(int numberOfElements){
        passFirstLock();
        System.out.println(Thread.currentThread().getId() + " am going to put " + numberOfElements + " elements");
        if(sizeOfBuffer - actualTail < numberOfElements){
            System.out.println(Thread.currentThread().getId() + " cant put elements :(");
            joinProducersQueue(numberOfElements);
        }
        actualTail += numberOfElements;
        System.out.println(Thread.currentThread().getId() + " put " + numberOfElements + " elements");
        System.out.println("Not free spaces: " + actualTail);
        wakeConsumerFairly();
        lock.unlock();
    }

    public void take(int numberOfElements){
        passFirstLock();
        System.out.println(Thread.currentThread().getId() + " am going to take " + numberOfElements + " elements");
        if(actualTail < numberOfElements){
            joinConsumersQueue(numberOfElements);
        }
        actualTail -= numberOfElements;
        System.out.println(Thread.currentThread().getId() + " took " + numberOfElements + " elements");
        System.out.println("Not free spaces: " + actualTail);
        wakeProducerFairly();
        lock.unlock();
    }

    private void joinProducersQueue(int numberOfElements){
        try {
            Condition tmp = lock.newCondition();
            producersArrayList.add(new QueueMember(numberOfElements,
                    tmp));
            System.out.println(Thread.currentThread().getId() + " am going to queue producer");
            firstLock.lock();
            iCanDoSth = true;
            firstCondition.signal();
            firstLock.unlock();
            tmp.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void joinConsumersQueue(int numberOfElements){
        try {
            Condition tmp = lock.newCondition();
            consumersArrayList.add(new QueueMember(numberOfElements,
                    tmp));
            System.out.println(Thread.currentThread().getId() + " in queue consumer");
            firstLock.lock();
            iCanDoSth = true;
            firstCondition.signal();
            firstLock.unlock();
            tmp.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void wakeProducerFairly(){
        if(producersArrayList.size() != 0) {
            Random r = new Random();
            int low = 0;
            int high = producersArrayList.size();
            int result = r.nextInt(high - low) + low;
            if (producersArrayList.get(result).result <= sizeOfBuffer - actualTail) {
                producersArrayList.get(result).condition.signal();
                System.out.println(Thread.currentThread().getId() + " woken up producer " + result);
                producersArrayList.remove(producersArrayList.get(result));
                return;
            } else {
                for (int i = (result + 1) % producersArrayList.size(); i != result; i = (i + 1) % producersArrayList.size()) {
                    if (producersArrayList.get(i).result <= sizeOfBuffer - actualTail) {
                        producersArrayList.get(i).condition.signal();
                        System.out.println(Thread.currentThread().getId() + " woken up producer " + i);
                        producersArrayList.remove(producersArrayList.get(i));
                        return;
                    }
                }
            }
        }
        System.out.println(Thread.currentThread().getId() + " didnt wake up anyone");
        firstLock.lock();
        iCanDoSth = true;
        firstCondition.signal();
        firstLock.unlock();
    }

    private void wakeConsumerFairly(){
        if(consumersArrayList.size() != 0) {
            Random r = new Random();
            int low = 0;
            int high = consumersArrayList.size();
            int result = r.nextInt(high - low) + low;
            if (consumersArrayList.get(result).result <= actualTail) {
                consumersArrayList.get(result).condition.signal();
                System.out.println(Thread.currentThread().getId() + " woken up consumer " + result);
                consumersArrayList.remove(consumersArrayList.get(result));
            return;
            } else {
                for (int i = (result + 1) % consumersArrayList.size(); i != result; i = (i + 1) % consumersArrayList.size()) {
                    if (consumersArrayList.get(i).result <= actualTail) {
                        consumersArrayList.get(i).condition.signal();
                        System.out.println(Thread.currentThread().getId() + " woken up consumer " + i);
                        consumersArrayList.remove(consumersArrayList.get(i));
                        return;
                    }
                }
            }
        }
        System.out.println(Thread.currentThread().getId() + " didnt wake up anyone");
        firstLock.lock();
        iCanDoSth = true;
        firstCondition.signal();
        firstLock.unlock();
    }

}
