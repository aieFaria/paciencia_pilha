package com.faria;

import java.util.Stack;

import com.faria.enums.Naipes;
import com.faria.enums.NumCarta;

public class PilhaJogo {

    private Naipes naipeAceito; // Atributo é o único naipe aceito na pilha
    private int tamanho = 19; // Máximo de cartas que esta pilha irá suportar

    private Stack<BCard> jogoPilha = new Stack<>();

    public PilhaJogo () {
        
    }

    // Função que verifica se a carta pode ser adicionada na pilha do jogo
    public boolean inserirPush(BCard movimento) {

        if( !this.jogoPilha.isEmpty() ) {

            // Verificação condicional para saber se a carta que esta sendo adicionada não é da mesma cor daquela 
            // no topo da pilha
            // Verifica se a carta é menor que aquela no topo da lista
            if ( !movimento.getCorCarta().equals(this.jogoPilha.peek().getCorCarta()) &&
                 movimento.getNumeroDaCarta().getValor() == (this.jogoPilha.peek().getNumeroDaCarta().getValor() - 1)
                ) {
                this.jogoPilha.push(movimento); // Adiciona Carta na pilha

                // Sempre torna o primeiro item da lista visivel por padrão
                this.jogoPilha.peek().setVisible(true); 
                return true;
            }

               // Verifica se a carta que esta sendo adicionada apilha vazia é um K
        } else if ( movimento.getNumeroDaCarta().equals(NumCarta.K) ) {
            this.jogoPilha.push(movimento); // Adiciona Carta na pilha

            // Sempre torna o primeiro item da lista visivel por padrão
            this.jogoPilha.peek().setVisible(true); 
            return true;
        } 

        return false;
        
    }


    // Insert que burla lógica de inserção para dar inicio ao jogo
    public void inserirInicio (BCard carta) {
        // Mantém as cartas viradas para baixo
        carta.setVisible(false);
        this.getJogoPilha().push(carta);
    }


    public Stack<BCard> getJogoPilha() {
        return jogoPilha;
    }


    public void setJogoPilha(Stack<BCard> jogoPilha) {
        this.jogoPilha = jogoPilha;
    }

    
}
