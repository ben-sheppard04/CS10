import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PracticeChatServerCommunicator extends Thread{
        private Socket sock;               // each instance is in a different thread and has its own socket
        private PracticeChatServer server;          // the main server instance
        private String name;               // client's name (first interaction with server)
        private BufferedReader in;          // from client
        private PrintWriter out;            // to client
        //TODO: your code here
        public boolean connected = true;
        public boolean admin = false;
        public PracticeChatServerCommunicator(Socket sock, PracticeChatServer server) {
            this.sock = sock;
            this.server = server;
        }

        public void run() {
            try {
                System.out.println("someone connected");

                // Communication channel
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                out = new PrintWriter(sock.getOutputStream(), true);

                // Identify -- first message is the name
                name = in.readLine();
                System.out.println("it's " + name);
                out.println("welcome " + name);
                server.broadcast(this, name + " entered the room");
                //TODO: your code here
                server.clients.put(name, this);
                // Chat away
                String line;
                while ((line = in.readLine()) != null) {
                    //TODO: your code here
                    String[] splitLine = line.split(" ");
                    if(splitLine[0].equals("KICK:")) {
                        if(admin){
                            if(server.clients.containsKey(splitLine[1])) {
                                PracticeChatServerCommunicator toKick = server.clients.get(splitLine[1]);
                                server.clients.remove(splitLine[1]);
                                toKick.kick();
                            }
                            else {
                                out.println("Sorry, that is not a person in server.");
                            }
                        }
                        else {
                           out.println("Sorry, you aren't an admin and can't kick people.");
                        }
                    }
                    else if (splitLine[0].equals("RENAME:")) {
                        rename(splitLine[1]);
                    }
                    else if (splitLine[0].equals("PASSWORD:")) {
                        int input = Integer.parseInt(splitLine[0]);
                        if(input == PracticeChatServer.masterPassword){
                            admin = true;
                            out.println("Correct password. You are now an admin and can remove anyone");
                        }
                        else {
                            out.println("Sorry, wrong password.");
                        }
                    }
                    else if (splitLine[0].equals("PRIVATE MESSAGE:")) {
                        String name = splitLine[1].substring(0, splitLine[1].length() - 1);
                        String message = splitLine[2];
                        privateMessage(name, message);
                    }
                    else if (splitLine[0].equals("SET EMAIL")) {
                        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
                        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
                        Matcher matcher = pattern.matcher(splitLine[1]);
                        boolean matchFound = matcher.matches();
                        if(matchFound) {
                            server.emails.put(name, splitLine[1]);
                            out.println("Email added successfully!");
                        }
                        else {
                            out.println("Email is in incorrect format. Please add email in the correct format.");
                        }
                    } else if (splitLine[0].equals("GET EMAIL")) {
                        if (server.emails.containsKey(splitLine[1])) {
                            String email = server.emails.get(splitLine[1]);
                            out.println(splitLine[1] + "'s email: " + email);
                        }
                        else {
                            out.println("ERROR: The specified client either does not exist or has not uploaded their email");
                        }

                    } else {
                        out.println("Please enter valid command.");
                    }

                }

                // Done
                System.out.println(name + " hung up");
                server.broadcast(this, name + " left the room");

                // Clean up -- note that also remove self from server's list of handlers so it doesn't         broadcast here
                server.removeCommunicator(this);
                out.close();
                in.close();
                sock.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Sends a message to the client
         * @param msg
         */
        public void send(String msg) {
            out.println(msg);
        }
        public void kick() {
            server.removeCommunicator(this);
            server.broadcast( this, this + " was deleted");
            out.println("you were kicked buddy");
            connected = false;
        }
        public void rename(String newName) {
            server.clients.remove(this.name);
            server.clients.put(newName, this);
            name = newName;

        }
        public void privateMessage(String toName, String message) {
            server.clients.get(toName).send(this.name + " sent you a message: " + message);
        }

}
