package com.faria;

import org.junit.jupiter.api.Test;

import com.faria.enums.Naipes;
import com.faria.enums.NumCarta;
import com.faria.telasSecundarias.ScreenDeCompra;

import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import java.util.Stack;


public class StackTest {

    @Nested
    @DisplayName("Verificações gerais de uma pilha")
    class All {
        @Test
        @DisplayName("Verificar pilha vazia")
        public void retrieveAnEmptyList() {
            Stack<Object> pilha = new Stack<>();

            assertEquals(pilha.isEmpty(), true);
        }
    }

    @Nested
    @DisplayName("Verificações de inserções de uma pilha")
    class Insercao {
        @Test
        @DisplayName("Verificar topo da pilha")
        public void retrieveAnEmptyList() {

            Stack<Integer> pilha = new Stack<>();
            pilha.push(2);
            pilha.push(1);

            assertEquals(pilha.peek(), 1);
        }

        @Test
        @DisplayName("Teste para inserção na pilha do jogo")
        public void pilhaDoJogo() {
            PilhaJogo pilha = new PilhaJogo();
            BCard carta = new BCard(Naipes.PAUS, NumCarta.AS, BCard.PRETO, false);

            assertEquals(pilha.inserirPush(carta), false);

            BCard cartaK = new BCard(Naipes.PAUS, NumCarta.K, BCard.PRETO, false);

            assertEquals(pilha.inserirPush(cartaK), true);

            BCard cartaQ = new BCard(Naipes.PAUS, NumCarta.Q, BCard.PRETO, false);

            assertEquals(pilha.inserirPush(cartaQ), false);

            cartaQ.setCorCarta("vermelho");

            assertEquals(pilha.inserirPush(cartaQ), true);

            assertEquals(cartaK.isVisible(), true);
            assertEquals(cartaQ.isVisible(), true);
        }
    }

    @Nested
    @DisplayName("Verificações de remoções de elemtos da pilha")
    class Remocao { 
        @Test
        @DisplayName("Verificar Monte de compras")
        public void esVaziamentoDePilhas() {

            Stack<BCard> pilha = new Stack<>();
            Stack<BCard> pilha2 = new Stack<>();

            pilha2.add( new BCard(Naipes.ESPADAS, NumCarta.AS, BCard.PRETO, false) );
            pilha2.add( new BCard(Naipes.ESPADAS, NumCarta.DOIS, BCard.PRETO, false) );
            
            pilha = ScreenDeCompra.esvaziarPilha( pilha2 );

            //assertEquals(pilha.peek(), new BCard(Naipes.ESPADAS, NumCarta.AS, BCard.PRETO, false));
            assertEquals(pilha2.isEmpty(), true);
        }
    }
}