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
                criarPaineisVisuais();
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

    private void criarPaineisVisuais() {
        for (int i = 0; i < 7; i++) {
            agrupamentButoesControle grupoPilhaJogo = new agrupamentButoesControle();

            grupoPilhaJogo.setBounds((30 + 105) * i, 0, 105, 500); 
            
            this.pilhaJogo.add(grupoPilhaJogo);
            this.add(grupoPilhaJogo);
        }
    }

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

    private void acaoInserirSimples(PilhaJogo pilhaAlvo, agrupamentButoesControle grupoPilhaJogoAlvo, JButton btnAlvo) {
        int indexClicado = 0;
        Object propIndex = btnAlvo.getClientProperty("index");
        if (propIndex != null) {
            int indexCarta = (int) propIndex;

            indexClicado = (int)((Object) btnAlvo.getClientProperty("index"));
        }
        
        if (EstadoJogo.isPilhaSelected()) {
            Stack<BCard> pilhaMovel = EstadoJogo.getSubPilhaMovimento();
            

            if (verificaPilhaMovimento(pilhaMovel, pilhaAlvo)) {

                Stack<BCard> origemReal = EstadoJogo.getPilhaOrigem();
                int qtdParaRemover = pilhaMovel.size();
                
                for (int i = 0; i < qtdParaRemover; i++) {
                    if (!origemReal.isEmpty()) origemReal.pop();
                }
     
                pilhaAlvo.getJogoPilha().addAll(pilhaMovel);
                moverComSucesso();
            } else {
                cancelarMover();
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

                if (!pilhaOrigem.isEmpty() && !pilhaOrigem.peek().isVisible()) {
                    pilhaOrigem.peek().setVisible(true);
                }
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
            cancelarMover();

            pilhaGuardada.setEnabled(false);
            return;
        }

        Stack<BCard> stackReal = pilhaOrigem.getJogoPilha();

        Stack<BCard> copiaSubPilha = new Stack<>();
        
        // Copia cartas do índice clicado até o topo
        for (int i = indexClicado; i < stackReal.size(); i++) {
            copiaSubPilha.add(stackReal.get(i));
        }

        //System.out.println("Ref: " + tempSubPilha.peek());
        //tempSubPilha = esvaziarPilha(stackReal);

        EstadoJogo.setSelecaoSubPilha(stackReal, copiaSubPilha, btnClicado);
        
        grupoPilhaJogoOrigem.destacarSubPilha(indexClicado);

    }

    private void moverComSucesso() {

        if (EstadoJogo.getBotaoOrigem() != null) {
            EstadoJogo.getBotaoOrigem().repaint();
        }
        
        EstadoJogo.limparSelecao();
        atualizaTodasPilhas();
        
        if (pilhaGuardada != null) pilhaGuardada.iniciarORatualizar();
        this.repaint();
    }

    private void cancelarMover() {
        EstadoJogo.limparSelecao();
        atualizaTodasPilhas();
    }

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

    public boolean inserirPush(BCard movimento, PilhaJogo pbg) {
        if (!pbg.getJogoPilha().isEmpty()) {
            BCard topo = pbg.getJogoPilha().peek();
            
            if (!movimento.getCorCarta().equals(topo.getCorCarta()) &&
                 movimento.getNumeroDaCarta().getValor() == (topo.getNumeroDaCarta().getValor() - 1)) {
                
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

    public void inverte(Stack<BCard> pilhaRef) {
        Stack<BCard> aux = new Stack<>();

        while ( !pilhaRef.isEmpty() ) {
            aux.push( pilhaRef.pop() );
        }

        pilhaRef = aux;
        
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

    public static Stack<BCard> esvaziarPilha(Stack<BCard> pilhaRef, int limite) {

        final int tamanho = pilhaRef.size();
        int i = 0;
        Stack<BCard> retorno = new Stack<>();
        List<BCard> aux = new ArrayList<>();

        while ( i < tamanho - limite && !pilhaRef.isEmpty()) {
            BCard movido = pilhaRef.pop();
            i++;
            aux.add( movido ); //Adiciona cada elemeto removido em sequencia
        }

        for (int j = aux.size() - 1; j >= 0; j--) {
            retorno.push(aux.get(j));
        }

        return retorno;
    }

    public ScreenGuardar getPilhaGuardada() { return pilhaGuardada; }
    public void setPilhaGuardada(ScreenGuardar pilhaGuardada) { this.pilhaGuardada = pilhaGuardada; }
}