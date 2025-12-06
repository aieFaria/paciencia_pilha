package com.faria;

import java.util.Stack;
import javax.swing.JButton;

public class EstadoJogo {

    public static Stack<BCard> pilhaOrigem = null; // De onde a carta está saindo
    public static JButton botaoOrigem = null;      // Qual botão/painel precisa ser atualizado visualmente
    

    public static Stack<BCard> subPilhaMovimento = null; 


    public static void setSelecao(Stack<BCard> origem, JButton botao) {
        limparSelecaoVisual();
        limparVariaveis(); 

        pilhaOrigem = origem;
        botaoOrigem = botao;
    }

    public static void setSelecaoSubPilha(Stack<BCard> origem, Stack<BCard> cartasMovendo, JButton botao) {
        limparSelecaoVisual();
        limparVariaveis();

        pilhaOrigem = origem;
        subPilhaMovimento = cartasMovendo;
        botaoOrigem = botao;
    }

    public static Stack<BCard> esvaziarPilha(Stack<BCard> pilhaRef) {

        Stack<BCard> retorno = new Stack<>();

        while ( !pilhaRef.isEmpty() ) {
            BCard movido = pilhaRef.pop();

            retorno.add( movido ); //Adiciona cada elemeto removido em sequencia
        }

        return retorno;
    }

    public static void apararPilhaOrigem(Stack<BCard> ref) {
            if(!ref.isEmpty())
                for (int i=0; i < ref.size(); i++) {
                if(!pilhaOrigem.isEmpty())
                        pilhaOrigem.pop();
            }
        
    }

    public static void limparSelecao() {
        limparSelecaoVisual();
        limparVariaveis();
    }

    private static void limparVariaveis() {
        pilhaOrigem = null;
        subPilhaMovimento = null;
        botaoOrigem = null;
    }

    private static void limparSelecaoVisual() {
        if (botaoOrigem != null) {
            botaoOrigem.setBorder(javax.swing.UIManager.getBorder("Button.border"));
     
            botaoOrigem.setBorder(null); // Remove borda amarela
        }
    }

    public static boolean isCardSelected() {
        
        return pilhaOrigem != null && !pilhaOrigem.isEmpty();
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
    
    public static Stack<BCard> getSubPilhaMovimento() {
        return subPilhaMovimento;
    }

    public static boolean isPilhaSelected() {
        
        return subPilhaMovimento != null && !subPilhaMovimento.isEmpty();
    }
}