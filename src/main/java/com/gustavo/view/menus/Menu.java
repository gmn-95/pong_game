package com.gustavo.view.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public interface Menu extends KeyListener {

    void initButtons();

    default JLabel createOptionLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        return label;
    }

    void initConfigs();

    void initTexto();
}
