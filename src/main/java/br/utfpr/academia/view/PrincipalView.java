package br.utfpr.academia.view;

import javax.swing.*;
import java.awt.*;

public class PrincipalView extends JFrame {
    private JTabbedPane abas;
    
    public PrincipalView() {
        setTitle("Sistema Acadêmico");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        abas = new JTabbedPane();
//        abas.addTab("Publicações", new PublicacoesPanel());
//        abas.addTab("Perfil", new PerfilPanel());
        
        add(abas);
    }
    
    // Classes internas para os painéis
//    class PublicacoesPanel extends JPanel {
//        public PublicacoesPanel() {
//            setLayout(new BorderLayout());
//            add(new JLabel("Feed de Publicações", SwingConstants.CENTER), BorderLayout.CENTER);
//        }
//    }
//    
//    class PerfilPanel extends JPanel {
//        public PerfilPanel() {
//            setLayout(new BorderLayout());
//            add(new JLabel("Configurações do Perfil", SwingConstants.CENTER), BorderLayout.CENTER);
//        }
//    }
}
