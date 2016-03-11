package ChatServer.serverSide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class SocketHandler implements Runnable {

    private Socket socket;
    private static int connetctionCounter;
    private static List<SocketHandler> handlers = Collections.synchronizedList(new ArrayList<>());
    private PrintWriter out;
    private int numberOfUser;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            handlers.add(this);
            connetctionCounter++;
            numberOfUser = connetctionCounter;
            System.out.println("user #" + connetctionCounter + " connected");
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            out = new PrintWriter(outputStream, true);

            while (true) {
                String message = in.nextLine();
                broadcast("user #" + numberOfUser + " said: " + message);
                if (message.equals("exit")) {
                    System.out.println("user #" + connetctionCounter + " disconnected");
                    connetctionCounter--;
                    break;

                }
            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        Iterator<SocketHandler> iterator = handlers.iterator();
        while (iterator.hasNext()) {
            iterator.next().out.println(message);
        }
    }
}
