package project.serverSide;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SocketHandler implements Runnable {

    private JLabel userCounter;
    private JTextArea textArea;
    private Socket socket;

    public static int getConnectionCounter() {
        return connectionCounter;
    }

    private volatile static int connectionCounter = 0;
    private volatile static List<SocketHandler> handlers = Collections.synchronizedList(new ArrayList<>());
    private PrintWriter out;
    private int userID = 0;

    public SocketHandler(Socket socket, JTextArea textArea, JLabel userCounter) {
        this.textArea = textArea;
        this.socket = socket;
        this.userCounter = userCounter;
    }

    @Override
    public void run() {

        try {
            handlers.add(this);
            connectionCounter++;
            userID++;
            userCounter.setText(connectionCounter + " users is connected");
            textArea.append("user #" + userID + " connected. \n");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            out = new PrintWriter(outputStream, true);

            out.println("You connected to server");

            while (true) {
                String message = in.nextLine();
                if (message.equalsIgnoreCase("exit")) {
                    textArea.append("user #" + userID + " disconnected. \n");
                    out.println("You disconnected from server");
                    connectionCounter--;
                    userCounter.setText(connectionCounter + " users is connected.");
                    break;
                } else {
                    broadcast("user #" + userID + " said: " + message);
                    textArea.append("user #" + userID + " said: " + message + "\n");
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
