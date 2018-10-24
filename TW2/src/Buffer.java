
public class Buffer {
    private String[] buffer;
    private int amount = 0;

    public Buffer(int buffLen) {
        this.buffer = new String[buffLen];
    }

    private SemBinary semBinary = new SemBinary(1);

    private boolean isFull() {
        return amount == buffer.length;
    }

    private boolean isEmpty() {
        return amount == 0;
    }

    public void put(String string) throws InterruptedException {
        //while(true) {
        semBinary.p();
//        System.out.println("put");
        if (!isFull()) {
            buffer[amount] = string;
            amount++;
            semBinary.v();
            return;
        } else {
            semBinary.v();
        }
//            semBinary.v();
        //}
    }

    public String take() throws InterruptedException {
        //while (true) {
        semBinary.p();
//        System.out.println("take");
        if (!isEmpty()) {
            amount--;
            semBinary.v();
            return buffer[amount];
            } else {
            semBinary.v();
            return "nic";
        }
    }
//            semBinary.v();
            //}
}




