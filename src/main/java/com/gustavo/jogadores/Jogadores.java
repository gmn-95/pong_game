package com.gustavo.jogadores;

import java.awt.*;

public class Jogadores{

    private Rectangle player1;
    private Rectangle player2;

    private final int yVelocidadeMovimentoParaCima = 8;
    private final int yVelocidadeMovimentoParaBaixo = 8;
    private final int height = 80;
    private final int width = 8;
    private int y = 250;

    public Jogadores() {
        player1 = new Rectangle(30, y, width, height);
        player2 = new Rectangle(760, y, width, height);
    }

    public Rectangle getPlayer1() {
        return player1;
    }

    public Rectangle getPlayer2() {
        return player2;
    }

    public int getyVelocidadeMovimentoParaCima() {
        return yVelocidadeMovimentoParaCima;
    }

    public int getyVelocidadeMovimentoParaBaixo() {
        return yVelocidadeMovimentoParaBaixo;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
