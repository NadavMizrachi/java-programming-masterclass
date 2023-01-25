public class UnSyncUser implements Runnable{
    private SynchronizedObject object;

    public UnSyncUser(SynchronizedObject object) {
        this.object = object;
    }

    @Override
    public void run() {
        this.object.unSyncMethod();
    }
}
