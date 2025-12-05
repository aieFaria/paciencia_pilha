package com.faria;

import java.util.Stack;
import javax.swing.JButton;


public class EstadoJogo {

    public static Stack<BCard> pilhaOrigem = null; // De onde a carta está saindo
    public static JButton botaoOrigem = null;      // Qual botão precisa ser atualizado visualmente

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

    // Método utilitário para definir a seleção de forma centralizada
    // Use isto no ScreenGuardar ou MonteDeCompra ao clicar numa carta
    public static void setSelecao(Stack<BCard> origem, JButton botao) {
   
        limparSelecaoVisual();

        pilhaOrigem = origem;
        botaoOrigem = botao;
    }


    public static void limparSelecao() {
        limparSelecaoVisual();
        pilhaOrigem = null;
        botaoOrigem = null;
    }

    // Método auxiliar apenas para limpar o visual (borda), sem perder a referência lógica
    // Útil se quisermos trocar a seleção sem processar jogada
    private static void limparSelecaoVisual() {
        if (botaoOrigem != null) {
            
            botaoOrigem.setBorder(javax.swing.UIManager.getBorder("Button.border"));
        }
    }

    // Verifica se temos uma carta "na mão" pronta para mover
    public static boolean temCartaSelecionada() {
        return pilhaOrigem != null && !pilhaOrigem.isEmpty();
    }
}
