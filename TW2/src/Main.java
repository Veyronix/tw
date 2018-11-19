public class Main {

    public static void main(String[] args) throws InterruptedException {
//        Buffer buffer = new Buffer(2);
//
//        int amountOfProAndCon = 3;
//        Thread[] producers = new Thread[amountOfProAndCon];
//        Thread[] consumers = new Thread[amountOfProAndCon];

//        for(int i = 0; i < amountOfProAndCon; i ++){
//            producers[i] = new Thread(new Producer(buffer));
//        }
//        for(int i = 0; i < amountOfProAndCon; i ++){
//            consumers[i] = new Thread(new Consumer(buffer));
//        }
//
//        for(int i = 0; i < amountOfProAndCon; i ++){
//            producers[i].start();
//            consumers[i].start();
//        }
//
//        for(int i = 0; i < amountOfProAndCon; i ++){
//            producers[i].join();
//            consumers[i].join();
//        }
//
//        System.out.print(buffer);
        int amountOfClients = 10;
        int maxTrolleys = 4;
        int maxClients = 6;
        Shop shop = new Shop(maxTrolleys, maxClients);
        Thread[] clients = new Thread[amountOfClients];
        for (int i = 0; i < amountOfClients; i++) {
            clients[i] = new Thread(new Client(shop));
        }
        for (int i = 0; i < amountOfClients; i++) {
            clients[i].start();
        }
        for (int i = 0; i < amountOfClients; i++) {
            clients[i].join();
        }

    }
}



