package com.faria.enums;

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