package com.faria;

import org.junit.jupiter.api.Test;

import com.faria.enums.Naipes;
import com.faria.enums.NumCarta;
import com.faria.MonteDeCompra;

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
            
            pilha = MonteDeCompra.esvaziarPilha( pilha2 );

            //assertEquals(pilha.peek(), new BCard(Naipes.ESPADAS, NumCarta.AS, BCard.PRETO, false));
            assertEquals(pilha2.isEmpty(), true);
        }
    }
}