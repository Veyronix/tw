import java.util.List;

public class Buffer {
    String[] buffer;
    int amount = 0;
    public Buffer(int buffLen){
        this.buffer = new String[buffLen];
    }

    private boolean isFull(){
        return amount == buffer.length;
    }

    private boolean isEmpty(){
        return amount == 0;
    }

    synchronized public void put(String string){
        while(true) {
                if (!isFull()) {
                    buffer[amount] = string;
                    amount++;
                    notifyAll();
                    return;
                }
                else{
                    try {
                        wait();
                    } catch (InterruptedException e) {}
                }
            }
    }

    synchronized public String take(){
        while (true) {
                if (!isEmpty()) {
                    amount--;
                    notifyAll();
                    return buffer[amount];
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {}
                }
            }
    }


}
