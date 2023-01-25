public class Reader implements Runnable {

    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        String msg = this.message.read();
        while (!msg.equals(MessagesConsts.LAST_MESSAGE)) {
            System.out.println("Got message : " + msg);
            msg = this.message.read();
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException ex) {

            }
        }
    }
}
