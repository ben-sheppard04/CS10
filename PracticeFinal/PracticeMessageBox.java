import java.util.ArrayList;
import java.util.List;

public class PracticeMessageBox {
    //TODO: your code here
    List<String> strings = new ArrayList<String>();
    //Put m as message once it's okay to do so
    public synchronized void put(String msg) throws InterruptedException {
        //TODO: your code here
        while(strings.size() >= 15) {
            wait();
        }
        strings.add(msg);
        notifyAll();
    }

    //Takes message once it's there
    public synchronized String take() throws InterruptedException {
        //TODO: your code here
        while(strings.size() <= 5) {
            wait();
        }
        String result = strings.get(strings.size() - 1);
        notifyAll();
        return result;
    }
}
