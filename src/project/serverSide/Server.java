package project.serverSide;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private TextArea textArea;

    public Server(TextArea textArea) {
        this.textArea = textArea;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8082);
        System.out.println("Server started");

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new SocketHandler(socket, textArea)).start();
        }
    }
}
