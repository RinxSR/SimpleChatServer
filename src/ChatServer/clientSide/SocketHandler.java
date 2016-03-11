package ChatServer.clientSide;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketHandler implements Runnable {

    private JTextField textField;
    private JTextArea textArea;

    private PrintWriter out;

    public SocketHandler(JTextField textField, JTextArea textArea) {
        this.textField = textField;
        this.textArea = textArea;
    }

    @Override

    public void run() {
        try {
            Socket socket = new Socket("localhost", 8082);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            out = new PrintWriter(outputStream, true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (in.hasNext()) {
                        textArea.append(in.nextLine() + "\n");
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage() {
        out.println(textField.getText());
    }
}
