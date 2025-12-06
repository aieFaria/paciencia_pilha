package com.faria.telas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.faria.BCard;
import com.faria.EstadoJogo;
import com.faria.PilhaGuard;
import com.faria.enums.Naipes;
import com.faria.enums.NumCarta;

public class ScreenGuardar extends JPanel {

    private boolean jogada = false;
    private PilhaGuard pilhaOuros, pilhaCopas, pilhaEspadas, pilhaPaus;
    private  ScreenDeCompra monteCompraRef;
    public JButton controlePilhaEspadas = new JButton();
    public JButton controlePilhaOuros = new JButton();
    public JButton controlePilhaPaus = new JButton();
    public JButton controlePilhaCopas = new JButton();
    private ScreenJogo screenJogoRef;
    private boolean vitoria = false;

    // Inicialização padrão trava os Naipes das pilhas
    public ScreenGuardar ( ScreenDeCompra monteCompraRef) {

        this.monteCompraRef = monteCompraRef;

        this.pilhaCopas = new PilhaGuard(Naipes.COPAS);
        this.pilhaOuros = new PilhaGuard(Naipes.OUROS);
        this.pilhaEspadas = new PilhaGuard(Naipes.ESPADAS);
        this.pilhaPaus = new PilhaGuard(Naipes.PAUS);

        this.setLocation(1, 1);
        this.setVisible(false);

    }

    public void iniciarORatualizar () {

        // Atualizar Tela caso a janela ja esteja visivel
        if ( !this.isVisible() ) {
            this.setLayout(null);
            
            controlePilhaCopas.setBackground(null);
            controlePilhaCopas.setBounds(0, 0, 105, 140);

            controlePilhaCopas.addActionListener(e -> {
                if(EstadoJogo.isPilhaSelected()) {return;}

                if (EstadoJogo.isCardSelected()) {

                    if (EstadoJogo.getPilhaOrigem() == pilhaCopas.getGuardPilha()) {

                        moverPilha(controlePilhaCopas, pilhaCopas.getGuardPilha()); 
                        return;
                    }

                    BCard cartaMover = EstadoJogo.pilhaOrigem.pop();
                    boolean statusMover = false;

                    if (this.pilhaCopas.getGuardPilha().isEmpty()) {
                       
                        if (cartaMover.getNaipe() == Naipes.COPAS && cartaMover.getNumeroDaCarta() == NumCarta.AS) {
                            this.pilhaCopas.push(cartaMover);
                            statusMover = true;
                        }
                    } else if (cartaMover.getNaipe() == Naipes.COPAS && (
                        cartaMover.getNumeroDaCarta().getValor() == ( pilhaCopas.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 ))) {
                        
                        this.pilhaCopas.push(cartaMover);
                        statusMover = true;
                 
                    }

                    if (statusMover) {

                        // Atualização dos componentes envolvidos
                        
                        monteCompraRef.iniciarORatualizar();
                        EstadoJogo.getBotaoOrigem().repaint();
                        EstadoJogo.limparSelecao();
                        screenJogoRef.atualizaTodasPilhas();
                        atualizaBotao(controlePilhaCopas, this.pilhaCopas.getGuardPilha());
                        this.repaint();
                        verificarvitoria();
                        this.jogada = true;

                    } else {
                        // Voltar carta para origem
                        EstadoJogo.pilhaOrigem.push(cartaMover);
                        atualizaBotao(controlePilhaCopas, this.pilhaCopas.getGuardPilha());
                        screenJogoRef.atualizaTodasPilhas();
                        monteCompraRef.iniciarORatualizar();
                        this.repaint();
                    }

                    EstadoJogo.limparSelecao();
    
                } else {
                    moverPilha(controlePilhaCopas, this.pilhaCopas.getGuardPilha());
                }

                this.repaint();

            });
            
            controlePilhaOuros.setBackground(null);
            controlePilhaOuros.setBounds(105+30, 0, 105, 140);

            controlePilhaOuros.addActionListener(e -> {

                if(EstadoJogo.isPilhaSelected()) {return;}

                if (EstadoJogo.isCardSelected()) {

                    if (EstadoJogo.getPilhaOrigem() == pilhaOuros.getGuardPilha()) {

                        moverPilha(controlePilhaOuros, pilhaOuros.getGuardPilha()); 
                        return;
                    }
                       
                    BCard cartaMover = EstadoJogo.pilhaOrigem.pop();
                    boolean statusMover = false;

                    if (this.pilhaOuros.getGuardPilha().isEmpty()) {
                       
                        if (cartaMover.getNaipe() == Naipes.OUROS && cartaMover.getNumeroDaCarta() == NumCarta.AS) {
                            this.pilhaOuros.push(cartaMover);
                            statusMover = true;
                        }
                    } else if (cartaMover.getNaipe() == Naipes.OUROS && (
                        cartaMover.getNumeroDaCarta().getValor() == ( pilhaOuros.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 ))) {
                        
                        this.pilhaOuros.push(cartaMover);
                        statusMover = true;
                 
                    }

                    if (statusMover) {

                        // Atualização dos componentes envolvidos

                        monteCompraRef.iniciarORatualizar();
                        EstadoJogo.getBotaoOrigem().repaint();
                        EstadoJogo.limparSelecao();
                        atualizaBotao(controlePilhaOuros, this.pilhaOuros.getGuardPilha());
                        screenJogoRef.atualizaTodasPilhas();
                        this.repaint();
                        this.jogada = true;
                        verificarvitoria();

                    } else {
                        // Voltar carta para origem
                        EstadoJogo.pilhaOrigem.push(cartaMover);
                        atualizaBotao(controlePilhaOuros, this.pilhaOuros.getGuardPilha());
                        monteCompraRef.iniciarORatualizar();
                        this.repaint();
                    }

                    EstadoJogo.limparSelecao();
    
                } else {
                    moverPilha(controlePilhaOuros, this.pilhaOuros.getGuardPilha());
                }

                this.repaint();

            });

            
            controlePilhaEspadas.setBackground(null);
            controlePilhaEspadas.setBounds( 270, 0, 105, 140);

            controlePilhaEspadas.addActionListener(e -> {

                // Evita que movimentos de Subpilhas sejam adicionados
                if(EstadoJogo.isPilhaSelected()) {return;}

                if (EstadoJogo.isCardSelected()) {
                       
                    if (EstadoJogo.getPilhaOrigem() == pilhaEspadas.getGuardPilha()) {

                        moverPilha(controlePilhaEspadas, pilhaEspadas.getGuardPilha()); 
                        return;
                    }
                       
                    BCard cartaMover = EstadoJogo.pilhaOrigem.pop();
                    boolean statusMover = false;

                    if (this.pilhaEspadas.getGuardPilha().isEmpty()) {
                       
                        if (cartaMover.getNaipe() == Naipes.ESPADAS && cartaMover.getNumeroDaCarta() == NumCarta.AS) {
                            this.pilhaEspadas.push(cartaMover);
                            statusMover = true;
                        }
                    } else if (cartaMover.getNaipe() == Naipes.ESPADAS && (
                        cartaMover.getNumeroDaCarta().getValor() == ( pilhaEspadas.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 ))) {
                        
                        this.pilhaEspadas.push(cartaMover);
                        statusMover = true;
                 
                    }

                    if (statusMover) {

                        // Atualização dos componentes envolvidos

                        monteCompraRef.iniciarORatualizar();
                        EstadoJogo.getBotaoOrigem().repaint();
                        EstadoJogo.limparSelecao();
                        atualizaBotao(controlePilhaEspadas, this.pilhaEspadas.getGuardPilha());
                        screenJogoRef.atualizaTodasPilhas();
                        this.repaint();
                        this.jogada = true;
                        verificarvitoria();

                    } else {
                        // Voltar carta para origem
                        EstadoJogo.pilhaOrigem.push(cartaMover);
                        atualizaBotao(controlePilhaEspadas, this.pilhaEspadas.getGuardPilha());
                        monteCompraRef.iniciarORatualizar();
                        this.repaint();
                    }

                    EstadoJogo.limparSelecao();
    
                } else {
                    moverPilha(controlePilhaEspadas, this.pilhaEspadas.getGuardPilha());
                }

                screenJogoRef.atualizaTodasPilhas();
                atualizaBotao(controlePilhaEspadas, pilhaEspadas.getGuardPilha());
                monteCompraRef.iniciarORatualizar();
                this.repaint();

            });
            
            controlePilhaPaus.setBackground(null);
            controlePilhaPaus.setBounds( 405, 0, 105, 140);

            controlePilhaPaus.addActionListener(e -> {

                if(EstadoJogo.isPilhaSelected()) {return;}

                if (EstadoJogo.isCardSelected()) {
                       
                    if (EstadoJogo.getPilhaOrigem() == pilhaPaus.getGuardPilha()) {

                        moverPilha(controlePilhaPaus, pilhaPaus.getGuardPilha()); 
                        return;
                    }
                       
                    BCard cartaMover = EstadoJogo.pilhaOrigem.pop();
                    boolean statusMover = false;

                    if (this.pilhaPaus.getGuardPilha().isEmpty()) {
                       
                        if (cartaMover.getNaipe() == Naipes.PAUS && cartaMover.getNumeroDaCarta() == NumCarta.AS) {
                            this.pilhaPaus.push(cartaMover);
                            statusMover = true;
                        }
                    } else if (cartaMover.getNaipe() == Naipes.PAUS && (
                        cartaMover.getNumeroDaCarta().getValor() == ( pilhaPaus.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 ))) {
                        
                        this.pilhaPaus.push(cartaMover);
                        statusMover = true;
                 
                    }

                    if (statusMover) {  

                        monteCompraRef.iniciarORatualizar();
                        EstadoJogo.getBotaoOrigem().repaint();
                        EstadoJogo.limparSelecao();
                        atualizaBotao(controlePilhaPaus, this.pilhaPaus.getGuardPilha());
                        screenJogoRef.atualizaTodasPilhas();
                        this.repaint();
                        this.jogada = true;
                        verificarvitoria();

                    } else {
                        // Voltar carta para origem
                        EstadoJogo.pilhaOrigem.push(cartaMover);
                        atualizaBotao(controlePilhaPaus, this.pilhaPaus.getGuardPilha());
                        monteCompraRef.iniciarORatualizar();
                        this.repaint();
                    }

                    EstadoJogo.limparSelecao();
    
                } else {
                    moverPilha(controlePilhaPaus, this.pilhaPaus.getGuardPilha());
                }

                atualizaBotao(controlePilhaPaus, pilhaPaus.getGuardPilha());
                monteCompraRef.iniciarORatualizar();
                this.repaint();

            });

            atualizaBotao(controlePilhaCopas, pilhaCopas.getGuardPilha());
            atualizaBotao(controlePilhaPaus, pilhaPaus.getGuardPilha());
            atualizaBotao(controlePilhaEspadas, pilhaEspadas.getGuardPilha());
            atualizaBotao(controlePilhaOuros, pilhaOuros.getGuardPilha());
            monteCompraRef.iniciarORatualizar();
            monteCompraRef.repaint();
            monteCompraRef.revalidate();
            verificarvitoria();

            this.setBounds(240+30+105+30+10, 5, (controlePilhaCopas.getWidth()+30)*4-30, 140);
            this.add(controlePilhaCopas);
            this.add(controlePilhaOuros);
            this.add(controlePilhaEspadas);
            this.add(controlePilhaPaus);
            this.setVisible(true);
            this.setBackground(Color.PINK);
        } else {
            atualizaBotao(controlePilhaCopas, pilhaCopas.getGuardPilha());
            atualizaBotao(controlePilhaPaus, pilhaPaus.getGuardPilha());
            atualizaBotao(controlePilhaEspadas, pilhaEspadas.getGuardPilha());
            atualizaBotao(controlePilhaOuros, pilhaOuros.getGuardPilha());
            monteCompraRef.iniciarORatualizar();
            monteCompraRef.repaint();
            monteCompraRef.revalidate();
            this.revalidate();
            this.repaint();

            verificarvitoria();
        }
    }


    /**
     * Método para mover elementos da pilha
     * @param botão de referencia
     * @param pilha de referencia
     *
     */
    private void moverPilha(JButton botao, Stack<BCard> pilha) {
        if (pilha.isEmpty()) return;

        // Se clicou na mesma que já estava selecionada -> Desmarca
        if (EstadoJogo.getPilhaOrigem() == pilha) {
            EstadoJogo.limparSelecao();
            botao.setBorder(null);
        } else {
            // Nova seleção
            EstadoJogo.setSelecao(pilha, botao);
            botao.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        }
    }

    /**
     * Atualiza a carta do topo do monte conforme há a movimentação das cartas
     * @param   botão de referncia para ser aatualizado
     * @param   (PilhaGuard) pilha de referencia para ser analisado o topo
     */
    private void atualizaBotao(JButton botao, Stack<BCard> pilhaRef) {
        String nomeImage = "empty.png";

        if ( !pilhaRef.isEmpty() ) {
    
            nomeImage = (pilhaRef.peek().getNumeroDaCarta()+"").toLowerCase() + 
                         pilhaRef.peek().getNaipe()+".png";

        } else if (pilhaRef == pilhaCopas.getGuardPilha()) {
            nomeImage = "emptyCOPAS.png";
        } else if (pilhaRef == pilhaEspadas.getGuardPilha()) {
            nomeImage = "emptyESPADAS.png";
        } else if (pilhaRef == pilhaPaus.getGuardPilha()) {
            nomeImage = "emptyPAUS.png";
        } else if (pilhaRef == pilhaOuros.getGuardPilha()) {
            nomeImage = "emptyOUROS.png";
        }
        

        Image img = ScreenMain.carregarImagem(nomeImage); 
        Image scaled = img.getScaledInstance(105, 140, Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(scaled));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    public PilhaGuard getPilhaOuros() {
        return pilhaOuros;
    }

    public void setPilhaOuros(PilhaGuard pilhaOuros) {
        this.pilhaOuros = pilhaOuros;
    }

    public PilhaGuard getPilhaCopas() {
        return pilhaCopas;
    }

    public void setPilhaCopas(PilhaGuard pilhaCopas) {
        this.pilhaCopas = pilhaCopas;
    }

    public PilhaGuard getPilhaEspadas() {
        return pilhaEspadas;
    }

    public void setPilhaEspadas(PilhaGuard pilhaEspadas) {
        this.pilhaEspadas = pilhaEspadas;
    }

    public PilhaGuard getPilhaPaus() {
        return pilhaPaus;
    }

    public void setPilhaPaus(PilhaGuard pilhaPaus) {
        this.pilhaPaus = pilhaPaus;
    }

    public boolean isJogada() {
        return this.jogada;
    }

    public void setJogada(boolean jogada) {
        this.jogada = jogada;
    }

    public void setScreenJogo(ScreenJogo pilhasDoJogo) {
        this. screenJogoRef = pilhasDoJogo;
    }

    public boolean verificarvitoria() {

        if ((13 == pilhaOuros.getGuardPilha().size() && 13 ==  pilhaCopas.getGuardPilha().size() &&
            13 == pilhaEspadas.getGuardPilha().size() && 13 ==  pilhaPaus.getGuardPilha().size())) {
            
        

            ScreenMain.caixaDialogoVitoria();
            
        }
        return vitoria;

    }
    
}
