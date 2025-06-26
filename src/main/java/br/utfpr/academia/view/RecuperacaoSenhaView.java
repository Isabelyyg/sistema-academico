package br.utfpr.academia.view;

import javax.swing.*;

public class RecuperacaoSenhaView extends JFrame {
    private JTextField txtEmail, txtResposta;
    private JButton btnRecuperar;

    public RecuperacaoSenhaView() {
        setTitle("Recuperação de Senha");
        setSize(400, 200);
        setLocationRelativeTo(null);
        
//        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
//        
//        panel.add(new JLabel("Email Institucional:"));
//        txtEmail = new JTextField();
//        panel.add(txtEmail);
//        
//        panel.add(new JLabel("Resposta Secreta:"));
//        txtResposta = new JTextField();
//        panel.add(txtResposta);
        
        btnRecuperar = new JButton("Recuperar Senha");
        btnRecuperar.addActionListener(e -> recuperarSenha());
//        panel.add(btnRecuperar);
//        
//        add(panel);
    }

    private void recuperarSenha() {
        try {
            String email = txtEmail.getText();
            String resposta = txtResposta.getText();
            
//            String senha = new LoginController().recuperarSenha(email, resposta);
//            JOptionPane.showMessageDialog(this, "Sua senha é: " + senha);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
