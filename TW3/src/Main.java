public class Main {

    public static void main(String[] args) throws InterruptedException {
        //zad 1
//        BoundedBuffer buffer = new BoundedBuffer();
//        int amountOfProAndCon = 3;
//        Thread[] producers = new Thread[amountOfProAndCon];
//        Thread[] consumers = new Thread[amountOfProAndCon];
//
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


//        zad2
//        int amountOfUsers = 4;
//        int liczbaDrukarek = 2;
//        Drukarka[] drukarki = new Drukarka[liczbaDrukarek];
//        Monitor_Drukarek monitor_drukarek = new Monitor_Drukarek(drukarki);
//        Thread[] users = new Thread[amountOfUsers];
//        for (int i = 0; i < amountOfUsers; i++) {
//            users[i] = new Thread(new User(monitor_drukarek));
//        }
//        for (int i = 0; i < amountOfUsers; i++) {
//            users[i].start();
//        }
//        for (int i = 0; i < amountOfUsers; i++) {
//            users[i].join();
//        }


        //zad3

        int liczbaPar = 2;
        Thread[] klients = new Thread[liczbaPar * 2];
        Kelner kelner = new Kelner();
        for(int i = 0; i < liczbaPar*2; i = i + 2){
            klients[i] = new Thread(new Klient(i,i+1,kelner));
            klients[i+1] = new Thread(new Klient(i+1,i,kelner));
        }
        for(int i = 0; i < liczbaPar; i++){
            klients[i].start();
            klients[liczbaPar*2 - 1 -i].start();
        }
        for(int i = 0; i < liczbaPar * 2; i++){
            klients[i].join();
        }

    }
}
