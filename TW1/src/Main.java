

public class Main {

    public static void main(String[] args) throws InterruptedException {

//        Counter counter = new Counter(0);
//        IncThread incThread = new IncThread(counter);
//        DecThread decThread = new DecThread(counter);
//        incThread.start();
//        decThread.start();
//
//        incThread.join();
//        decThread.join();
//        System.out.println(counter.integer);

        Buffer buffer = new Buffer(2);

        int amountOfProAndCon = 3;
        Thread[] producers = new Thread[amountOfProAndCon];
        Thread[] consumers = new Thread[amountOfProAndCon];

        for(int i = 0; i < amountOfProAndCon; i ++){
            producers[i] = new Thread(new Producer(buffer));
        }
        for(int i = 0; i < amountOfProAndCon; i ++){
            consumers[i] = new Thread(new Consumer(buffer));
        }

        for(int i = 0; i < amountOfProAndCon; i ++){
            producers[i].start();
            consumers[i].start();
        }

        for(int i = 0; i < amountOfProAndCon; i ++){
            producers[i].join();
            consumers[i].join();
        }

        System.out.print(buffer);

    }
}


