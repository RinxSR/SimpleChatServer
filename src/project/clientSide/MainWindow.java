package project.clientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame{

    private static MainWindow instance;

    private MainWindow() {

        setTitle("Online Chat");
        setSize(500, 500);
        setLocationByPlatform(true);

        JTextArea textArea = new JTextArea();
        JTextField textField = new JTextField();

        JScrollPane scrollPane = new JScrollPane(textArea);

        SocketHandler handler = new SocketHandler(textField, textArea, this);
        new Thread(handler).start();

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.sendMessage(textField.getText());
                textField.setText("");
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton button = new JButton("Send message");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.sendMessage(textField.getText());
                textField.setText("");
            }
        });

        add(scrollPane);
        add(panel, BorderLayout.SOUTH);

        panel.add(textField);
        panel.add(button,BorderLayout.EAST);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                handler.sendMessage("exit");
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        setVisible(true);
    }

    public static synchronized MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }
}
