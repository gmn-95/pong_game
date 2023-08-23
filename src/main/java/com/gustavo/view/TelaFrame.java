package com.gustavo.view;

import com.gustavo.*;
import com.gustavo.jogadores.Jogadores;

import javax.swing.*;
import java.awt.*;

public class TelaFrame extends JFrame {

    private DesenhaNaTela desenhaNaTela;

    private final int widthTela = 800;
    private final int heightTela = 600;

    public TelaFrame() throws HeadlessException {
        super();
        setTitle("Pong-game");
        setSize(widthTela, heightTela);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.white);

        Jogadores jogadores = new Jogadores();
        Bola bola = new Bola(widthTela, heightTela);
        Colisao colisao = new Colisao(bola, widthTela, heightTela);
        Movimento movimento = new Movimento(jogadores, bola, colisao);
        Pontuacao pontuacao = new Pontuacao(bola, widthTela, heightTela);
        desenhaNaTela = new DesenhaNaTela(widthTela, heightTela, jogadores, bola, movimento, pontuacao);

        add(desenhaNaTela);
    }

}
