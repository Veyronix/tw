public class Client implements Runnable {
    private Shop shop;
    public Client(Shop shop){
        this.shop = shop;
    }
    public void run(){

        for(int i = 0; i < 100; i++){
            try {
                shop.shoping();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
