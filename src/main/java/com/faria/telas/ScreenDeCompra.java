package com.faria.telas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.faria.BCard;
import com.faria.EstadoJogo;

/**
 * Classe que representa a tela de amostragens da pilha de compra
 * Apresenta suas regras como suas regras
 * 
 * @attribute-pilha1             A pilha de cartas viradas para baixo
 * @attribute-pilha2             A pilha de cartas visiveis que são as visiveis e manipulaveis
 * @attribute-inicializado       Indica se a classe já foi inicializada para regra de 
 *                               atualização dos Componentes da tela
 * @attribute-controleDoMonte    Botão responsável por virar as cartas da pilha1 para pilha2
 * @attribute-controleMonteSaida Botão responsavel por mover as cartas da pilha2 para local indicado
 * 
 * @methods Dispões de métodos que geram os Botões com as devidas figuras de cada carta
 * */


public class ScreenDeCompra extends JPanel {

    // Pilha1: Cartas viradas para baixo, isVisible = false. Pilha principal
    // Pilha2: Cartas viradas para cima, isVisible = true
    private Stack<BCard> pilha1, pilha2;
    private boolean inicializado = false;
    private JButton controleDoMonte = new JButton(); // Botão para controlar virada das cartas da pilha1
    private JButton controleMonteSaida = new JButton(); // Botão para controlar movimentação das cartas da pilha2

    // Configurações padrão do MonteDeCompra
    public String iniciarORatualizar() {

        String comando = "";

        // Atualizar Tela caso a janela ja esteja visivel
        if ( !inicializado ) {
            this.setLayout(null);

            controleDoMonte.setBounds(0, 0, 105, 140);
            controleMonteSaida.setBounds(controleDoMonte.getWidth()+30, 0, 105, 140);
            controleDoMonte.setBackground(Color.PINK);
            controleMonteSaida.setBackground(Color.PINK);
            controleMonteSaida.addActionListener(this::clicarMonte);

            // Ações executadas ao apertar sobre o monte de compra
            controleDoMonte.addActionListener(new ActionListener() {



                @Override
                public void actionPerformed(ActionEvent e) {
                    acao();
                    
                    iniciarORatualizar();

                }

            });

            // atualizaBotao(controleDoMonte, pilha1);
            // atualizaBotao(controleMonteSaida, pilha2);
            // this.repaint();
            
            this.setBounds(10, 5, controleDoMonte.getWidth()+30+controleMonteSaida.getWidth(), 140);
            this.setBackground(Color.PINK);
            this.add(controleMonteSaida);
            this.add(controleDoMonte);
            this.setVisible(true);
            
            inicializado = true;
        } 

        atualizaBotao(controleDoMonte, pilha1);
        atualizaBotao(controleMonteSaida, pilha2);

        this.revalidate();
        this.repaint();
        
        return comando;
        //this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    

    public ScreenDeCompra(Stack<BCard> pilha1, Stack<BCard> pilha2) {
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

    private void clicarMonte(ActionEvent ev) {
        JButton buttonDestino = (JButton) ev.getSource();

        if( EstadoJogo.isPilhaSelected() ) { return; }

        if (EstadoJogo.isPilhaSelected()) {
            EstadoJogo.limparSelecao();
            return;
        }

        //boolean testeLogico = !(EstadoJogo.pilhaOrigem.equals(pilha1) || EstadoJogo.pilhaOrigem.equals(pilha2));
        if (EstadoJogo.isCardSelected()) {
        
            System.out.println("Ação inválida no monte por enquanto.");
            EstadoJogo.limparSelecao();
            return;
        }

        // 
        if (!pilha2.isEmpty()) {
            EstadoJogo.pilhaOrigem = this.pilha2;
            EstadoJogo.botaoOrigem = buttonDestino;
            
            // Borda de seleção
            buttonDestino.setBorder(new LineBorder(Color.YELLOW, 3)); 
            System.out.println("Carta do Monte Selecionada");
        }
        

    }
    
    /*
     * Atualiza a carta do topo do monte conforme há a movimentação das cartas
     * @param   botão de referncia para ser aatualizado
     * @param   pilha de referencia para ser analisado o topo
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

        // Evita que a pilha de movimento desapareça
        if( EstadoJogo.isPilhaSelected() ) { return; }

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
        } finally {
            EstadoJogo.limparSelecao();
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
