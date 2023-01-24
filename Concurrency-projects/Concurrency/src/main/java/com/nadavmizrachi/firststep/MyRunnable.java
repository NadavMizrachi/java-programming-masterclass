package com.nadavmizrachi.firststep;

import static com.nadavmizrachi.firststep.ThreadColor.ANSI_BLUE;

public class MyRunnable implements Runnable{
    @Override
    public void run() {
        print("Hello from MyRunnable thread! My thread name : " + Thread.currentThread().getName());
    }

    private void print(String strToPrint) {
        System.out.println(ANSI_BLUE + strToPrint);
    }
}
