//import org.omg.CORBA.INTERNAL;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;

public class Shop {
    private final int maxTrolleys;
    private Integer maxClients;
    private SemCounting semShop;
    private SemBinary[] semTrolleys;
//    private SemBinary choosingTrolley = new SemBinary(1);

    public Shop(int maxTrolleys,int maxClients) {
        this.maxTrolleys = maxTrolleys;
        this.maxClients = maxClients;
        semShop = new SemCounting(maxClients);
        semTrolleys = new SemBinary[maxTrolleys];
        for (int i = 0; i < maxTrolleys; i++){
            semTrolleys[i] = new SemBinary(1);
        }
    }

    public void shoping() throws InterruptedException{
        semShop.p();
        int chosenTrolley = (int)(Math.random() * ((maxTrolleys)));
        semTrolleys[chosenTrolley].p();
        System.out.println(Thread.currentThread().getId() + " wybralem wozek " + chosenTrolley);
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getId() + " oddaje wozek " + chosenTrolley);
        semTrolleys[chosenTrolley].v();
//        System.out.println("skoncze zakupy");
        semShop.v();
        Thread.sleep(1000);
    }


}

