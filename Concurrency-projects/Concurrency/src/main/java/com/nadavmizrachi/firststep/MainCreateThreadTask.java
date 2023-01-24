package com.nadavmizrachi.firststep;

import static com.nadavmizrachi.firststep.ThreadColor.*;

/**
 * Demonstration how use thread for execute tasks.
 * The recommended way is using the Runnable interface.
 */
public class MainCreateThreadTask {
    public static void main(String[] args) {
        print("Hello from main thread!");

        // Here we use Class that extends 'Thread' class and overrides the 'run' method.
        Thread anotherThread = new AnotherThread();
        anotherThread.setName(" == ANOTHER_THREAD ==");
        anotherThread.start();
        //anotherThread.run();  // --> Mistake! This will be executed on main thread rather than new thread.

        // Here we use anonymous definition in order to create override to run method.
        new Thread() {
            @Override
            public void run() {
                print("Hello from anonymous thread! Thread name : " + currentThread().getName() + ". Starts to wait for AnotherThread");
                try {
                    // Current thread will continue running when 'anotherThread' will finish his .run()
                    anotherThread.join();
                    print("From anonymous.. AnotherThread was terminated. So now, I can continue :)");
                    return;
                } catch (InterruptedException ex) {
                    print("While I was waiting for AnotherThread,");
                }
            }

            private void print(String strToPrint) {
                System.out.println(ANSI_RED + strToPrint);
            }
        }.start();


        // Here we use Runnable interface by implementing the 'run' function
        Thread runnable = new Thread(new MyRunnable());
        runnable.start();

        print("Hello again from main thread!");
    }

    private static void print(String strToPrint) {
        System.out.println(ANSI_PURPLE + strToPrint);
    }
}
