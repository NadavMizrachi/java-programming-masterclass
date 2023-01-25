public class SyncUser implements Runnable {

    private SynchronizedObject object;

    public SyncUser(SynchronizedObject object) {
        this.object = object;
    }

    @Override
    public void run() {
        this.object.syncMethod();
    }
}
