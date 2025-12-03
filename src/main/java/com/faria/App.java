package com.faria;

import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import com.faria.enums.*;


public class App {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // Construtor da tela
        ScreenMain tela = new ScreenMain("PACIÊNCIA", 
                                         new Dimension(400, 400), 
                           true,  
                                 "icon.png");

        //fds;
        List<BCard> baralho = new ArrayList<>();


            
        // Gerador de baralho completo
        for (Naipes np : Naipes.values()) {

            String cor="";

            if(np.equals(Naipes.COPAS) || np.equals(Naipes.OUROS))
                cor = BCard.VERMELHO;
            else
                cor = BCard.PRETO;

            for (NumCarta num: NumCarta.values()) {
        
                BCard carta = new BCard(np, num, cor, false);
                baralho.add(carta);
            }

        }
        

        BCard carta1 = new BCard(Naipes.ESPADAS, NumCarta.AS, BCard.PRETO, false);

        System.out.println(baralho);
        
        tela.print(baralho.toString());

    }

    
}