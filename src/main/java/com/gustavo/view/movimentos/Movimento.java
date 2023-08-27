package com.gustavo.view.movimentos;

import com.gustavo.view.bola.Bola;
import com.gustavo.view.colisao.Colisao;
import com.gustavo.view.jogadores.Jogadores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Movimento implements KeyListener {

    private Jogadores jogadores;
    private Bola bola;
    private boolean moveUp1 = false;
    private boolean moveDown1 = false;
    private boolean moveUp2 = false;
    private boolean moveDown2 = false;
    private boolean deveVoltarLadoDireito = false;
    private boolean deveVoltarLadoEsquerdo = false;
    private boolean deveSubir = false;
    private boolean deveDescer = false;
    private boolean isIAjogando;
    private int vAtual = 0;
    private Colisao colisao;

    private enum Players{
        PLAYER_1("PLAYER_1"),
        PLAYER_2("PLAYER_2");

        String player;
        Players(String player) {
            this.player = player;
        }

    }

    public Movimento(Jogadores jogadores, Bola bola, Colisao colisao, boolean isIAjogando) {
        resetMovimento(jogadores, bola);
        this.colisao = colisao;
        this.isIAjogando = isIAjogando;

    }

    public void resetMovimento(Jogadores jogadores, Bola bola){
        this.jogadores = jogadores;
        this.bola = bola;

        this.bola.setxVelocidade(this.bola.getVelocidadeInicial());
        this.bola.setyVelocidade(this.bola.getVelocidadeInicial());
        this.jogadores.setyVelocidadeMovimentoIa(1);
        vAtual = this.bola.getVelocidadeInicial();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            moveUp1 = true;
        } else if (keyCode == KeyEvent.VK_S) {
            moveDown1 = true;
        } else if (keyCode == KeyEvent.VK_UP && !isIAjogando) {
            moveUp2 = true;
        } else if (keyCode == KeyEvent.VK_DOWN && !isIAjogando) {
            moveDown2 = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            moveUp1 = false;
        } else if (keyCode == KeyEvent.VK_S) {
            moveDown1 = false;
        } else if (keyCode == KeyEvent.VK_UP && !isIAjogando) {
            moveUp2 = false;
        } else if (keyCode == KeyEvent.VK_DOWN && !isIAjogando) {
            moveDown2 = false;
        }
    }

    private void moveParaCima(Players players){
        if(players.equals(Players.PLAYER_1)){
            jogadores.getPlayer1().y -= jogadores.getyVelocidadeMovimento();
        }
        else if(players.equals(Players.PLAYER_2)) {
            jogadores.getPlayer2().y -= jogadores.getyVelocidadeMovimento();
        }
    }

    private void moveParaBaixo(Players players){
        if(players.equals(Players.PLAYER_1)){
            jogadores.getPlayer1().y += jogadores.getyVelocidadeMovimento();
        }
        else if(players.equals(Players.PLAYER_2)){
            jogadores.getPlayer2().y += jogadores.getyVelocidadeMovimento();
        }
    }

    public void movimentoDosJogadores(){
        if(moveUp1 && colisao.checkColisaoParedeCimaJogadores(jogadores.getPlayer1())){
            moveParaCima(Players.PLAYER_1);
        }
        else if(moveDown1 && colisao.checkColisaoParedeBaixoJogadores(jogadores.getPlayer1())) {
            moveParaBaixo(Players.PLAYER_1);
        }

        if(moveUp2 && colisao.checkColisaoParedeCimaJogadores(jogadores.getPlayer2())) {
            moveParaCima(Players.PLAYER_2);
        }
        else if(moveDown2 && colisao.checkColisaoParedeBaixoJogadores(jogadores.getPlayer2())) {
            moveParaBaixo(Players.PLAYER_2);
        }
    }

    public void movimentosIA(){
        if(deveVoltarLadoDireito){
            if(bola.getY() < jogadores.getPlayer2().getMinY() && colisao.checkColisaoParedeCimaJogadores(jogadores.getPlayer2())){
                moveParaCima(Players.PLAYER_2);
            }
            else if(bola.getY() > jogadores.getPlayer2().getMaxY() && colisao.checkColisaoParedeBaixoJogadores(jogadores.getPlayer2())){
                moveParaBaixo(Players.PLAYER_2);
            }
        }
    }

    public void movimentoBola(){

        if(colisao.checkColisaoBolaJogador(jogadores.getPlayer2())){
            inverteParaEsquerda();
            aumentaVelocidadeBolaSeNecessario();
        }
        else if(colisao.checkColisaoBolaJogador(jogadores.getPlayer1())){
            inverteParaDireita();
            aumentaVelocidadeBolaSeNecessario();
        }

        if(deveVoltarLadoDireito){
            bola.moverParaDireita();
        }
        else if(deveVoltarLadoEsquerdo) {
            bola.moverParaEsquerda();
        }

        if (colisao.checkColisaoBolaParedeInferior()){
            inverteParaDeveSubir();
        }
        else if(colisao.checkColisaoBolaParedeSuperior()) {
            inverteParaDeveDescer();
        }

        if(deveSubir && !colisao.checkColisaoBolaNoMeioJogador(jogadores.getPlayer1()) && !colisao.checkColisaoBolaNoMeioJogador(jogadores.getPlayer2())){
            bola.moverParaCima();
        }
        else if(deveDescer && !colisao.checkColisaoBolaNoMeioJogador(jogadores.getPlayer1()) && !colisao.checkColisaoBolaNoMeioJogador(jogadores.getPlayer2())){
            bola.moverParaBaixo();
        }

        if(colisao.checkColisaoBolaNoMeioJogador(jogadores.getPlayer1())){
            bola.moverParaDireita();
            inverteParaDireita();
            zeraDirecaoVertical();
            aumentaVelocidadeBolaSeNecessario();
        }
        else if(colisao.checkColisaoBolaNoMeioJogador(jogadores.getPlayer2())){
            bola.moverParaEsquerda();
            inverteParaEsquerda();
            zeraDirecaoVertical();
            aumentaVelocidadeBolaSeNecessario();
        }

        if(colisao.checkColisaoBolaBateuNaPontaSuperioDaRaquete(jogadores.getPlayer1())
                || colisao.checkColisaoBolaBateuNaPontaSuperioDaRaquete(jogadores.getPlayer2())){
            inverteParaDeveSubir();
        }
        else if(colisao.checkColisaoBolaBateuNaPontaInferiorDaRaquete(jogadores.getPlayer1())
                || colisao.checkColisaoBolaBateuNaPontaInferiorDaRaquete(jogadores.getPlayer2())){
            inverteParaDeveDescer();
        }
    }

    public void direcaoInicialBola(){
        Random random = new Random();
        int direcaoInit = random.nextInt(4);
        switch (direcaoInit) {
            case 0:
                inverteParaEsquerda();
                inverteParaDeveSubir();
                break;
            case 1:
                inverteParaEsquerda();
                inverteParaDeveDescer();
                break;
            case 2:
                inverteParaDireita();
                inverteParaDeveSubir();
                break;
            case 3:
                inverteParaDireita();
                inverteParaDeveDescer();
                break;
        }
    }

    private void inverteParaDeveSubir(){
        deveSubir = true;
        deveDescer = false;
    }

    private void inverteParaDeveDescer(){
        deveDescer = true;
        deveSubir = false;
    }

    private void inverteParaEsquerda(){
        deveVoltarLadoDireito = false;
        deveVoltarLadoEsquerdo = true;
    }

    private void inverteParaDireita(){
        deveVoltarLadoDireito = true;
        deveVoltarLadoEsquerdo = false;
    }

    private void zeraDirecaoVertical(){
        deveDescer = false;
        deveSubir = false;
    }

    private void aumentaVelocidadeBolaSeNecessario(){
        vAtual += 1;
        if(isIAjogando){
            if(jogadores.getyVelocidadeMovimentoIa() < jogadores.getyVelocidadeMovimento()){
                jogadores.setyVelocidadeMovimentoIa(jogadores.getyVelocidadeMovimentoIa() + 1);
            }
        }
        bola.aumentaVelocidade(vAtual);
    }

    public boolean isIAjogando() {
        return isIAjogando;
    }
}
