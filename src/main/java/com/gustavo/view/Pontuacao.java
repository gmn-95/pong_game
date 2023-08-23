package com.gustavo.view;

import com.gustavo.Bola;

public class Pontuacao {

    private Bola bola;

    private final int widthTela;

    private final int heightTela;

    public Pontuacao(Bola bola, int widthTela, int heightTela) {
        this.bola = bola;
        this.widthTela = widthTela;
        this.heightTela = heightTela;
    }

    public boolean verificaSeBolaPassouDosLimites(){
        return bola.getX() >= widthTela || bola.getX() <= 0;
    }

    public Bola getBola() {
        return bola;
    }

    public void setBola(Bola bola) {
        this.bola = bola;
    }
}
