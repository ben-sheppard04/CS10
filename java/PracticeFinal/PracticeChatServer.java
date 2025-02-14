import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.*;

public class PracticeChatServer {
    private ServerSocket listen;                  // for accepting connections
    private ArrayList<PracticeChatServerCommunicator> comms;   // all the connections with clients
    public Map<String, PracticeChatServerCommunicator> clients;
    public static final Integer masterPassword = 1787;
    public Map<String, String> emails;
    public PracticeChatServer(ServerSocket listen) {
        this.listen = listen;
        comms = new ArrayList<PracticeChatServerCommunicator>();
        //TODO: your code here
        clients = new HashMap<>();
        emails = new HashMap<>();
    }

    /**
     * The usual loop of accepting connections and firing off new threads to handle them
     */
    public void getConnections() throws IOException {
        while (true) {
            //listen.accept in next line blocks until new connection
            PracticeChatServerCommunicator comm = new PracticeChatServerCommunicator(listen.accept(), this);
            comm.setDaemon(true);
            comm.start();
            addCommunicator(comm);
        }
    }

    /**
     * Adds the handler to the list of current client handlers
     */
    public synchronized void addCommunicator(PracticeChatServerCommunicator comm) {
        comms.add(comm);
    }

    /**
     * Removes the handler from the list of current client handlers
     */
    public synchronized void removeCommunicator(PracticeChatServerCommunicator comm) {
        comms.remove(comm);
    }

    /**
     * Sends the message from the one client handler to all the others (but not to the originator)
     */
    public synchronized void broadcast(PracticeChatServerCommunicator from, String msg) {
        for(PracticeChatServerCommunicator comm : comms) {
            comm.send(msg);
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("waiting for connections");
        new PracticeChatServer(new ServerSocket(4245)).getConnections();
    }

}
