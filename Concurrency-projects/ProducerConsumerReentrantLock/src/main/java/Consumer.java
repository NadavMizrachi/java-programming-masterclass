import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {

    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLocker;

    public Consumer(List<String> buffer, String color, ReentrantLock bufferLocker) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLocker = bufferLocker;
    }

    @Override
    public void run() {
        int counter = 0;
        while (true) {
            if (this.bufferLocker.tryLock()) {
                try {
                    if (this.buffer.isEmpty()) {
                        counter++;
                        continue;
                    }
                    else if (this.buffer.get(0).equals(MessagesConsts.EOF)) {
                        System.out.println(color + "Buffer is EOF");
                        break;
                    } else {
                        System.out.println("Counter is = " + counter);
                        counter = 0;
                        System.out.println(color + " Consuming message " + this.buffer.remove(0));
                    }
                } finally {
                    this.bufferLocker.unlock();
                }
            } else {
                counter++;
            }
        }
    }
}
