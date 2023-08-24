package com.gustavo.view;

import com.gustavo.view.bola.Bola;

import java.awt.*;

public class Pontuacao extends Rectangle {

    private Bola bola;

    private final int widthTela;

    private final int heightTela;

    private int pontuacaoJogador1;
    private int pontuacaoJogador2;

    public Pontuacao(Bola bola, int widthTela, int heightTela) {
        this.bola = bola;
        this.widthTela = widthTela;
        this.heightTela = heightTela;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 60));

        g.drawLine(widthTela / 2, 0, widthTela / 2, heightTela);

        g.drawString(pontuacaoJogador1 / 10 + String.valueOf(pontuacaoJogador1 % 10), (widthTela / 2) - 85, 50);
        g.drawString(pontuacaoJogador2 / 10 + String.valueOf(pontuacaoJogador2 % 10), (widthTela / 2) + 10, 50);
    }

    public boolean verificaSeBolaPassouDosLimites(){
        boolean result = false;
        if(bola.getX() >= widthTela){
            pontuacaoJogador1++;
            result = true;
        }
        else if(bola.getX() <= 0) {
            pontuacaoJogador2++;
            result = true;
        }
        return result;
    }

    public Bola getBola() {
        return bola;
    }

    public void setBola(Bola bola) {
        this.bola = bola;
    }

    public int getPontuacaoJogador1() {
        return pontuacaoJogador1;
    }

    public void setPontuacaoJogador1(int pontuacaoJogador1) {
        this.pontuacaoJogador1 = pontuacaoJogador1;
    }

    public int getPontuacaoJogador2() {
        return pontuacaoJogador2;
    }

    public void setPontuacaoJogador2(int pontuacaoJogador2) {
        this.pontuacaoJogador2 = pontuacaoJogador2;
    }
}
