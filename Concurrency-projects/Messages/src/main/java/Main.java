public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Writer Reader program...");

        Message msg = new Message();

        (new Thread(new Writer(msg))).start();
        (new Thread(new Reader(msg))).start();
    }
}
