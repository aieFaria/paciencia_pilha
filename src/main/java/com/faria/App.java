package com.faria;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.faria.telas.ScreenDeCompra;
import com.faria.telas.ScreenGuardar;
import com.faria.telas.ScreenJogo;
import com.faria.telas.ScreenMain;

public class App {

    public static Stack<BCard> movimenStack;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        iniciar();

    }

    public static void iniciar() {
        // Construtor da tela
        ScreenMain tela = new ScreenMain("PACIÊNCIA", 
                                         new Dimension(950, 800), 
                           true,  
                                 "icon.png");
        
        tela.setLayout(null); 
        

        /*
         * Baralho é definido como uma Lista de BCard
         * O baralho recebe um lista com todas as cartas organizadas sequencialmente
         * Utiliza shuffle cujo @param baralho embaralha as cartas da lista
         * 
         * */
        List<BCard> baralho = new ArrayList<>();
        baralho = BCard.gerarBaralho();
        Collections.shuffle(baralho);

        // Pilhas do monte de compra
        Stack<BCard> pilha1 = new Stack<>(); // Cartas viradas para baixo, isVisible = false

        pilha1.addAll(baralho.subList(0, 24)); //Separa parte do baralho para a pilha de compra

        ScreenDeCompra pilhDeCompra = new  ScreenDeCompra(pilha1, new Stack<>());
        ScreenGuardar pilhasFinais =  new ScreenGuardar(pilhDeCompra);
        ScreenJogo pilhasDoJogo = new ScreenJogo(pilhasFinais, baralho.subList(24, 52));
        pilhasFinais.setScreenJogo(pilhasDoJogo);
        //System.out.println(pilhDeCompra.getPilha1());

        pilhasDoJogo.iniciarORatualizar();
        pilhDeCompra.iniciarORatualizar();
        pilhasFinais.iniciarORatualizar();

        tela.add(pilhasDoJogo);
        tela.add(pilhasFinais);
        tela.add(pilhDeCompra);

        tela.atualizarTela("null");
    }


    
}