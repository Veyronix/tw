import java.util.Random;

public class Producer implements Runnable {

    private int M;
    private IBuffer buffer;
    private Measurement[] measurements;
    private int timesId;
    public Producer(int M, IBuffer buffer, Measurement[] measurements, int timesId){
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
        measurements[timesId].M = result;
//        System.out.println(Thread.currentThread().getId() + " am going to put " + result + " elements");
        double start = System.nanoTime();
        buffer.put(result);
        double end = System.nanoTime();
        measurements[timesId].time = end - start;
//        System.out.println(Thread.currentThread().getId() + " put " + result + " elements");
    }
}
