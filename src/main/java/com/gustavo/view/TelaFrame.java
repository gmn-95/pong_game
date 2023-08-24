package com.gustavo.view;

import javax.swing.*;
import java.awt.*;

public class TelaFrame extends JFrame {


    private final int widthTela = 800;
    private final int heightTela = 600;

    public TelaFrame() throws HeadlessException {
        super();
        setTitle("Pong-game");
        setSize(widthTela, heightTela);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);

        Menu menu = new Menu(widthTela, heightTela);
        add(menu);

        setVisible(true);

    }

}
