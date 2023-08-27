package com.gustavo.view.menus;

import com.gustavo.view.Pontuacao;
import com.gustavo.view.Game;
import com.gustavo.view.bola.Bola;
import com.gustavo.view.colisao.Colisao;
import com.gustavo.view.movimentos.Movimento;
import com.gustavo.view.jogadores.Jogadores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuInicial extends JPanel implements Menu {

    private final int widthTela;
    private final int heightTela;
    private JLabel btStartGame;
    private JLabel btExitGame;
    private JLabel btHelpGame;
    private boolean opcaoStartSelecionada = true;
    private boolean opcaoExitSelecionada = false;
    private boolean opcaoHelpSelecionada = false;

    public MenuInicial(int widthTela, int heightTela) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        initConfigs();
    }

    @Override
    public void initConfigs(){
        setSize(this.widthTela, this.heightTela);
        setBackground(Color.BLACK);
        setFocusable(true);
        setVisible(true);
        setLayout(new GridBagLayout());
        addKeyListener(this);
        initButtons();
    }

    @Override
    public void initTexto() {

    }

    @Override
    public void initButtons(){

        btStartGame = createOptionLabel("INICIAR");
        btHelpGame = createOptionLabel("AJUDA");
        btExitGame = createOptionLabel("SAIR");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0; // Ocupa todo o espaço horizontal disponível
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        btStartGame.setForeground(Color.YELLOW);
        add(btStartGame, constraints);

        constraints.gridy = 1;
        add(btHelpGame, constraints);

        constraints.gridy = 2;
        add(btExitGame, constraints);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN && opcaoStartSelecionada){
            setButtonSelection(false, true, false);
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN && opcaoHelpSelecionada){
            setButtonSelection(false, false, true);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && opcaoExitSelecionada) {
            setButtonSelection(false, true, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && opcaoHelpSelecionada) {
            setButtonSelection(true, false, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            if(opcaoExitSelecionada){
                SwingUtilities.getWindowAncestor(this).dispose();
                System.exit(0);
            }
            else if(opcaoStartSelecionada){
                iniciaPartida();
            }
            else if(opcaoHelpSelecionada){
                iniciaTelaDeAjuda();
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
        Game startGame = new Game(widthTela, heightTela, jogadores, bola, movimento, pontuacao);

        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.getContentPane().removeKeyListener(this);
        jFrame.getContentPane().remove(this);
        jFrame.getContentPane().add(startGame);
        jFrame.getContentPane().revalidate();
        jFrame.getContentPane().repaint();

        startGame.requestFocusInWindow();
    }

    private void iniciaTelaDeAjuda(){
        MenuAjuda menuAjuda = new MenuAjuda(widthTela, heightTela);

        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.getContentPane().removeKeyListener(this);
        jFrame.getContentPane().remove(this);
        jFrame.getContentPane().add(menuAjuda);
        jFrame.getContentPane().revalidate();
        jFrame.getContentPane().repaint();

        menuAjuda.requestFocusInWindow();
    }

    private void setButtonSelection(boolean start, boolean help, boolean exit) {
        opcaoStartSelecionada = start;
        opcaoHelpSelecionada = help;
        opcaoExitSelecionada = exit;

        btStartGame.setForeground(opcaoStartSelecionada ? Color.YELLOW : Color.WHITE);
        btHelpGame.setForeground(opcaoHelpSelecionada ? Color.YELLOW : Color.WHITE);
        btExitGame.setForeground(opcaoExitSelecionada ? Color.YELLOW : Color.WHITE);
    }
}
