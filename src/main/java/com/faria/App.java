package com.faria;

import java.awt.Dimension;
import java.util.List;
import java.util.Stack;

import java.util.ArrayList;
import com.faria.enums.*;
import com.faria.telasSecundarias. ScreenDeCompra;
import com.faria.telasSecundarias.ScreenGuardar;

public class App {

    public static Stack<BCard> movimenStack;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        // Construtor da tela
        ScreenMain tela = new ScreenMain("PACIÊNCIA", 
                                         new Dimension(935, 600), 
                           true,  
                                 "icon.png");
        
        tela.setLayout(null); 

        //fds;
        List<BCard> baralho = new ArrayList<>();
        baralho = BCard.gerarBaralho();

        BCard carta1 = new BCard(Naipes.PAUS, NumCarta.AS, BCard.PRETO, false);
        BCard carta2 = new BCard(Naipes.PAUS, NumCarta.DOIS, BCard.PRETO, false);

        // Pilhas do monte de compra
        Stack<BCard> pilha1 = new Stack<>(); // Cartas viradas para baixo, isVisible = false
     
        pilha1.addAll(baralho);

        ScreenDeCompra pilhDeCompra = new  ScreenDeCompra(pilha1, new Stack<>());
        ScreenGuardar pilhasFinais =  new ScreenGuardar(pilhDeCompra);
        //System.out.println(pilhDeCompra.getPilha1());

        pilhDeCompra.iniciarORatualizar();
        pilhasFinais.iniciarORatualizar();

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