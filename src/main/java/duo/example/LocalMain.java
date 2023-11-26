package duo.example;

import duo.example.ui.SuiteTab;

import javax.swing.*;

public class LocalMain {

    public static void main(String[] args) {
        final JFrame jFrame = new JFrame("example");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(new SuiteTab());
        jFrame.setSize(300, 300);
        jFrame.setVisible(true);
    }
}
