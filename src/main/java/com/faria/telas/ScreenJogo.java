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

            // Se for a primeira vez, cria os painéis
            if (pilhaJogo.isEmpty()) {
                criarPaineisVisuais();
                configurarAcoesPaineis();
            }
            
            atualizarVisualTodasPilhas();

            this.setBounds(10, 175, 915, 600); // Altura maior para caber cascata
            this.setBackground(Color.PINK);
            this.setVisible(true);

        } else {
            atualizarVisualTodasPilhas();
            
            if (this.getPilhaGuardada() != null) {
                this.getPilhaGuardada().iniciarORatualizar();
            }
            this.repaint();
            this.revalidate();
        }
    }

    private void criarPaineisVisuais() {
        for (int i = 0; i < 7; i++) {
            agrupamentButoesControle painel = new agrupamentButoesControle();
            // Posiciona horizontalmente
            painel.setBounds((30 + 105) * i, 0, 105, 500); 
            
            this.pilhaJogo.add(painel);
            this.add(painel);
        }
    }

    private void configurarAcoesPaineis() {
        for (int ii = 0; ii < this.pilhaJogo.size(); ii++) {
            
            int index = ii; // Variável final para uso no lambda
            agrupamentButoesControle painelAtual = this.pilhaJogo.get(index);
            PilhaJogo pilhaLogica = this.listadePilhas.get(index);

            // --- 1. AÇÃO AO CLICAR NA CARTA DO TOPO (Carta única) ---
            painelAtual.setacaoTransferirUnica(e -> {
                JButton btnClicado = (JButton) e.getSource();
                tratarCliqueTopo(pilhaLogica, painelAtual, btnClicado);
            });

            // --- 2. AÇÃO AO CLICAR NO MEIO (Mover Cascata) ---
            painelAtual.setacaoTransferirPilha(e -> {
                JButton btnClicado = (JButton) e.getSource();
                // Recupera o índice da carta clicada (propriedade salva no agrupamentButoesControle)
                Object propIndex = btnClicado.getClientProperty("index");
                
                if (propIndex != null) {
                    int indexCarta = (int) propIndex;
                    //System.out.println(Integer.valueOf(indexCarta));
                    tratarCliqueMeio(pilhaLogica, painelAtual, indexCarta, btnClicado);
                }
            });
        }
    }

    // --- LÓGICA DE CLIQUE NO TOPO (phg ou Seleção Simples) ---
    private void tratarCliqueTopo(PilhaJogo pilhaAlvo, agrupamentButoesControle painelAlvo, JButton btnAlvo) {
        int indexClicado = 0;
        Object propIndex = btnAlvo.getClientProperty("index");
        if (propIndex != null) {
            int indexCarta = (int) propIndex;
            System.out.println(Integer.valueOf(indexCarta));
            indexClicado = (int)((Object) btnAlvo.getClientProperty("index"));
        }
        

        // CASO A: Estamos movendo uma SUB-PILHA (Cascata) para cá
        if (EstadoJogo.temSubPilhaSelecionada()) {
            Stack<BCard> subPilha = EstadoJogo.getSubPilhaMovimento();
            
            if (validarMovimentoSubPilha(subPilha, pilhaAlvo)) {

                Stack<BCard> origemReal = EstadoJogo.getPilhaOrigem();
                int qtdParaRemover = subPilha.size();
                
                for (int i = 0; i < qtdParaRemover; i++) {
                    if (!origemReal.isEmpty()) origemReal.pop();
                }
     
                pilhaAlvo.getJogoPilha().addAll(subPilha);
                finalizarMovimentoSucesso();
            } else {
                cancelarMovimentoSubPilha();
            }
            return;
        }

        // CASO B: Estamos movendo CARTA ÚNICA para cá
        if (EstadoJogo.temCartaSelecionada()) {
            
            // Toggle (Deselecionar se clicar na mesma)
            if (EstadoJogo.getPilhaOrigem() == pilhaAlvo.getJogoPilha()) {
                EstadoJogo.limparSelecao();
                painelAlvo.setSelecionado(false);
                return;
            }

            Stack<BCard> pilhaOrigem = EstadoJogo.getPilhaOrigem();
            BCard cartaMover = pilhaOrigem.pop();

            if (inserirPush(cartaMover, pilhaAlvo)) {
                // Sucesso
                if (!pilhaOrigem.isEmpty() && !pilhaOrigem.peek().isVisible()) {
                    pilhaOrigem.peek().setVisible(true);
                }
                finalizarMovimentoSucesso();
            } else {
                // Falha
                pilhaOrigem.push(cartaMover);
                System.out.println("Movimento inválido.");
                // Opcional: Feedback visual de erro
            }
            return;
        }

        // CASO C: Nada selecionado -> Selecionar esta carta
        if (!pilhaAlvo.getJogoPilha().isEmpty()) {
            EstadoJogo.setSelecao(pilhaAlvo.getJogoPilha(), btnAlvo);
            painelAlvo.setSelecionado(true);
        }
    }

    // --- LÓGICA DE CLIQUE NO MEIO (Início de Cascata) ---
    private void tratarCliqueMeio(PilhaJogo pilhaOrigem, agrupamentButoesControle painelOrigem, int indexClicado, JButton btnClicado) {
        
        if (EstadoJogo.temCartaSelecionada() || EstadoJogo.temSubPilhaSelecionada()) {
            pilhaGuardada.setEnabled(false);
            return;
        }

        // Recortar a sub-pilha
        Stack<BCard> stackReal = pilhaOrigem.getJogoPilha();

        // Stack<BCard> tempSubPilha = new Stack<>();
        // List<BCard> tempLista = new ArrayList<>();

        Stack<BCard> copiaSubPilha = new Stack<>();
        
        // Copia as cartas do índice clicado até o topo
        for (int i = indexClicado; i < stackReal.size(); i++) {
            copiaSubPilha.add(stackReal.get(i));
        }

        //System.out.println("Ref: " + tempSubPilha.peek());
        //tempSubPilha = esvaziarPilha(stackReal);

        // Define seleção global
        EstadoJogo.setSelecaoSubPilha(stackReal, copiaSubPilha, btnClicado);
        
        painelOrigem.destacarSubPilha(indexClicado);

    }

    private void finalizarMovimentoSucesso() {

        if (EstadoJogo.getBotaoOrigem() != null) {
            EstadoJogo.getBotaoOrigem().repaint();
        }
        
        EstadoJogo.limparSelecao();
        atualizarVisualTodasPilhas();
        
        if (pilhaGuardada != null) pilhaGuardada.iniciarORatualizar();
        this.repaint();
    }

    private void cancelarMovimentoSubPilha() {
        EstadoJogo.limparSelecao();
        atualizarVisualTodasPilhas();
    }

    public void atualizarVisualTodasPilhas() {
        for (int i = 0; i < pilhaJogo.size(); i++) {
            agrupamentButoesControle painel = pilhaJogo.get(i);
            Stack<BCard> dados = listadePilhas.get(i).getJogoPilha();
            
            // Garante visibilidade do topo
            if (!dados.isEmpty() && !dados.peek().isVisible()) {
                dados.peek().setVisible(true);
            }
            
            painel.atualizarVisual(dados);
            
            // Reaplica borda amarela se for a seleção atual
            if (EstadoJogo.getPilhaOrigem() == dados) {
                painel.setSelecionado(true);
            } else {
                painel.setSelecionado(false);
            }
        }
    }

    public boolean inserirPush(BCard movimento, PilhaJogo pbg) {
        if (!pbg.getJogoPilha().isEmpty()) {
            BCard topo = pbg.getJogoPilha().peek();
            
            // Regra: Cor diferente e Valor descendente
            if (!movimento.getCorCarta().equals(topo.getCorCarta()) &&
                 movimento.getNumeroDaCarta().getValor() == (topo.getNumeroDaCarta().getValor() - 1)) {
                
                pbg.getJogoPilha().push(movimento);
                pbg.getJogoPilha().peek().setVisible(true);
                return true;
            }
        } else if (movimento.getNumeroDaCarta() == NumCarta.K) {
            // Regra: Rei em pilha vazia
            pbg.getJogoPilha().push(movimento);
            pbg.getJogoPilha().peek().setVisible(true);
            return true;
        } 
        return false;
    }
    
    // Validação para sub-pilhas
    private boolean validarMovimentoSubPilha(Stack<BCard> subPilha, PilhaJogo destino) {
        BCard baseDaSubPilha = subPilha.firstElement();

        if (destino.getJogoPilha().isEmpty()) {
            return baseDaSubPilha.getNumeroDaCarta() == NumCarta.K;
        }
        BCard topoDestino = destino.getJogoPilha().peek();
        return !baseDaSubPilha.getCorCarta().equals(topoDestino.getCorCarta()) &&
                baseDaSubPilha.getNumeroDaCarta().getValor() == (topoDestino.getNumeroDaCarta().getValor() - 1);
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