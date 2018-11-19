import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER = 2000;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;

    public Mandelbrot(int numberOfThread, int numberOfTasks,boolean eachPixelIsTask) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThread);
        List<Callable<String>> threads = new ArrayList<Callable<String>>();
        if(eachPixelIsTask){
            for(int i = 0; i < getWidth(); i++){
                for(int j = 0; j < getHeight(); j++){
                    threads.add(
                            new countPart(i,
                                    (i + 1),
                                    j,
                                    j+1,
                                    I));
                }
            }
        }else {
            for (int i = 0; i < numberOfTasks; i++) {
                threads.add(
                        new countPart(i * (getWidth() / (numberOfTasks)),
                                (i + 1) * (getWidth() / (numberOfTasks)),
                                0,
                                getHeight(),
                                I));
            }
        }
        try {
            long start = System.nanoTime();
            pool.invokeAll(threads);
            long end = System.nanoTime() - start;
            System.out.println(end);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public class countPart implements Callable<String> {
        private int x1,x2,y1,y2;
        private BufferedImage I;
        public countPart(int x1,int x2,int y1,int y2,BufferedImage I){
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.I = I;
        }

        @Override
        public String call() {
            for (int y = y1; y < y2 ; y++) {
                for (int x = x1; x < x2; x++) {
                    zx = zy = 0;
                    cX = (x - 400) / ZOOM;
                    cY = (y - 300) / ZOOM;
                    int iter = MAX_ITER;
                    while (zx * zx + zy * zy < 4 && iter > 0) {
                        tmp = zx * zx - zy * zy + cX;
                        zy = 2.0 * zx * zy + cY;
                        zx = tmp;
                        iter--;
                    }
                    I.setRGB(x, y, iter | (iter << 8));
                }
            }
            return "";
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}