package com.company;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("25 Game");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(650,680);
        window.setLayout(new BorderLayout());
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        Logic game = new Logic();
        window.add(game);
        window.setVisible(true);

    }
}
