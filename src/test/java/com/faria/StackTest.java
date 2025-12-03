package com.faria;

import org.junit.jupiter.api.Test;
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
}