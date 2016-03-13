package project.clientSide;

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
    private JFrame frame;

    private PrintWriter out;

    public SocketHandler(JTextField textField, JTextArea textArea, JFrame frame) {
        this.textField = textField;
        this.textArea = textArea;
        this.frame = frame;
    }

    @Override

    public void run() {
        try {
            Socket socket = new Socket("localhost", 8082);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Scanner in = new Scanner(inputStream);
            out = new PrintWriter(outputStream, true);


            while (in.hasNext()) {
                String line = in.nextLine();
                textArea.append(line + "\n");
                if (line.equals("You disconnected from server")) {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                    frame.dispose();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage() {
        out.println(textField.getText());
    }
}
