package com.faria;

import com.faria.enums.Naipes;
import com.faria.enums.NumCarta;

// Classe que define uma carta de baralho
public class BCard extends Object{

    // Constantes para evitar erros de digitação
    public static final String VERMELHO = "vermelho";
    public static final String PRETO = "preto";

    private Naipes naipe; //espadas, copas, paus, ouros
    private NumCarta numeroDaCarta; //ás = 1, 2 = 2, ..., k=13
    private String corCarta; //vermelho ou preto
    private boolean visibilidade; //True → carta virada pra cima, False → carta virada para baixo

    
    // Construtor padrão de uma Carta
    public BCard(Naipes naipe, NumCarta numeroDaCarta, String cor, boolean visibilidade){
        this.naipe = naipe;
        this.numeroDaCarta = numeroDaCarta;
        this.corCarta = cor;
        this.visibilidade = visibilidade;
    }

    @Override
    public String toString() {

        StringBuilder cartaInfo = new StringBuilder();
        cartaInfo.append("|| ");
        cartaInfo.append(this.naipe.getValor());
        cartaInfo.append(this.numeroDaCarta.getNome());
        // cartaInfo.append("\nCor: " + this.corCarta);
        cartaInfo.append(this.visibilidade ? "○" : "#");
        cartaInfo.append(" ||");

        return cartaInfo.toString();
    }
    public boolean isVisible() {
        return this.visibilidade;
    }

    public void setVisible(boolean visibilidade) {
        this.visibilidade = visibilidade;
    }

}


