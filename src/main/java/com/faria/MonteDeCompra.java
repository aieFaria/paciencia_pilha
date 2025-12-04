package com.faria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.ActiveEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.util.EmptyStackException;
import java.util.EventObject;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.faria.PuzzleGame.PuzzleButton;

public class MonteDeCompra extends JPanel {

    // Pilha1: Cartas viradas para baixo, isVisible = false. Pilha principal
    // Pilha2: Cartas viradas para cima, isVisible = true
    private Stack<BCard> pilha1, pilha2;

    // Configurações padrão do MonteDeCompra
    public void iniciarORatualizar() {

        // Atualizar Tela caso a janela ja esteja visivel
        if ( !this.isVisible() ) {
            this.setLayout(null);

            JButton controleDoMonte = new JButton(); // Botão para controlar virada das cartas da pilha1
            JButton controleMonteSaida = new JButton(); // Botão para controlar movimentação das cartas da pilha2

            controleDoMonte.setBounds(1, 1, 105, 140);
            controleMonteSaida.setBounds(controleDoMonte.getWidth()+30, 1, 105, 140);
            controleDoMonte.setBackground(null);
            controleMonteSaida.setBackground(Color.WHITE);
            //controleDoMonte.addActionListener(this::clicarMonte);

            controleDoMonte.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    acao();
                    atualizaBotao(controleDoMonte, pilha1);
                    atualizaBotao(controleMonteSaida, pilha2);
                    iniciarORatualizar();
                }

            });

            atualizaBotao(controleDoMonte, pilha1);
            atualizaBotao(controleMonteSaida, pilha2);
            
            this.setBounds(10, 5, controleDoMonte.getWidth()+30+controleMonteSaida.getWidth(), 140);
            this.setBackground(Color.BLUE);
            this.add(controleMonteSaida);
            this.add(controleDoMonte);
            this.setVisible(true);
            
        } else {
            this.revalidate();
            this.repaint();
        }

        
        //this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    

    public MonteDeCompra(Stack<BCard> pilha1, Stack<BCard> pilha2) {
        this.pilha1 = pilha1;
        this.pilha2 = pilha2;

        this.setLocation(1, 1);
        this.setVisible(false); //Padrão
    }

    @Override
    public String toString() {
        return ( pilha1.isEmpty() ? "null" : this.pilha1.peek() ) + "  ->  " +
               ( pilha2.isEmpty() ? "null" : this.pilha2.peek() );
    }

    private void clicarMonte(ActionEvent e) {
        this.acao();
        this.iniciarORatualizar();
        JButton btnClicado = (JButton) e.getSource();
    }
    
    /*
     * Atualiza a carta do topo do monte conforme há a movimentação das cartas
     * @param botão de referncia para ser aatualizado
     * @param pilha de referencia para ser analisado o topo
     */
    private void atualizaBotao(JButton botao, Stack<BCard> pilhaRef) {
        String nomeImage = "";

        if ( !pilhaRef.isEmpty() ) {
            if ( pilhaRef.peek().isVisible() ) {
                nomeImage = (pilhaRef.peek().getNumeroDaCarta()+"").toLowerCase() + pilhaRef.peek().getNaipe()+".png";
            } else {
                nomeImage = "fundo.png";
            }
        } else {
           nomeImage = pilhaRef.equals(pilha1) ? "reset.png" : "empty.png";
        }

        Image img = ScreenMain.carregarImagem(nomeImage); 
        Image scaled = img.getScaledInstance(105, 140, Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(scaled));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    /*
     * Função que realiza o movimento de pegar as cartas do Monte de comprar
     * A mesma função é responsável por retornar as cartas para o MOnte novamente 
     * quando tiver chegado ao fim
     */
    public void acao() {

        // Bloco try-catch para capturar exeção das pilhas vazias
        // Como tratar cada um deles
        try {

            BCard movido = pilha1.pop();
            movido.setVisible(true);

            pilha2.push( movido );

        } catch (EmptyStackException e) {

            if( pilha1.isEmpty() ) {

                pilha1 = esvaziarPilha(pilha2);

            } else if( pilha2.isEmpty() ) {
                BCard movido = pilha1.pop();
                movido.setVisible(true);

                pilha2.push( movido );
            }
        }

    }

    /*
     * Função para esvaziar uma pilha qualquer
     * @param Recebe como parametro a pilha que será esvaziada
     * @return Retorna a pilha esvaziada para o reaproveitamento
     * 
     * OBS: A ordem da pilha retornada pela função fica ao contrário da pilha
     *      esvaziada.
     */
    public static Stack<BCard> esvaziarPilha(Stack<BCard> pilhaRef) {

        Stack<BCard> retorno = new Stack<>();

        while ( !pilhaRef.isEmpty() ) {
            BCard movido = pilhaRef.pop();
            movido.setVisible(false);

            retorno.add( movido ); //Adiciona cada elemeto removido em sequencia
        }

        return retorno;
    }

    public Stack<BCard> getPilha1() {
        return this.pilha1;
    }

    public void setPilha1(Stack<BCard> pilha1) {
        this.pilha1 = pilha1;
    }

    public Stack<BCard> getPilha2() {
        return this.pilha2;
    }

    public void setPilha2(Stack<BCard> pilha2) {
        this.pilha2 = pilha2;
    }

}
