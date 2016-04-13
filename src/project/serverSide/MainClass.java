package project.serverSide;

import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws IOException {

        MainWindow window = MainWindow.getInstance();
        window.getServer().start();

    }



}
