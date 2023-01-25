import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
//      createThreadsManually();
        createThreadsInExecutorService();
    }

    private static void createThreadsManually() {
        ArrayList<String> buffer = new ArrayList<>();

        ReentrantLock bufferLocker = new ReentrantLock();

        new Thread(new Producer(buffer, ThreadColor.ANSI_GREEN, bufferLocker)).start();
        new Thread(new Consumer(buffer, ThreadColor.ANSI_BLUE, bufferLocker)).start();
        new Thread(new Consumer(buffer, ThreadColor.ANSI_RED, bufferLocker)).start();
    }

    private static void createThreadsInExecutorService() {
        ArrayList<String> buffer = new ArrayList<>();

        ReentrantLock bufferLocker = new ReentrantLock();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(new Producer(buffer, ThreadColor.ANSI_GREEN, bufferLocker));
        executorService.execute(new Consumer(buffer, ThreadColor.ANSI_BLUE, bufferLocker));
        executorService.execute(new Consumer(buffer, ThreadColor.ANSI_RED, bufferLocker));

        Future<String> futureString = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(ThreadColor.ANSI_PURPLE + "Hello there... I'm speaking from future code :) ...");
                return "FUTURE STRING !";
            }
        });

        try {
            System.out.println(futureString.get());
        } catch (ExecutionException ex) {
            System.out.println("There was an ExecutionException!");
        } catch (InterruptedException ex) {
            System.out.println("The thread that was executing the callable class was interrupted!");
        }
        executorService.shutdown();


    }
}
