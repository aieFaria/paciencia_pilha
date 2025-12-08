package com.faria.enums;


// Define cada naipe e seus simbolos
public enum Naipes {
    ESPADAS("♠"),
    COPAS("♥"),
    PAUS("♣"),
    OUROS("♦");

    public String simbool;

    Naipes(String simbolo) {
        simbool = simbolo;
    }

    public String getValor(){
        return simbool;
    }
}