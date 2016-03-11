package project.clientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {

    public void start() {

        JFrame frame = new JFrame("Online Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JTextArea textArea = new JTextArea();

        JTextField textField = new JTextField();

        SocketHandler handler = new SocketHandler(textField, textArea);
        new Thread(handler).start();

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.sendMessage();
                textField.setText("");
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton button = new JButton("Send message");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.sendMessage();
                textField.setText("");
            }
        });

        frame.add(textArea);
        frame.add(panel, BorderLayout.SOUTH);

        panel.add(textField);
        panel.add(button,BorderLayout.EAST);

        frame.setVisible(true);
    }
}
