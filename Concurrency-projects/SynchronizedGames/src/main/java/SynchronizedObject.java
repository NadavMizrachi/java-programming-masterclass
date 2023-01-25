public class SynchronizedObject {

    public synchronized void syncMethod() {
        System.out.println("Starting syncMethod...");
        while (true) {

        }
    }

    public synchronized void unSyncMethod() {
        System.out.println("Inside unSyncMethod");
    }
}
