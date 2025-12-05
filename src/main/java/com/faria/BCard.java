package com.faria;

import java.util.ArrayList;
import java.util.List;

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

    
    public String getCorCarta() {
        return corCarta;
    }

    public void setCorCarta(String corCarta) {
        this.corCarta = corCarta;
    }

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
        cartaInfo.append("| ");
        cartaInfo.append(this.naipe);
        cartaInfo.append(this.numeroDaCarta.getNome());
        // cartaInfo.append("\nCor: " + this.corCarta);
        cartaInfo.append(this.visibilidade ? "○" : "#");
        cartaInfo.append(" |");

        return cartaInfo.toString();
    }


    public Naipes getNaipe() {
        return naipe;
    }

    public void setNaipe(Naipes naipe) {
        this.naipe = naipe;
    }

    public NumCarta getNumeroDaCarta() {
        return numeroDaCarta;
    }

    public void setNumeroDaCarta(NumCarta numeroDaCarta) {
        this.numeroDaCarta = numeroDaCarta;
    }

    public boolean isVisible() {
        return this.visibilidade;
    }

    public void setVisible(boolean visibilidade) {
        this.visibilidade = visibilidade;
    }

    /*
     * Função para geração auxiliar de uma baralho completo e ordenado
     *
     * OBS: Não é necessario instanciar nenhum objeto para chamadada desta função
     *      a Função foi realocada para diminuir linhas de código na classe princial, App.java
     * 
     * @return Lista de Cartas contendo 1 baralho completo
    */
    public static List<BCard> gerarBaralho() {

        List<BCard> baralho = new ArrayList<>();
    
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

        return baralho;
    }
}


