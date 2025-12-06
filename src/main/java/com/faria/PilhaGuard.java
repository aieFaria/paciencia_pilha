package com.faria;

import java.util.EmptyStackException;
import java.util.Stack;

import com.faria.enums.Naipes;
import com.faria.enums.NumCarta;

/*
 * Essa classe representa as pilhas que guardam as cartas de cada Naipe
 * @code
 * 
 */ 
public class PilhaGuard {

    private Naipes naipeAceito; // Atributo é o único naipe aceito na pilha
    private int tamanho = 13; // Máximo de cartas que esta pilha irá suportar

    private Stack<BCard> guardPilha = new Stack<>();

    public PilhaGuard (Naipes naipe) {
        this.naipeAceito = naipe;
    }

    // Regra de push para acrescentar na pilha apenas se Naipe coincidir com o aceito
    // Também há regra para impedir push caso valor da carta não seja sequencia certa
    public void push(BCard card) {

        // Serve para mitigar quando a pilha for vazia
        try {

            if (card.getNaipe().equals(naipeAceito) && 
                card.getNumeroDaCarta().getValor() == (guardPilha.peek().getNumeroDaCarta().getValor() + 1) ) {

                guardPilha.push(card);

            }
        } catch (EmptyStackException e) {

            /*
             * Caso a pilha que guarda as cartas em sequencia separadamente por Naipe 
             * esteja vazia "guardPilha.peek()" gera a EmptyStackException então caso
             * deseje adicionar um elemento a essa pilha só dará certo se for o AS do naipe correto
             * 
             */
            if( card.getNaipe().equals(naipeAceito) && card.getNumeroDaCarta() == NumCarta.AS) {
                guardPilha.push(card);
            }
        }


    }

    public Naipes getNaipeAceito() {
        return naipeAceito;
    }

    public void setNaipeAceito(Naipes naipeAceito) {
        this.naipeAceito = naipeAceito;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Stack<BCard> getGuardPilha() {
        return guardPilha;
    }

    public void setGuardPilha(Stack<BCard> guardPilha) {
        this.guardPilha = guardPilha;
    }

}
