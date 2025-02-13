import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class PracticeChatClient{
        private Scanner console;					// input from the user
        private PracticeChatClientCommunicator comm;		// communication with the server
        private boolean hungup = false;				// has the server hung up on us?

        public PracticeChatClient(Socket sock) throws IOException {
            // For reading lines from the console
            console = new Scanner(System.in);

            // Fire off a new thread to handle incoming messages from server
            comm = new PracticeChatClientCommunicator(sock, this);
            comm.setDaemon(true);
            comm.start();

            // Greeting; name request and response
            System.out.println("Please enter your name");
            String name = console.nextLine(); //blocks until keyboard input
            comm.send(name);
        }

        /**
         * Get console input and send it to server;
         * stop & clean up when server has hung up (noted by hungup)
         */
        public void handleUser() throws IOException {
            while (!hungup) {
                //console.nextLine() blocks until text is entered
                comm.send(console.nextLine());
            }
        }

        /**
         * Notes that the server has hung up (so handleUser loop will terminate)
         */
        public void hangUp() {
            hungup = true;
        }

        public static void main(String[] args) throws IOException {
            new PracticeChatClient(new Socket("localhost", 4242)).handleUser();
        }
}
