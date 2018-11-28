public class Main {

    public static void main(String[] args)
    {
        for(int i = 0; i < 11; i++) {
            int numberOfThreads = 4;
            int numberOfTasks = 4;
            boolean eachPixelIsTask = false;
            new Mandelbrot(numberOfThreads,numberOfTasks,eachPixelIsTask).setVisible(true);
        }
    }
}
