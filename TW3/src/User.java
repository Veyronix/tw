public class User implements Runnable {
    private Monitor_Drukarek monitor_drukarek;
    public User(Monitor_Drukarek monitor_drukarek){
        this.monitor_drukarek = monitor_drukarek;
    }
    public void run() {
        for(int i = 0; i < 100; i++){
            int nr_drukarki = monitor_drukarek.rezerwuj();
            System.out.println(Thread.currentThread().getId() + " uzywam drukarki " + nr_drukarki);
            try {

                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getId() + " oddaje drukarke " + nr_drukarki);
                monitor_drukarek.zwolnij(nr_drukarki);
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
