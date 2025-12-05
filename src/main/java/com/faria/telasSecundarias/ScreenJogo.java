package com.faria.telasSecundarias;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.faria.BCard;
import com.faria.EstadoJogo;
import com.faria.PilhaJogo;
import com.faria.ScreenMain;
import com.faria.enums.NumCarta;

public class ScreenJogo extends JPanel {
    
    private ScreenGuardar pilhaGuardada;
    List<PilhaJogo> listadePilhas;
    List<JButton> botoes = new ArrayList<>();

    public ScreenJogo (ScreenGuardar pilhaGuardada, List<BCard> baralhoRef) {
        this.pilhaGuardada = pilhaGuardada;
        this.listadePilhas = geraPilhasDeJogoInicial(baralhoRef);
        PilhaJogo pilha1 = new PilhaJogo();

        //Construtor
        this.setLocation(1, 1);
        this.setVisible(false);
    }

    public void iniciarORatualizar () {

        if ( !this.isVisible() ) {
            this.setLayout(null);

            carregarBotoes();
            configuraBotoes();
            atualizarTodosBotoes();

            this.setBounds(10, 175, 915, 300);
            this.setBackground(Color.PINK);
            this.setVisible(true);

        } else {
            atualizarTodosBotoes();
            this.getPilhaGuardada().iniciarORatualizar(); // Essa única chamada requisita atualização de todas as Screens
            this.repaint();
            this.revalidate();
        }
        
    }

    // Configura cada botão em ordem crescenter, emfileirado
    private void configuraBotoes() {

        for (int ii=0; ii < this.botoes.size(); ii++) {

            int i = ii;

            this.botoes.get(i).addActionListener(e -> {
            
            if (EstadoJogo.temCartaSelecionada()) {
          
                if (EstadoJogo.getPilhaOrigem().equals(this.listadePilhas.get(i).getJogoPilha())) {
                    moverPilha(this.botoes.get(i), this.listadePilhas.get(i).getJogoPilha());
                    return;
                }

                Stack<BCard> pilhaOrigem = EstadoJogo.getPilhaOrigem();
                BCard cartaMover = pilhaOrigem.pop();
                boolean statusMover = false;

                statusMover = inserirPush(cartaMover, this.listadePilhas.get(i));

                if (statusMover) {
                    // Atualização dos componentes envolvidos
                    if (!pilhaOrigem.isEmpty()) {
                        // Assume-se que o método isVisible() controla se está virada
                        if (!pilhaOrigem.peek().isVisible()) {
                            pilhaOrigem.peek().setVisible(true);
                        }
                    }

                    atualizarTodosBotoes(); 
                    atualizaBotao(botoes.get(i), this.listadePilhas.get(i).getJogoPilha());
                    EstadoJogo.getPilhaOrigem(); //verificar
                    pilhaGuardada.iniciarORatualizar();
                    EstadoJogo.getBotaoOrigem().repaint();
                    EstadoJogo.limparSelecao();
                    this.repaint();
                    
                    
                } else {
                    // Movimento inválido: devolve a carta
                    pilhaOrigem.push(cartaMover);
                    System.out.println("Movimento inválido.");
                    EstadoJogo.limparSelecao();
                    // Opcional: Feedback visual de erro
                }

            } else {
                moverPilha(this.botoes.get(i), this.listadePilhas.get(i).getJogoPilha());
            }
                
                // Sempre redesenha ao final
                pilhaGuardada.iniciarORatualizar();
                atualizaBotao(botoes.get(i), this.listadePilhas.get(i).getJogoPilha());
                this.repaint();
                if (this.getParent() != null) this.getParent().repaint();

            });

            atualizaBotao(botoes.get(i), listadePilhas.get(i).getJogoPilha());
            pilhaGuardada.iniciarORatualizar();
            EstadoJogo.limparSelecao();

        }
    }

    void atualizarTodosBotoes() {
        int cont = 0;

        if(listadePilhas == null || botoes == null) {
            return;
        }

        for (JButton botao : this.botoes) {

            String nomeImage = "empty.png";

            if ( !this.listadePilhas.get(cont).getJogoPilha().isEmpty() ) {
                if ( this.listadePilhas.get(cont).getJogoPilha().peek().isVisible() ) {
                    nomeImage = (this.listadePilhas.get(cont).getJogoPilha().peek().getNumeroDaCarta()+"").toLowerCase() + this.listadePilhas.get(cont).getJogoPilha().peek().getNaipe()+".png";
                } else {
                    nomeImage = "fundo.png";
                }

                if (!listadePilhas.get(cont).getJogoPilha().peek().isVisible()) {
                    listadePilhas.get(cont).getJogoPilha().peek().setVisible(true);
                }
            }

            Image img = ScreenMain.carregarImagem(nomeImage); 
            Image scaled = img.getScaledInstance(105, 140, Image.SCALE_SMOOTH);
            botao.setIcon(new ImageIcon(scaled));
            botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
            cont++;
        }

            
            
    }
        

    // Função que verifica se a carta pode ser adicionada na pilha do jogo
    public boolean inserirPush(BCard movimento, PilhaJogo pbg) {

        if( !pbg.getJogoPilha().isEmpty() ) {

            // Verificação condicional para saber se a carta que esta sendo adicionada não é da mesma cor daquela 
            // no topo da pilha
            // Verifica se a carta é menor que aquela no topo da lista
            if ( !movimento.getCorCarta().equals(pbg.getJogoPilha().peek().getCorCarta()) &&
                 movimento.getNumeroDaCarta().getValor() == (pbg.getJogoPilha().peek().getNumeroDaCarta().getValor() - 1)
                ) {
                pbg.getJogoPilha().push(movimento); // Adiciona Carta na pilha

                // Sempre torna o primeiro item da lista visivel por padrão
                pbg.getJogoPilha().peek().setVisible(true); 
                return true;
            }

               // Verifica se a carta que esta sendo adicionada apilha vazia é um K
        } else if ( movimento.getNumeroDaCarta().equals(NumCarta.K) ) {
            pbg.getJogoPilha().push(movimento); // Adiciona Carta na pilha

            // Sempre torna o primeiro item da lista visivel por padrão
            pbg.getJogoPilha().peek().setVisible(true); 
            return true;
        } 

        return false;
        
    }

    private void carregarBotoes() {

        for (int i = 0; i < 7; i++) {
            JButton botao = new JButton();

            botao.setBounds((30+105)*i, 0, 105, 140);
            botao.setBackground(null);
            this.botoes.add(botao);
            this.add(botao);

        }

    }

    /*
     * Atualiza a carta do topo do monte conforme há a movimentação das cartas
     * @param   botão de referncia para ser aatualizado
     * @param   pilha de referencia para ser analisado o topo
     */
    private void atualizaBotao(JButton botao, Stack<BCard> pilhaRef) {
        String nomeImage = "";

        if ( !pilhaRef.isEmpty() ) {
            if ( pilhaRef.peek().isVisible() ) {
                nomeImage = (pilhaRef.peek().getNumeroDaCarta()+"").toLowerCase() + pilhaRef.peek().getNaipe()+".png";
            } else {
                nomeImage = "fundo.png";
            }

            if(!pilhaRef.peek().isVisible()){
                pilhaRef.peek().setVisible(true);
            }
        } else {
           nomeImage = "empty.png";
        }

        

        Image img = ScreenMain.carregarImagem(nomeImage); 
        Image scaled = img.getScaledInstance(105, 140, Image.SCALE_SMOOTH);
        botao.setIcon(new ImageIcon(scaled));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    /*
     * Gera as pilhas de jogo, sendo @param um consumivel desta função
     * ao passo que vai gerando as pilhas vai retirando do baralho
     * 
     */ 
    public List<PilhaJogo> geraPilhasDeJogoInicial(List<BCard> baralahoRef) {

        List<PilhaJogo> retorno = new LinkedList<>();

        int aleatorio = 0;

        for (int i = 0; i < 7; ++i) {

            PilhaJogo pilhaJogo = new PilhaJogo();

            //Progressividade no aumento de cartas por pilha
            for (int j = 0; j <= i; ++j) {

                System.out.println(baralahoRef.get(aleatorio).toString());
                BCard carta = baralahoRef.get(aleatorio);
                //baralahoRef.set((int) aleatorio, null); 

                pilhaJogo.getJogoPilha().push(carta);
                aleatorio++;

            }
            // Seta visibilidade por padrão da carta no topo da pilha como True
            pilhaJogo.getJogoPilha().peek().setVisible(true);

            retorno.add(pilhaJogo);
            
            
            System.out.println("Ref: " + retorno.get(i).getJogoPilha().peek());
        }

        // Ao final atualizar JButtons e pilhas
        atualizarTodosBotoes();
        this.repaint();
        return retorno;

    }

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

    public ScreenGuardar getPilhaGuardada() {
        return pilhaGuardada;
    }

    public void setPilhaGuardada(ScreenGuardar pilhaGuardada) {
        this.pilhaGuardada = pilhaGuardada;
    }

    

}
