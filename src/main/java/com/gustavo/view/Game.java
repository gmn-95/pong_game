package com.gustavo.view;

import com.gustavo.view.bola.Bola;
import com.gustavo.view.jogadores.Jogadores;
import com.gustavo.view.menus.MenuInicial;
import com.gustavo.view.menus.MenuPause;
import com.gustavo.view.movimentos.Movimento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements Runnable, KeyListener {

    private Jogadores jogadores;
    private Bola bola;
    private Movimento movimento;
    private final int widthTela;
    private final int heightTela;
    private Thread gameThread;
    private boolean jaIniciou = false;
    private Pontuacao pontuacao;
    private static boolean isPausado = false;

    private MenuPause menuPause;


    public Game(int widthTela, int heightTela, Jogadores jogadores, Bola bola, Movimento movimento, Pontuacao pontuacao) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        this.jogadores = jogadores;
        this.bola = bola;
        this.movimento = movimento;
        this.pontuacao = pontuacao;

        setSize(widthTela, heightTela);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this.movimento);
        addKeyListener(this);

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGame(g);
    }

    private void addMenuPause() {
        isPausado = true;
        menuPause = new MenuPause(widthTela, heightTela);
        add(menuPause); // Adicione o menuPause ao Game JPanel
        revalidate(); // Revalide o Game JPanel para refletir as alterações
    }

    private void removeMenuPause(){
        isPausado = false;
        remove(menuPause);
        revalidate();
    }

    private void paintGame(Graphics g) {
        pontuacao.draw(g);
        jogadores.draw(g);
        bola.draw(g);

        //isso deixa o jogo 'liso' sem travamentos
        Toolkit.getDefaultToolkit().sync();
    }

    private boolean continuaGame = true;

    @Override
    public void run() {

        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while(continuaGame) {
            long now = System.nanoTime();
            delta += (now -lastTime) / ns;
            lastTime = now;

            if (isPausado) {
                delta = 0;
                try {
                    Thread.sleep(10); // Pequena pausa para reduzir uso da CPU
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue; // Pula para a próxima iteração do loop
            }

            if(delta >=1) {
                movimento.movimentoBola();
                if(movimento.isIAjogando()){
                    movimento.movimentosIA();
                }
                movimento.movimentoDosJogadores();
                direcaoBolaAposPontoOuIncioJogo();
                resetPositionBolaEJogadores();
                repaint();
                delta--;
            }
        }

        gameThread.interrupt();
    }

    private void resetPositionBolaEJogadores(){
        if(pontuacao.verificaSeBolaPassouDosLimites()){
            jogadores.resetPosicao();
            bola.resetPosicao();
            movimento.resetMovimento(jogadores, bola);
            pontuacao.setBola(bola);
            jaIniciou = false;
        }
    }

    private void direcaoBolaAposPontoOuIncioJogo(){
        if(!jaIniciou){
            movimento.direcaoInicialBola();
            jaIniciou = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }


    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE && !isPausado){
            addMenuPause();
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE && isPausado) {
            removeMenuPause();
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE
                && Game.isPausado
                || (keyEvent.getKeyCode() == KeyEvent.VK_ENTER
                && menuPause != null
                && menuPause.isOpcaoContinueSelecionada())){
            menuPause.setButtonSelection(false, false);
            removeMenuPause();
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN && Game.isPausado) {
            menuPause.setButtonSelection(false, true);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_UP && Game.isPausado) {
            menuPause.setButtonSelection(true, false);
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER && menuPause != null && menuPause.isOpcaoExitSelecionada()) {
            removeMenuPause();
            MenuInicial menu = new MenuInicial(widthTela, heightTela);
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            jFrame.getContentPane().removeKeyListener(this);
            jFrame.getContentPane().remove(this);
            jFrame.getContentPane().add(menu);
            jFrame.getContentPane().revalidate();
            jFrame.getContentPane().repaint();

            menu.requestFocusInWindow();

            continuaGame = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
