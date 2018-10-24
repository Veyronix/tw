public class Klient implements Runnable{
    public int idKlienta;
    public int idPartnera;
    private Kelner kelner;
    public Klient(int idKlienta, int idParnera,Kelner kelner){
        this.idKlienta = idKlienta;
        this.idPartnera = idParnera;
        this.kelner = kelner;
    }

    public void run() {
        for(int i = 0; i < 100; i++){
            System.out.println(this.idKlienta + " chce zajac stoł");
            kelner.chceStolik(this);
            try {
                Thread.sleep(100);
                System.out.println(this.idKlienta + " zwalnia stoł");
                kelner.zwalniam();
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}
