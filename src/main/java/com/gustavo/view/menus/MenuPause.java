package com.gustavo.view.menus;

import com.gustavo.view.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

//public class MenuPause extends JPanel implements KeyListener {
public class MenuPause extends JPanel implements Menu {

    private final int widthTela;
    private final int heightTela;
    private JLabel btContinueGame;
    private JLabel btExitGame;
    private boolean opcaoExitSelecionada = false;
    private boolean opcaoContinueSelecionada = true;

    public MenuPause(int widthTela, int heightTela) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        initConfigs();
    }

    @Override
    public void initConfigs() {
        setSize(this.widthTela, this.heightTela);
        setOpaque(false);
        setFocusable(true);
        setVisible(true);
        setLayout(new GridBagLayout());
//        addKeyListener(this);
        initButtons();
    }

    @Override
    public void initButtons(){
        btContinueGame = createOptionLabel("CONTINUE");
        btExitGame = createOptionLabel("SAIR");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0; // Ocupa todo o espaço horizontal disponível
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets((heightTela / 2) - 50, 0, 10, 0);

        btContinueGame.setForeground(Color.YELLOW);
        add(btContinueGame, constraints);
//        add(btContinueGame);

        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.gridy = 1;
        add(btExitGame, constraints);
//        add(btExitGame);
    }

    @Override
    public JLabel createOptionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        return label;
    }


    @Override
    public void initTexto() {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE && Game.isPausado
                || keyEvent.getKeyCode() == KeyEvent.VK_ENTER && opcaoContinueSelecionada){
            setButtonSelection(false, false);
            Game.isPausado = false;
            remove(btContinueGame);
            remove(btExitGame);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            setButtonSelection(false, true);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            setButtonSelection(true, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER && opcaoExitSelecionada) {
            SwingUtilities.getWindowAncestor(this).dispose();
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    private void setButtonSelection(boolean continua, boolean exit) {
        opcaoContinueSelecionada = continua;
        opcaoExitSelecionada = exit;

        btContinueGame.setForeground(opcaoContinueSelecionada ? Color.YELLOW : Color.WHITE);
        btExitGame.setForeground(opcaoExitSelecionada ? Color.YELLOW : Color.WHITE);
    }
}
