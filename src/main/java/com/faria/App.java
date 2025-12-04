package com.faria;

import java.awt.Dimension;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;

import java.util.ArrayList;
import com.faria.enums.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // Construtor da tela
        ScreenMain tela = new ScreenMain("PACIÊNCIA", 
                                         new Dimension(600, 600), 
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
        

        MonteDeCompra pilhDeCompra = new MonteDeCompra(pilha1, new Stack<>());

        pilhDeCompra.iniciarORatualizar();
        tela.add(pilhDeCompra);

        tela.atualizarTela("null");

        // System.out.println(pilhDeCompra.toString());
        // pilhDeCompra.acao();
        // System.out.println(pilhDeCompra.toString());
        // pilhDeCompra.acao();
        // System.out.println(pilhDeCompra.toString());
        // pilhDeCompra.acao();
        // System.out.println(pilhDeCompra.toString());
        
        // tela.print(pilhDeCompra.toString());
        // pilhDeCompra.acao();
        // tela.print("\n" + pilhDeCompra.toString());

        
    }

    public void monteCompras() {

    }
    
}