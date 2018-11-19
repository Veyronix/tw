public class Main {

    public static void main(String[] args)
    {
        for(int i = 0; i < 11; i++) {
            int numberOfThreads = 4;
            int numberOfTasks = 40;
            boolean eachPixelIsTask = true;
            Thread thread = new Thread(new Task(numberOfThreads, numberOfTasks, eachPixelIsTask));
            thread.run();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
