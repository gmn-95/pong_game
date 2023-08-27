package com.gustavo.view.jogadores;

import java.awt.*;

public class Jogadores{

    private Rectangle player1;
    private Rectangle player2;
    private final int yVelocidadeMovimento = 13;
    private int yVelocidadeMovimentoIa = 1;
    private final int height = 80;
    private final int width = 8;
    private int y = 250;

    public Jogadores() {
        player1 = new Rectangle(30, y, width, height);
        player2 = new Rectangle(760, y, width, height);
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(getPlayer1().x, getPlayer1().y, getPlayer1().width, getPlayer1().height);
        g.fillRect(getPlayer2().x, getPlayer2().y, getPlayer2().width, getPlayer2().height);
    }

    public void resetPosicao(){
        getPlayer1().x = 30;
        getPlayer1().y = y;
        getPlayer1().width = width;
        getPlayer1().height = height;

        getPlayer2().x = 760;
        getPlayer2().y = y;
        getPlayer2().width = width;
        getPlayer2().height = height;

    }

    public Rectangle getPlayer1() {
        return player1;
    }

    public Rectangle getPlayer2() {
        return player2;
    }

    public int getyVelocidadeMovimento() {
        return yVelocidadeMovimento;
    }

    public int getyVelocidadeMovimentoIa() {
        return yVelocidadeMovimentoIa;
    }

    public void setyVelocidadeMovimentoIa(int yVelocidadeMovimentoIa) {
        this.yVelocidadeMovimentoIa = yVelocidadeMovimentoIa;
    }
}
