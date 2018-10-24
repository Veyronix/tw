public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 100;   i++) {
            try{
                String message = buffer.take();
                System.out.print(message);
            }catch (InterruptedException e){}
        }

    }
}
