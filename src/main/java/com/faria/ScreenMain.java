package com.faria;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.faria.telasSecundarias. ScreenDeCompra;
import com.faria.telasSecundarias.ScreenGuardar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/* Classe de controle para melhor visibilidade do código
 *
 *
 */

public class ScreenMain extends JFrame {

    private JTextArea console = new JTextArea();
    public static  ScreenDeCompra pilhaCompra;
    public static ScreenGuardar pilhasFinais;

    public ScreenMain(String titulo, Dimension tamanho, boolean visibilidade, String icone) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configuração padrão
        //this.setIconImage();

        // Configurações pertinentes a nossa aplicação
        this.setTitle(titulo);
        this.setSize(tamanho);
        this.setVisible(visibilidade);
        this.setIconImage(carregarImagem(icone));

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

    /*
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
            System.out.println("Imagem não encontrada <" + caminho + ">"); //Insere o caminho completo para visualização
            return null;
        }

        return img;
    }

}