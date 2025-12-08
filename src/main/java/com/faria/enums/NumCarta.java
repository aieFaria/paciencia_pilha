package com.faria.enums;

// Enum para numeração das cartas, determinando seu valor (tipo int) e simbolo referente (tipo String)
public enum NumCarta {
    K(13, "K"),
    Q(12, "Q"),
    J(11, "J"),
    DEZ(10, "10"),
    NOVE(9, "9"),
    OITO(8, "8"),
    SETE(7, "7"),
    SEIS(6, "6"),
    CINCO(5, "5"),
    QUATRO(4, "4"),
    TRES(3, "3"),
    DOIS(2, "2"),
    AS(1, "ÀS");

    private int valorCarta;
    private String nomeCarta;

    NumCarta(int valor, String nome) {
        this.valorCarta = valor;
        this.nomeCarta = nome;
    }

    public int getValor() {
        return valorCarta;
    }

    public String getNome() {
        return nomeCarta;
    }
}
