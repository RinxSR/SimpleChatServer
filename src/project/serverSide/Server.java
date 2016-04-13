package project.serverSide;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private boolean serverOn = false;

    public void setServerOn(boolean serverOn) {
        this.serverOn = serverOn;
    }

    private JTextArea logTextArea;
    private JLabel userCounter;

    public Server(JTextArea logTextArea, JLabel userCounter) {
        this.logTextArea = logTextArea;
        this.userCounter = userCounter;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8082);
        System.out.println("Server started");

        while (true) {
            Socket socket = serverSocket.accept();
            if (serverOn) {
                new Thread(new SocketHandler(socket, logTextArea, userCounter)).start();
            }

        }
    }
}
