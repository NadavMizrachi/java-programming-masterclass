public class Main {
    public static void main(String[] args) {
        System.out.println("Main thread start using");

        SynchronizedObject object = new SynchronizedObject();
        new Thread(new SyncUser(object)).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {

        }

        System.out.println("Creating UnSyncUser...");
        new Thread(new UnSyncUser(object)).start();
    }
}
