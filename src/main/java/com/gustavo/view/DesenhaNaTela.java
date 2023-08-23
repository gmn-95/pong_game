package com.gustavo.view;

import com.gustavo.Bola;
import com.gustavo.Colisao;
import com.gustavo.jogadores.Jogadores;
import com.gustavo.Movimento;

import javax.swing.*;
import java.awt.*;

public class DesenhaNaTela extends JPanel implements Runnable {

    private Jogadores jogadores;
    private Bola bola;
    private Movimento movimento;
    private final int widthTela;
    private final int heightTela;
    private Thread gameThread;

    private boolean jaIniciou = false;

    private Pontuacao pontuacao;

    public DesenhaNaTela(int widthTela, int heightTela, Jogadores jogadores, Bola bola, Movimento movimento, Pontuacao pontuacao) {
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
        // Cor da tela
        g.setColor(Color.BLACK);
        // Desenhar as 'raquetes'
        g.setColor(Color.WHITE);
        g.fillRect(jogadores.getPlayer1().x, jogadores.getPlayer1().y, jogadores.getPlayer1().width, jogadores.getPlayer1().height);
        g.fillRect(jogadores.getPlayer2().x, jogadores.getPlayer2().y, jogadores.getPlayer2().width, jogadores.getPlayer2().height);

        //divisória tela
        g.drawLine(widthTela / 2, 0, widthTela / 2, heightTela);

        //bola
        g.fillOval(bola.getX(), bola.getY(), bola.getRaio(), bola.getRaio());

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
                direcaoInicialBola();
                resetPositionBolaEJogadores();
                repaint();
                delta--;
            }
        }
    }

    private void resetPositionBolaEJogadores(){
        if(pontuacao.verificaSeBolaPassouDosLimites()){
            this.jogadores = new Jogadores();
            this.bola = new Bola(widthTela, heightTela);
            this.movimento = new Movimento(jogadores, bola, new Colisao(bola, widthTela, heightTela));
            addKeyListener(this.movimento);
            pontuacao.setBola(bola);
            jaIniciou = false;
        }
    }

    private void direcaoInicialBola(){
        if(!jaIniciou){
            movimento.direcaoInicialBola(true);
            jaIniciou = true;
        }
    }
}
