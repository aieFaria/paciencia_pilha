package com.faria;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.faria.enums.Naipes;
import com.faria.enums.NumCarta;
import com.faria.telasSecundarias. ScreenDeCompra;
import com.faria.telasSecundarias.ScreenGuardar;
import com.faria.telasSecundarias.ScreenJogo;

public class App {

    public static Stack<BCard> movimenStack;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        // Construtor da tela
        ScreenMain tela = new ScreenMain("PACIÊNCIA", 
                                         new Dimension(950, 800), 
                           true,  
                                 "icon.png");
        
        tela.setLayout(null); 

        //fds;
        List<BCard> baralho = new ArrayList<>();
        baralho = BCard.gerarBaralho();

        Collections.shuffle(baralho);

        BCard carta1 = new BCard(Naipes.PAUS, NumCarta.AS, BCard.PRETO, false);
        BCard carta2 = new BCard(Naipes.PAUS, NumCarta.DOIS, BCard.PRETO, false);

        // Pilhas do monte de compra
        Stack<BCard> pilha1 = new Stack<>(); // Cartas viradas para baixo, isVisible = false
        System.out.println(baralho.get(24));
        System.out.println(baralho.get(51));
        pilha1.addAll(baralho.subList(0, 24));

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
        
        // tela.print(pilhDeCompra.toString());
        // pilhDeCompra.acao();
        // tela.print("\n" + pilhDeCompra.toString());

        
    }

    public void monteCompras() {

    }


    
}