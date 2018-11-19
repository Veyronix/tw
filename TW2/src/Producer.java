public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 100;   i++) {
            try {
                buffer.put("message " + i);
            }catch (InterruptedException e){}
        }

    }
}