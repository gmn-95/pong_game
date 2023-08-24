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
        return checkColisaoBolaJogador(jogador) &&
                (
                    bola.getY() == jogador.getCenterY()
                    || (bola.getY() >= jogador.getCenterY() - 10 && bola.getY() <= jogador.getCenterY())
                    || (bola.getY() <= jogador.getCenterY() + 10 && bola.getY() >= jogador.getCenterY())
                );
    }

    public boolean checkColisaoBolaBateuNaPontaSuperioDaRaquete(Rectangle jogador){
        return checkColisaoBolaJogador(jogador)
                && bola.getY() <= jogador.getCenterY() - 20;
    }

    public boolean checkColisaoBolaBateuNaPontaInferiorDaRaquete(Rectangle jogador){
        return checkColisaoBolaJogador(jogador)
                && bola.getY() >= jogador.getCenterY() + 10;
    }

    public boolean checkColisaoBolaParedeInferior(){
        return bola.getY() >= (heightTela - 40);
    }

    public boolean checkColisaoBolaParedeSuperior(){
        return bola.getY() <= 0;
    }
}
