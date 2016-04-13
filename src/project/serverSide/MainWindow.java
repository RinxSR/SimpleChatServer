package project.serverSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainWindow extends JFrame {

    private static MainWindow instance;

    private Panel panel;
    private JButton button;
    private TextArea textArea;

    private Thread serverThread;


    private MainWindow() {
        this.setTitle("Simple Chat Server");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationByPlatform(true);

        panel = new Panel();
        panel.setLayout(new BorderLayout());

        button = new JButton("Server start");
        button.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
        panel.add(button, BorderLayout.EAST);


        this.add(panel, BorderLayout.NORTH);

        textArea = new TextArea();

        this.add(textArea);

        this.setVisible(true);
    }

    public static synchronized MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

}

//                serverThread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Server server = new Server(textArea);
//                        try {
//                            server.start();
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                });
//                serverThread.start();