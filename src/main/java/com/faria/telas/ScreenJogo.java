package com.faria.telas;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.faria.BCard;
import com.faria.EstadoJogo;
import com.faria.PilhaJogo;
import com.faria.agrupamentButoesControle;
import com.faria.enums.NumCarta;


/**
 * Classe que representa a tela de amostragens das cartas manipulaveis em pilhas do jogo 
 * 
 * @attribute-pilhaGuardada Utilizada para chamar a atualização da tela que armazenam as pilhas 
 *                          que são guardadas;
 * @attribute-listadePilhas É a lista de pilhas que podem ser manipuladas em lote considerando 
 *                          as regras de atribuição.
 * 
 * @constructor O construtor desta classe recebe como parametros, o atributo pilhaGuardada e também
 *              a referencia do baralho, ja limitado ao número de cartas ideal para geração inicial do
 *              jogo, através de {@link #geraPilhasDeJogoInicial(List)}
 *              
 * */



public class ScreenJogo extends JPanel {
    
    private ScreenGuardar pilhaGuardada;
    private List<PilhaJogo> listadePilhas;
    private List<agrupamentButoesControle> pilhaJogo = new ArrayList<>();

    public ScreenJogo (ScreenGuardar pilhaGuardada, List<BCard> baralhoRef) {
        this.pilhaGuardada = pilhaGuardada;
        this.listadePilhas = geraPilhasDeJogoInicial(baralhoRef);

        this.setLocation(1, 1);
        this.setVisible(false);
    }

    public void iniciarORatualizar () {
        if ( !this.isVisible() ) {
            this.setLayout(null);

            if (pilhaJogo.isEmpty()) {
                iniciarAgrupamentoBotoes();
                configurarBotoes();
            }
            
            atualizaTodasPilhas();

            this.setBounds(10, 175, 915, 600);
            this.setBackground(Color.PINK);
            this.setVisible(true);

        } else {
            atualizaTodasPilhas();
            
            if (this.getPilhaGuardada() != null) {
                this.getPilhaGuardada().iniciarORatualizar();
            }
            this.repaint();
            this.revalidate();
        }
    }

    // Cria o agrupamento de botões com base na classe "agrupamentButoesControle"
    private void iniciarAgrupamentoBotoes() {
        for (int i = 0; i < 7; i++) {
            agrupamentButoesControle grupoPilhaJogo = new agrupamentButoesControle();

            grupoPilhaJogo.setBounds((30 + 105) * i, 0, 105, 500); 
            
            this.pilhaJogo.add(grupoPilhaJogo);
            this.add(grupoPilhaJogo);
        }
    }

    // Configura cada botão a nivel de pilha e define a acao que sera executada ao clicar no botão de referencia
    // seja ele na base da pilha ou no meio da pilha (Caso a carta seja visível)
    private void configurarBotoes() {
        for (int ii = 0; ii < this.pilhaJogo.size(); ii++) {
            
            agrupamentButoesControle grupoPilhaJogoAtual = this.pilhaJogo.get(ii);
            PilhaJogo pilhaLogica = this.listadePilhas.get(ii);

            grupoPilhaJogoAtual.setacaoTransferirUnica(e -> {
                JButton btnClicado = (JButton) e.getSource();

                acaoInserirSimples(pilhaLogica, grupoPilhaJogoAtual, btnClicado);
            });

          
            grupoPilhaJogoAtual.setacaoTransferirPilha(e -> {
                JButton btnClicado = (JButton) e.getSource();

                Object propIndex = btnClicado.getClientProperty("index");
                
                if (propIndex != null) {
                    int indexCarta = (int) propIndex;
                    //System.out.println(Integer.valueOf(indexCarta));
                    acaoInserirPilha(pilhaLogica, grupoPilhaJogoAtual, indexCarta, btnClicado);
                }
            });
        }
    }

    // Define a ação mais simples de inserção, movendo uma carta da pilha para outra pilha qualquer, podenso ser 
    // qualquer pilha, mesmo aquelas recusadas.
    private void acaoInserirSimples(PilhaJogo pilhaAlvo, agrupamentButoesControle grupoPilhaJogoAlvo, JButton btnAlvo) {
        int indexClicado = 0;
        Object propIndex = btnAlvo.getClientProperty("index");
        if (propIndex != null) {
            int indexCarta = (int) propIndex;

            indexClicado = (int)((Object) btnAlvo.getClientProperty("index"));
        }
        
        if (EstadoJogo.isPilhaSelected()) {
            Stack<BCard> pilhaMovel = EstadoJogo.getpilhaMovimento();
            

            if (verificaPilhaMovimento(pilhaMovel, pilhaAlvo)) {

                Stack<BCard> origemReal = EstadoJogo.getPilhaOrigem();
                int qtdParaRemover = pilhaMovel.size();
                
                for (int i = 0; i < qtdParaRemover; i++) {
                    if (!origemReal.isEmpty()) origemReal.pop();
                }
     
                pilhaAlvo.getJogoPilha().addAll(pilhaMovel);
                moverComSucesso();
            } else {
                EstadoJogo.limparSelecao();
                atualizaTodasPilhas();
            }
            return;
        }

        if (EstadoJogo.isCardSelected()) {
            

            if (EstadoJogo.getPilhaOrigem() == pilhaAlvo.getJogoPilha()) {
                EstadoJogo.limparSelecao();
                grupoPilhaJogoAlvo.setSelecionado(false);
                return;
            }

            Stack<BCard> pilhaOrigem = EstadoJogo.getPilhaOrigem();
            BCard cartaMover = pilhaOrigem.pop();

            if (inserirPush(cartaMover, pilhaAlvo)) {

                moverComSucesso();
            } else {
            
                // Ordem de execução correta para não gerar falhas visuais
                pilhaOrigem.push(cartaMover);
                EstadoJogo.limparSelecao();
                atualizaTodasPilhas();
                
            }
            return;
        }

        if (!pilhaAlvo.getJogoPilha().isEmpty()) {
            EstadoJogo.setSelecao(pilhaAlvo.getJogoPilha(), btnAlvo);
            grupoPilhaJogoAlvo.setSelecionado(true);
        }
    }

    private void acaoInserirPilha(PilhaJogo pilhaOrigem, agrupamentButoesControle grupoPilhaJogoOrigem, int indexClicado, JButton btnClicado) {
        
        if (EstadoJogo.isCardSelected() || EstadoJogo.isPilhaSelected()) {
            
            EstadoJogo.limparSelecao();
            atualizaTodasPilhas();

            pilhaGuardada.setEnabled(false);
            return;
        }

        Stack<BCard> stackReal = pilhaOrigem.getJogoPilha();

        Stack<BCard> pilhaMovimento = new Stack<>();
        

        for (int i = indexClicado; i < stackReal.size(); i++) {
            pilhaMovimento.add(stackReal.get(i));
        }


        EstadoJogo.setSelecaoDePilha(stackReal, pilhaMovimento, btnClicado);
        
        grupoPilhaJogoOrigem.destacarSelecao(indexClicado);

    }

    // Método auxiliar para realizar a finalização da operação de mover a(s) pilha(s) com sucesso
    private void moverComSucesso() {

        if (EstadoJogo.getBotaoOrigem() != null) {
            EstadoJogo.getBotaoOrigem().repaint();
        }
        
        EstadoJogo.limparSelecao();
        atualizaTodasPilhas();
        
        if (pilhaGuardada != null) 
            pilhaGuardada.iniciarORatualizar();

        this.repaint();
    }

    // Função para atualizar todas as pilhas da parte debaixo do jogo, Referenciadas como Pilhas de jogo
    public void atualizaTodasPilhas() {
        for (int i = 0; i < pilhaJogo.size(); i++) {
            agrupamentButoesControle grupoPilhaJogo = pilhaJogo.get(i);
            Stack<BCard> dados = listadePilhas.get(i).getJogoPilha();
            
            if (!dados.isEmpty() && !dados.peek().isVisible()) {
                dados.peek().setVisible(true);
            }
            
            grupoPilhaJogo.atualizarVisual(dados);
            
            if (EstadoJogo.getPilhaOrigem() == dados) {
                grupoPilhaJogo.setSelecionado(true);
            } else {
                grupoPilhaJogo.setSelecionado(false);
            }
        }
    }

    // Insere uma carta na pilha do jogo, passadas como parâmetro
    public boolean inserirPush(BCard movimento, PilhaJogo pbg) {
        if (!pbg.getJogoPilha().isEmpty()) {
            BCard base = pbg.getJogoPilha().peek();
            
            if (!movimento.getCorCarta().equals(base.getCorCarta()) &&
                 movimento.getNumeroDaCarta().getValor() == (base.getNumeroDaCarta().getValor() - 1)) {
                
                pbg.getJogoPilha().push(movimento);
                pbg.getJogoPilha().peek().setVisible(true);
                return true;
            }
        } else if (movimento.getNumeroDaCarta() == NumCarta.K) {

            pbg.getJogoPilha().push(movimento);
            pbg.getJogoPilha().peek().setVisible(true);
            return true;
        } 
        return false;
    }
    
    // Válida se a pilha de movimento pode ser adicionada na pilha de destino com base no primeiro elemento
    private boolean verificaPilhaMovimento(Stack<BCard> pilhaMovel, PilhaJogo destino) {
        BCard primeiroElemento = pilhaMovel.firstElement();

        if (destino.getJogoPilha().isEmpty()) {
            return primeiroElemento.getNumeroDaCarta() == NumCarta.K;
        }

        if (EstadoJogo.getPilhaOrigem() == pilhaMovel) {
            return false;
        }
        
        return !primeiroElemento.getCorCarta().equals(destino.getJogoPilha().peek().getCorCarta()) &&
                primeiroElemento.getNumeroDaCarta().getValor() == (destino.getJogoPilha().peek().getNumeroDaCarta().getValor() - 1);
    }

    public List<PilhaJogo> geraPilhasDeJogoInicial(List<BCard> baralahoRef) {
        List<PilhaJogo> retorno = new LinkedList<>();
        int indiceBaralho = 0;

        for (int i = 0; i < 7; ++i) {
            PilhaJogo pilhaJogo = new PilhaJogo();
            for (int j = 0; j <= i; ++j) {
                if (indiceBaralho < baralahoRef.size()) {
                    BCard carta = baralahoRef.get(indiceBaralho++);
                    pilhaJogo.getJogoPilha().push(carta);
                }
            }
            if (!pilhaJogo.getJogoPilha().isEmpty()) {
                pilhaJogo.getJogoPilha().peek().setVisible(true);
            }
            retorno.add(pilhaJogo);
        }
        return retorno;
    }

    public ScreenGuardar getPilhaGuardada() { 
        return pilhaGuardada;
    }

    // Definição do set para declaração posterior a instanciação da classe ScreenJogo na função principal
    public void setPilhaGuardada(ScreenGuardar pilhaGuardada) { 
        this.pilhaGuardada = pilhaGuardada; 
    }
}