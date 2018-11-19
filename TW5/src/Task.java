public class Task implements Runnable {

    private int numberOfThreads;
    private int numberOfTasks;
    private boolean eachPixelIsTask;
    public Task(int numberOfThreads, int numberOfTasks, boolean eachPixelIsTask){
        this.numberOfThreads = numberOfThreads;
        this.numberOfTasks = numberOfTasks;
        this.eachPixelIsTask = eachPixelIsTask;
    }
    @Override
    public void run() {
        new Mandelbrot(numberOfThreads,numberOfTasks,eachPixelIsTask).setVisible(true);
    }
}
