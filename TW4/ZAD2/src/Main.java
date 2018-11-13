public class Main {

    public static void main(String[] args) {

        int numberOfProducers = 1000;
        int numberOfConsumers = 1000;
        int M = 100000;
        Thread[] producers = new Thread[numberOfProducers];
        Thread[] consumers = new Thread[numberOfConsumers];
//        IBuffer buffer = new NaiveBuffer(M);
        IBuffer buffer = new FairBuffer(M);
        Measurement[] measurements = new Measurement[numberOfConsumers+numberOfProducers];
        for(int i = 0; i < numberOfConsumers + numberOfProducers; i++){
            measurements[i] = new Measurement(-1.0,-1);
        }
        for(int i = 0; i < numberOfConsumers; i++){
            consumers[i] = new Thread(new Consumer(M,buffer,measurements,i));
        }

        for(int i = 0; i < numberOfProducers; i++){
            producers[i] = new Thread(new Producer(M,buffer,measurements,i+numberOfConsumers));
        }

        for(int i = 0; i < numberOfConsumers;i++){
            consumers[i].start();
            producers[i].start();
        }

        try {
            for (int i = 0; i < numberOfConsumers; i++) {
                consumers[i].join(2000);
//                consumers[i].stop();
            }
            for (int i = 0; i < numberOfProducers; i++) {
                producers[i].join(2000);
//                producers[i].stop();

            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        for(int i = 0; i < numberOfConsumers + numberOfProducers; i++){
            if(i == 0){
                System.out.println("Consumer");
            }
            else if(i == numberOfConsumers){
                System.out.println("Producer");
            }
            System.out.print(measurements[i].M + "," + measurements[i].time);
            System.out.println();
        }
    }
}
