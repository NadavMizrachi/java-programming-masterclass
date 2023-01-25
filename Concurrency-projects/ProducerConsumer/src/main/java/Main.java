import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> buffer = new ArrayList<>();

        new Thread(new Producer(buffer, ThreadColor.ANSI_GREEN)).start();
        new Thread(new Consumer(buffer, ThreadColor.ANSI_BLUE)).start();
        new Thread(new Consumer(buffer, ThreadColor.ANSI_RED)).start();
    }
}
