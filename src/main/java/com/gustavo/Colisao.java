package com.gustavo;

import java.awt.*;

public class Colisao {

    private Bola bola;

    private final int widthTela;

    private final int heightTela;

    public Colisao(Bola bola, int widthTela, int heightTela) {
        this.bola = bola;
        this.widthTela = widthTela;
        this.heightTela = heightTela;
    }

    public boolean checkColisaoParedeJogadores(Rectangle jogador){
        return jogador.getY() < heightTela - jogador.getHeight() - jogador.getHeight() / 2;
    }

    /**
     * Essa 'folga' representa um valor dado a 'raquete' do lado direito, para que a bola nao rebata somente quando
     * estiver na metade da raquete
     * */
    public boolean checkColisaoBolaJogador(Rectangle jogador){
        return jogador.intersects(bola.getX(), bola.getY(), bola.getRaio(), bola.getRaio());
    }

    public boolean checkColisaoBolaNoMeioJogador(Rectangle jogador){
//        System.out.println((jogador.getY() - 10) >= bola.getY() && (jogador.getY() + 10) <= bola.getY() && jogador.getX() == bola.getX());
//        System.out.println("jogador.getY(): " + jogador.getY());
//        System.out.println("bola.getY(): " + bola.getY());
//        System.out.println("jogador.getX(): " + jogador.getX());
//        System.out.println("bola.getX(): " + bola.limiteDireito());
//        System.out.println("jogador.getMinX(): " + jogador.getMinX());
//        System.out.println("jogador.getMinY(): " + jogador.getMinY());
//        System.out.println("jogador.getMaxY(): " + jogador.getMaxY());
        return bola.getY() <= jogador.getMaxY() - 30 && bola.getY() >= jogador.getMinY() + 30
                && checkColisaoBolaJogador(jogador);
    }

    public boolean checkColisaoBolaBateuNaPontaSuperioDaRaquete(Rectangle jogador){
        return checkColisaoBolaJogador(jogador)
//                && bola.getY() >= jogador.getMinY()
                && bola.getY() <= jogador.getCenterY() - 28;
    }

    public boolean checkColisaoBolaBateuNaPontaInferiorDaRaquete(Rectangle jogador){
        return checkColisaoBolaJogador(jogador)
//                && bola.getY() <= jogador.getMaxY()
                && bola.getY() >= jogador.getCenterY() + 28;
    }

    public boolean checkColisaoBolaParedeInferior(){
        return bola.getY() >= (heightTela - 40);
    }

    public boolean checkColisaoBolaParedeSuperior(){
        return bola.getY() <= 0;
    }
}
