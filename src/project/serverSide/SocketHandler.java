package project.serverSide;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class SocketHandler implements Runnable {

    private TextArea textArea;
    private Socket socket;
    private volatile static int connectionCounter;
    private volatile static List<SocketHandler> handlers = Collections.synchronizedList(new ArrayList<>());
    private PrintWriter out;
    private int numberOfUser;

    public SocketHandler(Socket socket, TextArea textArea) {
        this.textArea = textArea;
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            handlers.add(this);
            connectionCounter++;
            numberOfUser = connectionCounter;
            System.out.println("user #" + connectionCounter + " connected");
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            out = new PrintWriter(outputStream, true);

            out.println("You connected to server");

            while (true) {
                String message = in.nextLine();
                if (message.equals("exit")) {
                    System.out.println("user #" + connectionCounter + " disconnected");
                    out.println("You disconnected from server");
                    connectionCounter--;
                    break;
                } else {
                    broadcast("user #" + numberOfUser + " said: " + message);
                }
            }
            inputStream.close();
            outputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (SocketHandler handler : handlers) {
            handler.out.println(message);
        }
    }
}
