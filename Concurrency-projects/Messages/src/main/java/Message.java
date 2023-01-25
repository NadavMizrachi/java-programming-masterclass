public class Message {

    private String payload;
    private boolean isEmpty = true;

    public synchronized String read() {
        while (isEmpty) {
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        isEmpty = true;
        notifyAll();
        return payload;
    }

    public synchronized void write(String payload) {
        while (!isEmpty) {
            try {
                wait();
            } catch (InterruptedException ex) {

            }
        }
        isEmpty = false;
        notifyAll();
        this.payload = payload;
    }

}
