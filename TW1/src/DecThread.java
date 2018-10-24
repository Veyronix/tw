class DecThread extends Thread {

    public Counter counter;

    public DecThread(Counter counter){
        this.counter = counter;
    }
    public void run() {
        for(int i = 0;i < 100000000;i++)
            synchronized (counter) {counter.decrement();}
    }
}