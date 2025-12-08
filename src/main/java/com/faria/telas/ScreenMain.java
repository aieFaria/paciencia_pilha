package com.faria.telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import com.faria.App;

/**
 * Classe que representa tela completa do programa
 * 
 * Para cada jogada realizada é necessária atualização local e/ou global dependendo do caso,
 * Então utiliza-se:
 * 
 * @attribute-pilhaCompra Para chamada de funções para atualização da Classe ScreenDeCompra
 * @attribute-pilhaFinais Perve para chamada de funções para atualização da Classe ScreenGuardar
 *
 */

public class ScreenMain extends JFrame {

    private JTextArea console = new JTextArea();
    public static  ScreenDeCompra pilhaCompra;
    public static ScreenGuardar pilhasFinais;

    public ScreenMain(String titulo, Dimension tamanho, boolean visibilidade, String icone) {

        JMenuBar barraMenu = new JMenuBar();
        JMenu menu = new JMenu("Jogo");
        menu.setMnemonic(KeyEvent.VK_J);

        JMenuItem itemNovo = new JMenuItem("Novo Jogo");
        itemNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0)); // Atalho F2
        itemNovo.addActionListener(e -> {
            this.dispose();
            App.iniciar();
        });
        menu.add(itemNovo);
        barraMenu.add(menu);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configuração padrão
        this.getContentPane().setBackground(Color.PINK);

        // Configurações pertinentes a nossa aplicação
        this.setTitle(titulo);
        this.setSize(tamanho);
        this.setVisible(visibilidade);
        this.setIconImage(carregarImagem(icone));
        this.setJMenuBar(barraMenu);
        
        

        // Acrescentando console por padrão
        console.setFocusable(false);
        console.setBackground(Color.BLACK);
        console.setVisible(true);
        console.setFont(new Font(Font.MONOSPACED, Font.PLAIN,14));
        console.setForeground(Color.WHITE);
        //console.setBounds(1, 1, this.getWidth(), this.getHeight());
        console.setBounds(1, 1, 0, 0);
        //this.add(console);

    }

    // Criação da função null para escrever no console criado
    public void print(String text) {
        this.console.setText(text);
    }

    public void atualizarTela(String texto) {
        this.repaint();
        this.revalidate();
        this.console.setText(texto);
    }

    /**
     * Carregamento de imagem com BufferImage
     * Evita erros na abertura da imagem
     * @param caminho É o nome do arquivo da imagem préviamente salvo na pasta resources.
    */
    public static Image carregarImagem(String caminho) {

        BufferedImage img = null;
        caminho = "src/main/resources/" + caminho; // Caminho padrão da imagen + nome do arquivo de imagem
        try {
        
            File f = new File(caminho);

            if (f.exists()) {

                img = ImageIO.read(f);
            } 
          
        } catch (IOException e) {
            e.printStackTrace();
        }
   
        if (img == null) {
    
            return null;
        }

        return img;
    }

    // Configura a caixa de dialogo aberto quando a condição de vitória for satisfeita
    public static void caixaDialogoVitoria() {

        ImageIcon iconePersonalizado = new ImageIcon(
                ScreenMain.carregarImagem("vitoria.png").getScaledInstance(64, 64, Image.SCALE_SMOOTH)
            );

        Object[] options = {"Iniciar outra partida", "Sair do jogo"};
        String mensagem = "<html><body style='width: 300px'>" + 
                "PARABÉNS VOCÊ CONCLUIU O JOGO!<br>" + 
                "O que deseja fazer?</body></html>";

        JOptionPane optionPane = new JOptionPane(
            mensagem,                        
            JOptionPane.PLAIN_MESSAGE,     
            JOptionPane.YES_NO_OPTION,       
            iconePersonalizado,            
            options,                        
            options[0]                      
        );

        JDialog dialog = optionPane.createDialog("Vitória!");

        dialog.setIconImage(carregarImagem("iconCute.png"));
        dialog.setLocation(300, 350);
        dialog.setSize(350, 150);
        dialog.setResizable(false);


        dialog.setVisible(true); 

        switch (optionPane.getValue() != null ? (String) optionPane.getValue() : "Sair do jogo") {
            case "Iniciar outra partida":
                for (java.awt.Window window : java.awt.Window.getWindows()) {
                    window.dispose();
                }
                App.iniciar();
            break;

            case "Sair do jogo":
                System.exit(0);
            break;


            default:
                System.exit(0);
                break;
        }
    }

}