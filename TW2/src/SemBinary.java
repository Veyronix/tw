public class SemBinary {

    private Boolean status;
    public SemBinary(int i){
        this.status = new Boolean(true);
    }

    public void p() throws InterruptedException{
        synchronized (this) {
            System.out.println(Thread.currentThread().getId() + " " + status);
            while(status == false){
                wait();
            }
            status = false;
        }
    }

    public void v(){
        synchronized (this) {
            status = true;
            notifyAll();
        }
    }
}

