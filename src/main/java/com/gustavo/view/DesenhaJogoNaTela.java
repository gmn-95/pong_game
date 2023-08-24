package com.gustavo.view;

import com.gustavo.view.bola.Bola;
import com.gustavo.view.jogadores.Jogadores;
import com.gustavo.view.movimentos.Movimento;

import javax.swing.*;
import java.awt.*;

public class DesenhaJogoNaTela extends JPanel implements Runnable {

    private Jogadores jogadores;
    private Bola bola;
    private Movimento movimento;
    private final int widthTela;
    private final int heightTela;
    private Thread gameThread;
    private boolean jaIniciou = false;
    private Pontuacao pontuacao;

    public DesenhaJogoNaTela(int widthTela, int heightTela, Jogadores jogadores, Bola bola, Movimento movimento, Pontuacao pontuacao) {
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
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGame(g);
    }

    private void paintGame(Graphics g) {
        pontuacao.draw(g);
        jogadores.draw(g);
        bola.draw(g);

        //isso deixa o jogo 'liso' sem travamentos
        Toolkit.getDefaultToolkit().sync();
    }


    @Override
    public void run() {

        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while(true) {
            long now = System.nanoTime();
            delta += (now -lastTime) / ns;
            lastTime = now;
            if(delta >=1) {
                movimento.movimentoDosJogadores();
                movimento.movimentoBola();
                direcaoBolaAposPontoOuIncioJogo();
                resetPositionBolaEJogadores();
                repaint();
                delta--;
            }
        }
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
}
