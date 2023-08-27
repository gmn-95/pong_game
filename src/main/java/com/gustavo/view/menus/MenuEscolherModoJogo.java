package com.gustavo.view.menus;

import com.gustavo.view.Game;
import com.gustavo.view.Pontuacao;
import com.gustavo.view.bola.Bola;
import com.gustavo.view.colisao.Colisao;
import com.gustavo.view.jogadores.Jogadores;
import com.gustavo.view.movimentos.Movimento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuEscolherModoJogo extends JPanel implements Menu, KeyListener {

    private final int widthTela;
    private final int heightTela;
    private JLabel btJogadorVsJogador;
    private JLabel btJogadorVsIa;
    private JLabel btVoltar;
    private boolean contraIA;
    private boolean opcaoJogadorVsJogadorSelecionada = true;
    private boolean opcaoJogadorVsIaSelecionada = false;
    private boolean opcaoVoltarSelecionada = false;


    public MenuEscolherModoJogo(int widthTela, int heightTela) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        initConfigs();
    }

    @Override
    public void initButtons() {
        btJogadorVsJogador = createOptionLabel("JOGADOR VS JOGADOR");
        btJogadorVsIa = createOptionLabel("JOGADOR VS IA");
        btVoltar = createOptionLabel("VOLTAR");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0; // Ocupa todo o espaço horizontal disponível
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 0, 10, 0);

        btJogadorVsJogador.setForeground(Color.YELLOW);
        add(btJogadorVsJogador, constraints);

        constraints.gridy = 1;
        add(btJogadorVsIa, constraints);

        constraints.gridy = 2;
        add(btVoltar, constraints);
    }

    @Override
    public void initConfigs() {
        setSize(this.widthTela, this.heightTela);
        setBackground(Color.BLACK);
        setFocusable(true);
        setVisible(true);
        setLayout(new GridBagLayout());
        initButtons();
        addKeyListener(this);
    }

    private void iniciaPartida(){
        Jogadores jogadores = new Jogadores();
        Bola bola = new Bola(widthTela, heightTela);
        Colisao colisao = new Colisao(bola, widthTela, heightTela);
        Movimento movimento;
        movimento = new Movimento(jogadores, bola, colisao, contraIA);
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

    @Override
    public void initTexto() {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN && opcaoJogadorVsJogadorSelecionada){
            setButtonSelection(false, true, false);
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN && opcaoJogadorVsIaSelecionada){
            setButtonSelection(false, false, true);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && opcaoVoltarSelecionada) {
            setButtonSelection(false, true, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && opcaoJogadorVsIaSelecionada) {
            setButtonSelection(true, false, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            if(opcaoVoltarSelecionada){
                voltaMenu();
            }
            else if(opcaoJogadorVsJogadorSelecionada){
                contraIA = false;
                iniciaPartida();
            }
            else if(opcaoJogadorVsIaSelecionada){
                contraIA = true;
                iniciaPartida();
            }
        }
    }

    private void voltaMenu(){
        MenuInicial menu = new MenuInicial(widthTela, heightTela);
        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.getContentPane().removeKeyListener(this);
        jFrame.getContentPane().remove(this);
        jFrame.getContentPane().add(menu);
        jFrame.getContentPane().revalidate();
        jFrame.getContentPane().repaint();

        menu.requestFocusInWindow();
    }

    private void setButtonSelection(boolean opcaoJogadorVsJogadorSelecionada, boolean opcaoJogadorVsIaSelecionada, boolean opcaoVoltarSelecionada) {
        this.opcaoJogadorVsJogadorSelecionada = opcaoJogadorVsJogadorSelecionada;
        this.opcaoJogadorVsIaSelecionada = opcaoJogadorVsIaSelecionada;
        this.opcaoVoltarSelecionada = opcaoVoltarSelecionada;

        btJogadorVsJogador.setForeground(this.opcaoJogadorVsJogadorSelecionada ? Color.YELLOW : Color.WHITE);
        btJogadorVsIa.setForeground(this.opcaoJogadorVsIaSelecionada ? Color.YELLOW : Color.WHITE);
        btVoltar.setForeground(this.opcaoVoltarSelecionada ? Color.YELLOW : Color.WHITE);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

}
