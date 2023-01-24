public class Main {

    public static String THREAD_1_NAME = "Thread 1";
    public static String THREAD_2_NAME = "Thread 2";

    public static void main(String[] args) {
        Countdown countdown = new Countdown();

        CountdownThread countdownThread1 = new CountdownThread(countdown);
        countdownThread1.setName(THREAD_1_NAME);
        countdownThread1.start();
        CountdownThread countdownThread2 = new CountdownThread(countdown);
        countdownThread2.setName(THREAD_2_NAME);
        countdownThread2.start();
    }
}

class Countdown {
    private int i = 10;     // This var will be saved on head ---> Shared memory between JVM threads!


    // Option 1, make the whole function synchronized
//    public synchronized void doCountdown() {
//        String color;
//        switch (Thread.currentThread().getName()) {
//            case "Thread 1":
//                color = ThreadColor.ANSI_BLUE;
//                break;
//            case "Thread 2":
//                color = ThreadColor.ANSI_GREEN;
//                break;
//            default:
//                color = "";
//                break;
//        }
//
//        for ( i = 10; i > 0 ; i--) {
//            System.out.println(color + " thread printing. Value of i = " + i);
//        }
//    }

    // Option 2, make certain block synchronized. Notice! You should activate the syncronized
    // on object that sits on heap! (not on function local variable because each thread has its own
    // stack memory and therefore, other local variables
        public void doCountdown() {
        String color;
        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_BLUE;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_GREEN;
                break;
            default:
                color = "";
                break;
        }

        synchronized (this) {
            for ( i = 10; i > 0 ; i--) {
                System.out.println(color + " thread printing. Value of i = " + i);
            }
        }
    }
}

class CountdownThread extends Thread {
    private Countdown countdown;

    public CountdownThread(Countdown countdown) {
        this.countdown = countdown;
    }

    @Override
    public void run() {
        this.countdown.doCountdown();
    }
}

