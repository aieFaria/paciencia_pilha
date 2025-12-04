package com.faria.telasSecundarias;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;

import java.util.EmptyStackException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.faria.BCard;
import com.faria.EstadoJogo;
import com.faria.PilhaGuard;
import com.faria.ScreenMain;
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

                if (EstadoJogo.temCartaSelecionada()) {
                       
                    BCard cartaMover = EstadoJogo.pilhaOrigem.pop();

                    try {
                        if (cartaMover.getNaipe() == Naipes.COPAS &&
                        cartaMover.getNumeroDaCarta().getValor() == ( pilhaCopas.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 )) {
                        
             
                        this.pilhaCopas.push(cartaMover);
                 
                        atualizaBotao(controlePilhaCopas, this.pilhaCopas); // Atualiza referencia de destino
                        monteCompraRef.iniciarORatualizar();

                        this.jogada = true;
                    } else {
                        EstadoJogo.pilhaOrigem.push(cartaMover);

                        atualizaBotao(controlePilhaCopas, this.pilhaCopas);
                        monteCompraRef.iniciarORatualizar();
                    }
                    } catch (EmptyStackException ex) {
                        if( cartaMover.getNaipe().equals(pilhaCopas.getNaipeAceito()) && cartaMover.getNumeroDaCarta() == NumCarta.AS) {
                            pilhaCopas.push(cartaMover);
                        }
                    }

                    // Pega próxama carta do monte caso a pilha ja esteja vazia, método automatico
                    if (monteCompraRef.getPilha2().isEmpty()) {
                        monteCompraRef.acao();
                    }

                    EstadoJogo.limparSelecao();
    
                }

                atualizaBotao(controlePilhaCopas, pilhaCopas);
                monteCompraRef.iniciarORatualizar();
                this.repaint();

            });
            
            controlePilhaOuros.setBackground(null);
            controlePilhaOuros.setBounds(105+30, 0, 105, 140);

            controlePilhaOuros.addActionListener(e -> {

                if (EstadoJogo.temCartaSelecionada()) {
                       
                    BCard cartaMover = EstadoJogo.pilhaOrigem.pop();
                    //System.out.println("Ref; " + (cartaMover.getNumeroDaCarta().getValor() == ( pilhaOuros.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 )));

                    try {
                        if (cartaMover.getNaipe() == Naipes.OUROS && (
                        cartaMover.getNumeroDaCarta().getValor() == ( pilhaOuros.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 ))) {
                        
             
                        this.pilhaOuros.push(cartaMover);
                 
                        atualizaBotao(controlePilhaOuros, this.pilhaOuros); // Atualiza referencia de destino
                        monteCompraRef.iniciarORatualizar();

                        this.jogada = true;
                    } else {
                        EstadoJogo.pilhaOrigem.push(cartaMover);

                        atualizaBotao(controlePilhaOuros, this.pilhaOuros);
                        monteCompraRef.iniciarORatualizar();
                    }
                    } catch (EmptyStackException ex) {
                         
                        if( cartaMover.getNaipe().equals(pilhaOuros.getNaipeAceito()) && cartaMover.getNumeroDaCarta() == NumCarta.AS) {
                            
                            pilhaOuros.push(cartaMover);
                            
                        }
                    }

                    if (monteCompraRef.getPilha2().isEmpty()) {
                        monteCompraRef.acao();
                    }

                    EstadoJogo.limparSelecao();
    
                }

                atualizaBotao(controlePilhaOuros, pilhaOuros);
                monteCompraRef.iniciarORatualizar();
                this.repaint();

            });

            
            controlePilhaEspadas.setBackground(null);
            controlePilhaEspadas.setBounds( 270, 0, 105, 140);

            controlePilhaEspadas.addActionListener(e -> {

                if (EstadoJogo.temCartaSelecionada()) {
                       
                    BCard cartaMover = EstadoJogo.pilhaOrigem.pop();
                    //System.out.println("Ref; " + (cartaMover.getNumeroDaCarta().getValor() == ( pilhaOuros.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 )));

                    try {
                        if (cartaMover.getNaipe() == Naipes.ESPADAS && (
                        cartaMover.getNumeroDaCarta().getValor() == ( pilhaEspadas.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 ))) {
                        
             
                        this.pilhaEspadas.push(cartaMover);
                 
                        atualizaBotao(controlePilhaEspadas, this.pilhaEspadas); // Atualiza referencia de destino
                        monteCompraRef.iniciarORatualizar();

                        this.jogada = true;
                    } else {
                        EstadoJogo.pilhaOrigem.push(cartaMover);

                        atualizaBotao(controlePilhaEspadas, this.pilhaEspadas);
                        monteCompraRef.iniciarORatualizar();
                    }
                    } catch (EmptyStackException ex) {
                         
                        if( cartaMover.getNaipe().equals(pilhaEspadas.getNaipeAceito()) && cartaMover.getNumeroDaCarta() == NumCarta.AS) {
                            
                            pilhaEspadas.push(cartaMover);
                            
                        }
                    }


                    if (monteCompraRef.getPilha2().isEmpty()) {
                        monteCompraRef.acao();
                    }
                    EstadoJogo.limparSelecao();
    
                }

                atualizaBotao(controlePilhaEspadas, pilhaEspadas);
                monteCompraRef.iniciarORatualizar();
                this.repaint();

            });
            
            controlePilhaPaus.setBackground(null);
            controlePilhaPaus.setBounds( 405, 0, 105, 140);

            controlePilhaPaus.addActionListener(e -> {

                if (EstadoJogo.temCartaSelecionada()) {
                       
                    BCard cartaMover = EstadoJogo.pilhaOrigem.pop();
                    //System.out.println("Ref; " + (cartaMover.getNumeroDaCarta().getValor() == ( pilhaOuros.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 )));

                    try {
                        if (cartaMover.getNaipe() == Naipes.PAUS && (
                        cartaMover.getNumeroDaCarta().getValor() == ( pilhaPaus.getGuardPilha().peek().getNumeroDaCarta().getValor() + 1 ))) {
                        
                        this.pilhaPaus.push(cartaMover);
                 
                        atualizaBotao(controlePilhaPaus, this.pilhaPaus); // Atualiza referencia de destino
                        monteCompraRef.iniciarORatualizar();

                        this.jogada = true;
                    } else {
                        EstadoJogo.pilhaOrigem.push(cartaMover);

                        atualizaBotao(controlePilhaPaus, this.pilhaPaus);
                        monteCompraRef.iniciarORatualizar();
                    }
                    } catch (EmptyStackException ex) {
                         
                        if( cartaMover.getNaipe().equals(pilhaPaus.getNaipeAceito()) && cartaMover.getNumeroDaCarta() == NumCarta.AS) {
                            
                            pilhaPaus.push(cartaMover);
                            
                        }
                    }

                    if (monteCompraRef.getPilha2().isEmpty()) {
                        monteCompraRef.acao();
                    }
                    EstadoJogo.limparSelecao();
    
                }

                atualizaBotao(controlePilhaPaus, pilhaPaus);
                monteCompraRef.iniciarORatualizar();
                this.repaint();

            });

            atualizaBotao(controlePilhaCopas, pilhaCopas);
            atualizaBotao(controlePilhaPaus, pilhaPaus);
            atualizaBotao(controlePilhaEspadas, pilhaEspadas);
            atualizaBotao(controlePilhaOuros, pilhaOuros);
            monteCompraRef.repaint();

            this.setBounds(240+30+105+30-5, 5, (controlePilhaCopas.getWidth()+30)*4-30, 140);
            this.add(controlePilhaCopas);
            this.add(controlePilhaOuros);
            this.add(controlePilhaEspadas);
            this.add(controlePilhaPaus);
            this.setVisible(true);
            this.setBackground(Color.GREEN);
        } else {
            atualizaBotao(controlePilhaCopas, pilhaCopas);
            atualizaBotao(controlePilhaPaus, pilhaPaus);
            atualizaBotao(controlePilhaEspadas, pilhaEspadas);
            atualizaBotao(controlePilhaOuros, pilhaOuros);
            monteCompraRef.repaint();
            monteCompraRef.revalidate();
            this.revalidate();
            this.repaint();
        }
    }

    /*
     * Atualiza a carta do topo do monte conforme há a movimentação das cartas
     * @param   botão de referncia para ser aatualizado
     * @param   (PilhaGuard) pilha de referencia para ser analisado o topo
     */
    private void atualizaBotao(JButton botao, PilhaGuard pilhaRef) {
        String nomeImage = "";

        if ( !pilhaRef.getGuardPilha().isEmpty() ) {
    
            nomeImage = (pilhaRef.getGuardPilha().peek().getNumeroDaCarta()+"").toLowerCase() + 
                         pilhaRef.getGuardPilha().peek().getNaipe()+".png";

        } else if (pilhaRef.equals(pilhaCopas)) {
            nomeImage = "emptyCOPAS.png";
        } else if (pilhaRef.equals(pilhaEspadas)) {
            nomeImage = "emptyESPADAS.png";
        } else if (pilhaRef.equals(pilhaPaus)) {
            nomeImage = "emptyPAUS.png";
        } else if (pilhaRef.equals(pilhaOuros)) {
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

    
    
}
