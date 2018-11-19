

public class Converter implements Runnable {
    private Buffer buffer;
    private WorkPosition myPosition;
    private WorkPosition precedingConverterPosition;
    private int id;
    public Converter(Buffer buffer,
                     WorkPosition myPosition,
                     WorkPosition precedingConverterPosition,
                     int id)
    {
        this.id = id;
        this.buffer = buffer;
        this.myPosition = myPosition;
        this.precedingConverterPosition = precedingConverterPosition;
    }

    public void run() {

        try {
            for (int i = 0; i < buffer.bufferSize; i++) {
                precedingConverterPosition.lockPosition();
                while ( precedingConverterPosition.getPosition() == i) {
                    precedingConverterPosition.getPositionCondition().await();
                }
                myPosition.lockPosition();
                if(i+1 == buffer.bufferSize) {
                    myPosition.setPosition(i+3);
                }
                else{
                    myPosition.setPosition(i);
                }
                System.out.println(this.id + " pracuje teraz na komorce nr " + i);
                myPosition.signal();
                myPosition.unlockPosition();
                precedingConverterPosition.unlockPosition();
            }

            System.out.println(this.id + " skonczylem prace.");
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}