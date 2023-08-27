package com.gustavo.view;

import com.gustavo.view.menus.MenuInicial;

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

        MenuInicial menu = new MenuInicial(widthTela, heightTela);
        add(menu);

        setVisible(true);

    }

}
