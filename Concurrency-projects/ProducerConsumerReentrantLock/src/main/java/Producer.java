import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Producer implements Runnable {

    private int messagesCount = 20;
    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLocker;

    public Producer(List<String> buffer, String color, ReentrantLock bufferLocker) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLocker = bufferLocker;
    }

    @Override
    public void run() {
        Random r = new Random();
        String[] msgsToProduce = createMessagesToProduce();
        for (String msg : msgsToProduce) {
            System.out.println(color + " Producer adding " + msg + " ...");
            try {
                this.bufferLocker.lock();
                this.buffer.add(msg);
            } finally {
                this.bufferLocker.unlock();
            }

            try {
                Thread.sleep(r.nextInt(1000));
            } catch (InterruptedException ex) {

            }
        }

        System.out.println(color + " Producer adding EOF");
        try {
            this.bufferLocker.lock();
            this.buffer.add(MessagesConsts.EOF);
        } finally {
            this.bufferLocker.unlock();
        }
    }

    private String[] createMessagesToProduce() {
        String[] msgs = new String[this.messagesCount];
        for (int i = 0; i < this.messagesCount; i++) {
            msgs[i] = "Message Number " + i;
        }
        return msgs;
    }
}
