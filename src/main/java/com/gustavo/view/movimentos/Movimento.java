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

    private int vAtual;
    private Colisao colisao;

    private enum Players{
        PLAYER_1("PLAYER_1"),
        PLAYER_2("PLAYER_2");

        String player;
        Players(String player) {
            this.player = player;
        }

    }

    public Movimento(Jogadores jogadores, Bola bola, Colisao colisao) {
        this.jogadores = jogadores;
        this.bola = bola;
        this.colisao = colisao;

        this.bola.setxVelocidade(this.bola.getVelocidadeMaxima() / 2);
        this.bola.setyVelocidade(this.bola.getVelocidadeMaxima() / 2);
        vAtual = this.bola.getxVelocidade();
    }

    public void resetMovimento(Jogadores jogadores, Bola bola){
        this.jogadores = jogadores;
        this.bola = bola;

        this.bola.setxVelocidade(this.bola.getVelocidadeMaxima() / 2);
        this.bola.setyVelocidade(this.bola.getVelocidadeMaxima() / 2);
        vAtual = this.bola.getxVelocidade();
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
        } else if (keyCode == KeyEvent.VK_UP) {
            moveUp2 = true;
        } else if (keyCode == KeyEvent.VK_DOWN) {
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
        } else if (keyCode == KeyEvent.VK_UP) {
            moveUp2 = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            moveDown2 = false;
        }
    }

    private void moveParaCima(Players players){
        if(players.equals(Players.PLAYER_1)){
            jogadores.getPlayer1().y -= jogadores.getyVelocidadeMovimentoParaCima();
        }
        else {
            jogadores.getPlayer2().y -= jogadores.getyVelocidadeMovimentoParaCima();
        }
    }

    private void moveParaBaixo(Players players){
        if(players.equals(Players.PLAYER_1)){
            jogadores.getPlayer1().y += jogadores.getyVelocidadeMovimentoParaBaixo();
        }
        else {
            jogadores.getPlayer2().y += jogadores.getyVelocidadeMovimentoParaBaixo();
        }
    }

    public void movimentoDosJogadores(){
        if(moveUp1 && jogadores.getPlayer1().y >= 0){
            moveParaCima(Players.PLAYER_1);
        }
        else if(moveDown1 && colisao.checkColisaoParedeJogadores(jogadores.getPlayer1())){
            moveParaBaixo(Players.PLAYER_1);
        }

        if(moveUp2 && jogadores.getPlayer2().y >= 0) {
            moveParaCima(Players.PLAYER_2);
        }
        else if(moveDown2 && colisao.checkColisaoParedeJogadores(jogadores.getPlayer2())){
            moveParaBaixo(Players.PLAYER_2);
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
//            System.out.println("checkColisaoBolaNoMeioJogador P1");
        }
        else if(colisao.checkColisaoBolaNoMeioJogador(jogadores.getPlayer2())){
            bola.moverParaEsquerda();
            inverteParaEsquerda();
            zeraDirecaoVertical();
            aumentaVelocidadeBolaSeNecessario();
//            System.out.println("checkColisaoBolaNoMeioJogador P2");
        }

        if(colisao.checkColisaoBolaBateuNaPontaSuperioDaRaquete(jogadores.getPlayer1())
                || colisao.checkColisaoBolaBateuNaPontaSuperioDaRaquete(jogadores.getPlayer2())){
            inverteParaDeveSubir();
            aumentaVelocidadeBolaSeNecessario();
//            System.out.println("checkColisaoBolaBateuNaPontaSuperioDaRaquete");
        }

        if(colisao.checkColisaoBolaBateuNaPontaInferiorDaRaquete(jogadores.getPlayer1())
                || colisao.checkColisaoBolaBateuNaPontaInferiorDaRaquete(jogadores.getPlayer2())){
            inverteParaDeveDescer();
            aumentaVelocidadeBolaSeNecessario();
//            System.out.println("checkColisaoBolaBateuNaPontaInferiorDaRaquete");
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
            case 2:
                inverteParaDireita();
                inverteParaDeveSubir();
            default:
                inverteParaDireita();
                inverteParaDeveDescer();
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
//        System.out.println("VELOCIDADE ATUAL: " + vAtual);
        if(vAtual < bola.getVelocidadeMaxima()){
            vAtual++;
            bola.aumentaVelocidade(vAtual);
        }
    }

}
