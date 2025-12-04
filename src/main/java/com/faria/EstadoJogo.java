package com.faria;

import java.util.Stack;
import javax.swing.JButton;

import com.faria.telasSecundarias. ScreenDeCompra;
import com.faria.telasSecundarias.ScreenDeCompra;

public class EstadoJogo {
    // Variáveis estáticas ("Globais")
    public static Stack<BCard> pilhaOrigem = null; // Referencia de saída da carta
    public static JButton botaoOrigem = null;      // Referencia do botão que precisa atualizar
    public static ScreenDeCompra mt = null;
    public static boolean in =  false;
    
    // Método para limpar a seleção após uma jogada
    public static void limparSelecao() {

        if(mt != null) {
            atualizaTudo(mt);
        }

        if (botaoOrigem != null) {
            
            botaoOrigem.setBorder(javax.swing.UIManager.getBorder("Button.border"));
        }
        pilhaOrigem = null;
        botaoOrigem = null;
    }

    private static void atualizaTudo( ScreenDeCompra mt) {
        mt.iniciarORatualizar();
        mt.repaint();
    }

    // Verifica se temos uma carta "na mão"
    public static boolean temCartaSelecionada() {
        return pilhaOrigem != null && !pilhaOrigem.isEmpty();
    }

    public static boolean inserivel(){
        return true;
    }
}
