public class SemCounting {
    private Integer stock;

    public SemCounting(int stock){
        this.stock = stock;
    }

    public void p() throws InterruptedException{
        synchronized (this) {
            while(stock == 0){
                wait();
            }
            stock--;
        }
    }

    public void v(){
        synchronized (this) {
            stock++;
            notifyAll();
        }
    }
}
