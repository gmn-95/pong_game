package com.gustavo.view.bola;

import java.awt.*;

public class Bola {

    private int raio = 8;
    private int x;
    private int y;
    private int xVelocidade;
    private int yVelocidade;
    private final int velocidadeMaxima = 11;
    private final int widthTela;
    private final int heightTela;

    public Bola(int widthTela, int heightTela) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;

        calculaPosicao();
        calculaRaio();
    }

    private void calculaPosicao(){
        int centroX = widthTela / 2;
        int centroY = heightTela / 2;

        x = centroX - raio;
        y = centroY - raio;
    }

    public void draw(Graphics g){
        g.fillOval(getX(), getY(), getRaio(), getRaio());
    }

    public void moverParaCima(){
        setY(getY() - getyVelocidade());
    }

    public void moverParaBaixo(){
        setY(getY() + getyVelocidade());
    }

    public void resetPosicao(){
        xVelocidade = 0;
        yVelocidade = 0;
        calculaPosicao();
    }

    public void moverParaEsquerda(){
        setX(getX() - getxVelocidade());
    }

    public void moverParaDireita(){
        setX(getX() + getxVelocidade());
    }

    public int limiteEsquerdo(){
        return x - raio;
    }

    public int limiteDireito(){
        return x + raio;
    }

    public int limiteInferior(){
        return y + raio;
    }

    public int limiteSuperior(){
        return y - raio;
    }

    private void calculaRaio(){
        raio = raio * 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRaio() {
        return raio;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxVelocidade() {
        return xVelocidade;
    }

    public void setxVelocidade(int xVelocidade) {
        this.xVelocidade = xVelocidade;
    }

    public int getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    public int getyVelocidade() {
        return yVelocidade;
    }

    public void setyVelocidade(int yVelocidade) {
        this.yVelocidade = yVelocidade;
    }

    public void aumentaVelocidade(int v){
        if(v < velocidadeMaxima){
            setxVelocidade(v);
            setyVelocidade(v);
        }
    }
}
