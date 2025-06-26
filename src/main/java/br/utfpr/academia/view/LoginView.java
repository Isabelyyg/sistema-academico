package br.utfpr.academia.view;

import br.utfpr.academia.controller.LoginController;
import br.utfpr.academia.model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnLogin, btnCadastrar, btnRecuperar;

    public LoginView() {
        configurarJanela();
        initComponents();
    }

    private void configurarJanela() {
        setTitle("Login - Sistema Acadêmico UTFPR");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Logo (simulado)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblLogo = new JLabel(new ImageIcon("src/main/resources/images/logo_utfpr.png")); // Substitua pelo caminho real
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblLogo, gbc);

        // Campos de Login
        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Email Institucional:"), gbc);

        gbc.gridy++;
        txtEmail = new JTextField();
        txtEmail.setToolTipText("Digite seu email @alunos.utfpr.edu.br");
        panel.add(txtEmail, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Senha:"), gbc);

        gbc.gridy++;
        txtSenha = new JPasswordField();
        txtSenha.setToolTipText("Mínimo 8 caracteres com pelo menos 1 símbolo");
        panel.add(txtSenha, gbc);

        // Botões
        gbc.gridy++;
        btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(this::realizarLogin);
        panel.add(btnLogin, gbc);

        gbc.gridx++;
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(this::abrirCadastro);
        panel.add(btnCadastrar, gbc);

        // Link de recuperação
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        btnRecuperar = new JButton("Esqueci minha senha");
        btnRecuperar.setBorderPainted(false);
        btnRecuperar.setForeground(Color.BLUE);
        btnRecuperar.setContentAreaFilled(false);
        btnRecuperar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRecuperar.addActionListener(this::abrirRecuperacao);
        panel.add(btnRecuperar, gbc);

        // Adiciona os componentes principais
        add(panel, BorderLayout.CENTER);

        // Configura o botão Enter para login
        getRootPane().setDefaultButton(btnLogin);
    }

    private void realizarLogin(ActionEvent evt) {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        try {
            Usuario usuarioAutenticado = new LoginController().autenticar(email, senha);;
            
            if (usuarioAutenticado != null) {
//                new PrincipalView(usuarioAutenticado).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Usuário ou senha inválidos", 
                    "Erro de Autenticação", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Erro no Login", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCadastro(ActionEvent evt) {
        new CadastroView().setVisible(true);
    }

    private void abrirRecuperacao(ActionEvent evt) {
        new RecuperacaoSenhaView().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Define o look and feel do sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
