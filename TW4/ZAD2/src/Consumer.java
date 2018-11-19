import java.util.Random;

public class Consumer implements Runnable {

    private int M;
    private IBuffer buffer;
    private Measurement[] measurements;
    private int timesId;
    public Consumer(int M, IBuffer buffer, Measurement[] measurements, int timesId){
        this.M = M;
        this.buffer = buffer;
        this.measurements = measurements;
        this.timesId = timesId;
    }

    public void run(){
        Random r = new Random();
        int low = 1;
        int high = M+1;
        int result = r.nextInt(high-low) + low;
//        System.out.println(Thread.currentThread().getId() + " am going to take " + result + " elements");
        measurements[timesId].M = result;
        double start = System.nanoTime();
        buffer.take(result);
        double end = System.nanoTime();
        measurements[timesId].time = end - start;
//        System.out.println(Thread.currentThread().getId() + " took " + result + " elements");
    }
}