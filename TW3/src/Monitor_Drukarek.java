import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor_Drukarek {
    private Drukarka[] drukarki;
    boolean jestWolnaDrukarka = true;

    private boolean[] dostepnoscDrukarek;
    final Lock lock = new ReentrantLock();
    final Condition nieMaDrukarek  = lock.newCondition();

    public Monitor_Drukarek(Drukarka[] drukarki){
        this.drukarki = drukarki;
        this.dostepnoscDrukarek = new boolean[drukarki.length];
        for(int i = 0;i < drukarki.length;i++){
            this.dostepnoscDrukarek[i] = true;
        }
    }

    public int rezerwuj(){
        lock.lock();
        int nr_drukarki = -1;
        try {
            while(jestWolnaDrukarka == false){
                nieMaDrukarek.await();
            }
            nr_drukarki = pierwszaWolnaDrukarka();
            dostepnoscDrukarek[nr_drukarki] = false;
            if(pierwszaWolnaDrukarka() == -1) {
                jestWolnaDrukarka = false;
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return nr_drukarki;
    }
    public void zwolnij(int nr_drukarki){
        lock.lock();
        try {
            dostepnoscDrukarek[nr_drukarki] = true;
            jestWolnaDrukarka = true;
            nieMaDrukarek.signal();
        }finally {
            lock.unlock();
        }
    }

    public int pierwszaWolnaDrukarka(){
        for(int i = 0; i < drukarki.length;i++){
            if(dostepnoscDrukarek[i] == true) {
                return i;
            }
        }
        return -1;
    }

}
