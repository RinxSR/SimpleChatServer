package ChatServer.clientSide;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingWindow {
    private String IPadress;

    public void start() {
        JFrame frame = new JFrame("Online Chat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 200);

        JTextField textField = new JTextField();
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IPadress = textField.getText();
                frame.dispose();
            }
        });

        frame.add(textField);

        frame.setVisible(true);
    }
}
