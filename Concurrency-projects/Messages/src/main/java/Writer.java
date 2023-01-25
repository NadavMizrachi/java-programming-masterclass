public class Writer implements Runnable {

    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        String[] msgPayloads = createMessagesPayloadPool();
        for (String payload: msgPayloads) {
            this.message.write(payload);
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException ex) {

            }
        }
        this.message.write(MessagesConsts.LAST_MESSAGE);
    }

    public String[] createMessagesPayloadPool() {
        return new String[] {
                "First",
                "Second",
                "Third",
                "Four"
        };
    }
}
