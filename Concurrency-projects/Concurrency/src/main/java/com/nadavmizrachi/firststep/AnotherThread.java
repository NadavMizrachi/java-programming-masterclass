package com.nadavmizrachi.firststep;

import static com.nadavmizrachi.firststep.ThreadColor.ANSI_GREEN;

public class AnotherThread extends Thread {
    @Override
    public void run() {
        print("Hello from AnotherThread class. Thread name : " + currentThread().getName() + " I'm going to sleep... Z Z Z Z Z Z...");

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException ex) {
            // While thread sleeps, it could be interrupted by another thread, if so, the catch block
            // will be executed.
            print("AnotherThread (name = " + Thread.currentThread().getName() + ") " +
                    "was interrupted while sleeping");
        }

        print("AnotherThread (name = " + Thread.currentThread().getName() + ") finish sleeping");
    }

    private void print(String strToPrint) {
        System.out.println(ANSI_GREEN + strToPrint);
    }
}
