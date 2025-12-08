package com.faria;

import java.util.Stack;
import javax.swing.JButton;

/**
 * Classe que guarda o estado das jogadas para registro
 * Composta de atributos e métodos estáticos, sendo estes utilizados para controle 
 * dentro do programa
 * 
 * @attribute-pilhaOrigem    Referência a pilha de Origem
 * @attribute-botaoOrigem    Referência o botão clicado para selecionar a pilha de movimento
 * @attribute-pilhaMovimento Referência a pilha que esta em movimento
 *
 * */
public class EstadoJogo {

    private static Stack<BCard> pilhaOrigem = null; // De onde a carta está saindo
    private static JButton botaoOrigem = null;      // Qual botão/painel precisa ser atualizado visualmente
    private static Stack<BCard> pilhaMovimento = null; 

    // Seta a seleçao de origem, no caso de movimentação simples de única carda
    public static void setSelecao(Stack<BCard> origem, JButton botao) {
        limparSelecaoVisual();
        limpaAtributos(); 

        pilhaOrigem = origem;
        botaoOrigem = botao;
    }

    // Seta a seleçao de origem, no caso de movimentação composta de uma pilha de cartas
    public static void setSelecaoDePilha(Stack<BCard> origem, Stack<BCard> cartasMovendo, JButton botao) {
        limparSelecaoVisual();
        limpaAtributos();

        pilhaOrigem = origem;
        pilhaMovimento = cartasMovendo;
        botaoOrigem = botao;
    }

    /**
     * Limpa a seleção por completo, método public para utilização em outras classes
     * Ela executa dois métodos: {@link #limparSelecaoVisual()} e {@link #limpaAtributos()}
     */ 
    public static void limparSelecao() {
        limparSelecaoVisual();
        limpaAtributos();
    }

    // Limpa os atributos da classe colocando todos como null
    private static void limpaAtributos() {
        pilhaOrigem = null;
        pilhaMovimento = null;
        botaoOrigem = null;
    }

    // Remove a borda do botão de Origem
    private static void limparSelecaoVisual() {
        if (botaoOrigem != null) {
            botaoOrigem.setBorder(javax.swing.UIManager.getBorder("Button.border"));
            
            botaoOrigem.setBorder(null); // Remove borda amarela
        }
    }

    /**
     * Método de verificação lógica para caso haja carta selecionada
     * @return true caso tenha carta selecionada, false caso não tenha
     */ 
    public static boolean isCardSelected() {
        
        return pilhaOrigem != null && !pilhaOrigem.isEmpty();
    }

    /**
     * Método de verificação lógica para caso haja pilha de cartas selecionada
     * @return true caso tenha pilha selecionada, false caso não tenha
     */ 
    public static boolean isPilhaSelected() {
        
        return pilhaMovimento != null && !pilhaMovimento.isEmpty();
    }

    public static Stack<BCard> getPilhaOrigem() {
        return pilhaOrigem;
    }

    public static void setPilhaOrigem(Stack<BCard> pilhaOrigem) {
        EstadoJogo.pilhaOrigem = pilhaOrigem;
    }

    public static JButton getBotaoOrigem() {
        return botaoOrigem;
    }

    public static void setBotaoOrigem(JButton botaoOrigem) {
        EstadoJogo.botaoOrigem = botaoOrigem;
    }
    
    public static Stack<BCard> getpilhaMovimento() {
        return pilhaMovimento;
    }

    
}