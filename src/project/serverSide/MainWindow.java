package project.serverSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    private static MainWindow instance;

    private Panel panel1;
    private Panel panel2;
    private JButton buttonStart;
    private JButton buttonStop;
    private JLabel userCounter;
    private JTextArea logTextArea;

    private Server server;

    public Server getServer() {
        return server;
    }

    private MainWindow() {

        setTitle("Simple Chat Server");
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationByPlatform(true);

        panel1 = new Panel();
        panel1.setLayout(new BorderLayout());

        panel2 = new Panel();
        panel2.setLayout(new FlowLayout());
        panel1.add(panel2, BorderLayout.EAST);

        buttonStart = new JButton("Server start");
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.setServerOn(true);
                logTextArea.append("New connection to the chat is possible\n");

            }
        });
        panel2.add(buttonStart);

        buttonStop = new JButton("Server stop");
        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.setServerOn(false);
                logTextArea.append("New connection to the chat is impossible\n");

            }
        });

        panel2.add(buttonStop);


        add(panel1, BorderLayout.NORTH);

        logTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        add(scrollPane);

        userCounter = new JLabel("0 users is connected");
        add(userCounter, BorderLayout.SOUTH);

        setVisible(true);

        server = new Server(logTextArea, userCounter);
    }

    public static synchronized MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

}
