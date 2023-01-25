import java.util.List;

public class Consumer implements Runnable {

    private List<String> buffer;
    private String color;

    public Consumer(List<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (buffer) {
                if (this.buffer.isEmpty()) {
                    continue;
                } else if (this.buffer.get(0).equals(MessagesConsts.EOF)) {
                    System.out.println(color + "Buffer is EOF");
                    break;
                } else {
                    System.out.println(color + " Consuming message " + this.buffer.remove(0));
                }
            }
        }
    }
}
