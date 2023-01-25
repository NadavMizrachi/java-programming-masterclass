import java.util.List;
import java.util.Random;

public class Producer implements Runnable {

    private int messagesCount = 20;
    private List<String> buffer;
    private String color;

    public Producer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        Random r = new Random();
        String[] msgsToProduce = createMessagesToProduce();
        for (String msg : msgsToProduce) {
            System.out.println(color + " Producer adding " + msg + " ...");
            synchronized (buffer) {
                this.buffer.add(msg);
            }
            try {
                Thread.sleep(r.nextInt(1000));
            } catch (InterruptedException ex) {

            }
        }

        System.out.println(color + " Producer adding EOF");
        synchronized (buffer) {
            this.buffer.add(MessagesConsts.EOF);
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
