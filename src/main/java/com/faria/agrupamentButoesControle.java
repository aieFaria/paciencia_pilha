package com.faria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;

import com.faria.telas.ScreenMain;

/**
 * Representa uma coluna visual do jogo (ScreenJogo) com efeito de cascata.
 * Substitui o JButton simples usado anteriormente.
 */
public class agrupamentButoesControle extends JLayeredPane {

    private static final int LARGURA = 105;
    private static final int ALTURA = 140;
    private static final int ESPACAMENTO = 25; // O espaçamento vertical entre cartas (AQUI ESTÁ O SEGREDO)

    private Stack<BCard> pilhaReferencia;
    private ActionListener acaoTransferirUnica; // O evento que acontecerá ao clicar na carta do topo
    private ActionListener acaoTransferirPilha;

    public agrupamentButoesControle() {
        this.setLayout(null); // JLayeredPane precisa de layout null para posicionamento absoluto
        this.setPreferredSize(new Dimension(LARGURA, ALTURA)); // Tamanho mínimo inicial
    }

    /**
     * Define o listener que será disparado quando o usuário clicar na carta do TOPO.
     */
    public void setacaoTransferirUnica(ActionListener acao) {
        setSelecionado(true);
        this.acaoTransferirUnica = acao;
    }

    public void setacaoTransferirPilha(ActionListener acao) {
        this.acaoTransferirPilha = acao;
    }

    public void atualizarVisual(Stack<BCard> pilha) {
        this.pilhaReferencia = pilha;
        this.removeAll();
        int cont = 0;

        JButton botao = new JButton();

        if( pilha.isEmpty() ) {
            botao = criarCartaVisual("empty.png", 0);

            if (acaoTransferirUnica != null) {
                botao.addActionListener(acaoTransferirUnica);
            }

            this.add(botao, 0);
            this.setPreferredSize(new Dimension(LARGURA, ALTURA));
            this.setSize(LARGURA, ALTURA);
            
        } else {
            for (BCard carta : this.pilhaReferencia) {
                
                String nomeImage = "empty.png";

                if ( carta.isVisible() ) {
                    nomeImage = (carta.getNumeroDaCarta()+"").toLowerCase() + carta.getNaipe()+".png";
                } else {
                    nomeImage = "fundo.png";
                }

                botao = criarCartaVisual(nomeImage, ESPACAMENTO*cont);
                botao.putClientProperty("carta", carta);
                botao.putClientProperty("index", cont);

                if (!pilha.peek().isVisible()) {
                    pilha.peek().setVisible(true);
                }

                if ( !carta.isVisible()) {
                    botao.setEnabled(false);
                }

                if (cont == (pilha.size() - 1) ) {
                    if (acaoTransferirUnica != null) {
                        botao.addActionListener(acaoTransferirUnica);
                    }
                    
                } else {
                    if (acaoTransferirPilha != null) {
                        botao.addActionListener(acaoTransferirPilha);
                    }
                }

                this.add(botao, Integer.valueOf(cont));
                cont++;

            }

            int alturaTotal = ALTURA + (pilha.size() * ESPACAMENTO);
            this.setPreferredSize(new Dimension(LARGURA, alturaTotal));
            this.setSize(LARGURA, alturaTotal);
        }

        this.repaint();
        this.revalidate();
    }

    // Método auxiliar para criar a figura do botão
    private JButton criarCartaVisual(String nomeImagem, int yPos) {
        JButton btn = new JButton();

        Image img = ScreenMain.carregarImagem(nomeImagem);

        if (img != null) {
            Image scaled = img.getScaledInstance(LARGURA, ALTURA, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaled));
            btn.setDisabledIcon(new ImageIcon(scaled));
        }

        
        btn.setBounds(0, yPos, LARGURA, ALTURA);
        
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(true);
        btn.setFocusPainted(false);
        
        return btn;
    }
    
   
    public void setSelecionado(boolean selecionado) {
        if (this.getComponentCount() > 0) {
            
            JButton topo = (JButton) this.getComponent(0); 
            
            if (selecionado) {
                
                topo.setBorderPainted(true); 
                topo.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            } else {
           
                topo.setBorder(null);
                topo.setBorderPainted(false);
            }
        }
    }

    /**
     * Cria borda em volta da seleção
     * @param indexInicial Se for -1, limpa todas as bordas.
     */
    public void destacarSubPilha(int indexInicial) {

        Border bordaTopo = BorderFactory.createMatteBorder(3, 3, 0, 3, Color.YELLOW);
        Border bordaMeio = BorderFactory.createMatteBorder(0, 3, 0, 3, Color.YELLOW);
        Border bordaFundo = BorderFactory.createMatteBorder(0, 3, 3, 3, Color.YELLOW);

        for (Component comp : this.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                Object prop = btn.getClientProperty("index");
                
                if (prop != null) {
                    int idx = (int) prop;
                    if (indexInicial!= -1 && idx >= indexInicial) {

                        btn.setBorderPainted(true);

                        if (idx == indexInicial)
                            btn.setBorder(bordaTopo);
                        else if ( idx > indexInicial )
                            btn.setBorder(bordaFundo);
                        else
                            btn.setBorder(bordaMeio);

                    } else {

                        btn.setBorder(null);
                        btn.setBorderPainted(false);
                    }
                }
            }
        }

        this.repaint();

    }

}
