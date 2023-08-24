package com.gustavo.view;

import com.gustavo.view.bola.Bola;
import com.gustavo.view.colisao.Colisao;
import com.gustavo.view.movimentos.Movimento;
import com.gustavo.view.jogadores.Jogadores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu extends JPanel implements KeyListener {

    private final int widthTela;
    private final int heightTela;
    private JLabel btStartGame;
    private JLabel btExitGame;
    private boolean opcaoStartSelecionada = true;
    private boolean opcaoExitSelecionada = false;

    public Menu(int widthTela, int heightTela) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        setSize(this.widthTela, this.heightTela);
        setBackground(Color.BLACK);
        setFocusable(true);
        setVisible(true);
        setLayout(new GridBagLayout());
        initOptions();
        addKeyListener(this);
    }

    public void initOptions(){
        initButtons();
    }

    private void initButtons(){

        btStartGame = createOptionLabel("START");
        btExitGame = createOptionLabel("EXIT");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0; // Ocupa todo o espaço horizontal disponível
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        btStartGame.setForeground(Color.YELLOW);
        add(btStartGame, constraints);

        constraints.gridy = 1;
        add(btExitGame, constraints);
    }

    private JLabel createOptionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        return label;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN){
            setButtonSelection(false, true);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            setButtonSelection(true, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            if(opcaoExitSelecionada){
                SwingUtilities.getWindowAncestor(this).dispose();
                System.exit(0);
            }
            else if(opcaoStartSelecionada){
                iniciaPartida();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    private void iniciaPartida(){
        Jogadores jogadores = new Jogadores();
        Bola bola = new Bola(widthTela, heightTela);
        Colisao colisao = new Colisao(bola, widthTela, heightTela);
        Movimento movimento = new Movimento(jogadores, bola, colisao);
        Pontuacao pontuacao = new Pontuacao(bola, widthTela, heightTela);
        DesenhaJogoNaTela desenhaJogoNaTela = new DesenhaJogoNaTela(widthTela, heightTela, jogadores, bola, movimento, pontuacao);

        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.getContentPane().removeKeyListener(this);
        jFrame.getContentPane().remove(this);
        jFrame.getContentPane().add(desenhaJogoNaTela);
        jFrame.getContentPane().revalidate();
        jFrame.getContentPane().repaint();

        desenhaJogoNaTela.requestFocusInWindow();
    }

    private void setButtonSelection(boolean start, boolean exit) {
        opcaoStartSelecionada = start;
        opcaoExitSelecionada = exit;

        btStartGame.setForeground(start ? Color.YELLOW : Color.WHITE);
        btExitGame.setForeground(exit ? Color.YELLOW : Color.WHITE);
    }
}
