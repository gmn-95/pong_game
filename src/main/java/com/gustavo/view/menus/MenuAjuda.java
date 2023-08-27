package com.gustavo.view.menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuAjuda extends JPanel implements Menu, KeyListener {

    private final int widthTela;
    private final int heightTela;


    public MenuAjuda(int widthTela, int heightTela) {
        this.widthTela = widthTela;
        this.heightTela = heightTela;
        initConfigs();
    }

    @Override
    public void initButtons() {
    }

    @Override
    public void initConfigs() {
        setSize(this.widthTela, this.heightTela);
        setBackground(Color.BLACK);
        setFocusable(true);
        setVisible(true);
        setLayout(new GridBagLayout());
        addKeyListener(this);
        initTexto();
    }

    @Override
    public void initTexto(){
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html"); // Define o tipo de conteúdo como HTML
        editorPane.setEditable(false); // Impede que o usuário edite o texto
        editorPane.setText(
                "<html>" +
                        "<body style='color: white'>" +
                            "<h1>Controles do Jogo</h1>"
                            + "<p>Use as teclas W A S D do teclado controlar o jogador 1 (jogador da esquerda).</p>"
                            + "<p>Use as SETAS do teclado controlar o jogador 2 (jogador da direita).</p>"
                            + "<p>A Tecla ESC pausa o jogo.</p>"
                            + "<br></br>"
                            + "<br></br>"
                            + "<br></br>"
                            + "<br></br>"
                            + "<p>Aperte ESC para voltar ao MENU</p>"
                        + "</body>" +
                "</html>");

        editorPane.setBackground(Color.BLACK);
        add(editorPane);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE){
            voltaParaMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    private void voltaParaMenu(){
        MenuInicial menu = new MenuInicial(widthTela, heightTela);

        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        jFrame.getContentPane().removeKeyListener(this);
        jFrame.getContentPane().remove(this);
        jFrame.getContentPane().add(menu);
        jFrame.getContentPane().revalidate();
        jFrame.getContentPane().repaint();

        menu.requestFocusInWindow();
    }
}
