import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Kelner {
    private final Lock lock = new ReentrantLock();
    private final Condition czekamNaPartnera  = lock.newCondition();
    private final Condition czekamNaWolnyStol  = lock.newCondition();
    private int ileOsobPrzyStole = 0;
    private int[] stolik = new int[2];
    private ArrayList<Integer> czekajacyKlienci = new ArrayList<Integer>();
    public Kelner(){
        stolik[0] = -1;
        stolik[1] = -1;
    }

    public void chceStolik(Klient klient){
        lock.lock();
        try {
            if(czekajacyKlienci.contains(klient.idPartnera)){
                while(ileOsobPrzyStole != 0){
                    czekamNaWolnyStol.await();
                }
                ileOsobPrzyStole++;
                stolik[0] = klient.idKlienta;
                czekamNaPartnera.signal();
                System.out.println(klient.idKlienta + " siada przy stole");
            }
            else{
                czekajacyKlienci.add(klient.idKlienta);
                while (stolik[0] != klient.idPartnera){
                    czekamNaPartnera.await();
                }
                ileOsobPrzyStole++;
                stolik[1] = klient.idKlienta;
                System.out.println(klient.idKlienta + " siada przy stole");
            }
        }catch (InterruptedException e ){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }
    public void zwalniam()
    {
        lock.lock();
        try{
            ileOsobPrzyStole--;
            if(ileOsobPrzyStole == 0){
                czekamNaWolnyStol.signal();
            }
        }
        finally {
            lock.unlock();
        }
    }
}
