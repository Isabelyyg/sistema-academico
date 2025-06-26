package br.utfpr.academia.view;

import br.utfpr.academia.controller.UsuarioController;
import br.utfpr.academia.model.Usuario;
import br.utfpr.academia.service.ValidacaoService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroView extends JFrame {
    // Componentes RF01-RF02 (campos obrigatórios)
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;
    private JTextField txtNome;
    private JComboBox<String> cmbCurso;
    private JComboBox<Integer> cmbSemestre;
    
    // Componentes RF04 (recuperação)
    private JComboBox<String> cmbPerguntaSecreta;
    private JTextField txtRespostaSecreta;
    
    // Componentes RF07 (opcionais)
    private JButton btnSelecionarFoto;
    private JTextArea txtInteresses;
    private JCheckBox chkPerfilPublico;
    
    public CadastroView() {
        configurarJanela();
        initComponents();
    }

    private void configurarJanela() {
        setTitle("Cadastro de Usuário");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // RF01 - Email institucional
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Email Institucional:"));
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panelPrincipal.add(txtEmail, gbc);
        
        // RF02 - Senha com validação
        gbc.gridy++; gbc.gridx = 0;
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Senha (8+ caracteres com símbolo):"));
        gbc.gridx = 1;
        txtSenha = new JPasswordField();
        panelPrincipal.add(txtSenha, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Confirmar Senha:"));
        gbc.gridx = 1;
        txtConfirmarSenha = new JPasswordField();
        panelPrincipal.add(txtConfirmarSenha, gbc);
        
        // RF07 - Campos obrigatórios
        gbc.gridy++; gbc.gridx = 0;
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Nome Completo:"));
        gbc.gridx = 1;
        txtNome = new JTextField();
        panelPrincipal.add(txtNome, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Curso:"));
        gbc.gridx = 1;
        cmbCurso = new JComboBox<>(new String[]{
            "Ciência da Computação", 
            "Engenharia de Software",
            "Engenharia Elétrica",
            "Administração"
        });
        panelPrincipal.add(cmbCurso, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Semestre:"));
        gbc.gridx = 1;
        cmbSemestre = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        panelPrincipal.add(cmbSemestre, gbc);
        
        // RF04 - Pergunta secreta
        gbc.gridy++; gbc.gridx = 0;
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Pergunta Secreta:"));
        gbc.gridx = 1;
        cmbPerguntaSecreta = new JComboBox<>(new String[]{
            "Qual o nome do seu primeiro pet?",
            "Qual sua cidade natal?",
            "Qual seu filme favorito?"
        });
        panelPrincipal.add(cmbPerguntaSecreta, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Resposta Secreta:"));
        gbc.gridx = 1;
        txtRespostaSecreta = new JTextField();
        panelPrincipal.add(txtRespostaSecreta, gbc);
        
        // RF07 - Campos opcionais
        gbc.gridy++; gbc.gridx = 0;
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Foto (opcional):"));
        gbc.gridx = 1;
        btnSelecionarFoto = new JButton("Selecionar...");
        btnSelecionarFoto.addActionListener(this::selecionarFoto);
        panelPrincipal.add(btnSelecionarFoto, gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        adicionarComponente(panelPrincipal, gbc, 0, new JLabel("Interesses Acadêmicos (opcional):"));
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtInteresses = new JTextArea(3, 20);
        panelPrincipal.add(new JScrollPane(txtInteresses), gbc);
        
        gbc.gridy++; gbc.gridx = 0;
        gbc.gridwidth = 2;
        chkPerfilPublico = new JCheckBox("Perfil Público (visível para todos)");
        chkPerfilPublico.setSelected(true);
        panelPrincipal.add(chkPerfilPublico, gbc);
        
        // Botão de cadastro
        gbc.gridy++; gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JButton btnCadastrar = new JButton("Finalizar Cadastro");
        btnCadastrar.addActionListener(this::cadastrarUsuario);
        panelPrincipal.add(btnCadastrar, gbc);
        
        add(panelPrincipal, BorderLayout.CENTER);
    }

    private void adicionarComponente(JPanel panel, GridBagConstraints gbc, int y, JComponent component) {
        gbc.gridy = y;
        gbc.gridx = 0;
        panel.add(component, gbc);
    }

    private void selecionarFoto(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            // Lógica para carregar imagem seria implementada aqui
            JOptionPane.showMessageDialog(this, "Foto selecionada com sucesso!");
        }
    }

    private void cadastrarUsuario(ActionEvent evt) {
        try {
            validarCamposObrigatorios();
            
            Usuario usuario = new Usuario();
            usuario.setEmail(txtEmail.getText());
            usuario.setSenha(new String(txtSenha.getPassword()));
            usuario.setNome(txtNome.getText());
            usuario.setCurso((String) cmbCurso.getSelectedItem());
            usuario.setSemestre((Integer) cmbSemestre.getSelectedItem());
            usuario.setPerguntaSecreta((String) cmbPerguntaSecreta.getSelectedItem());
            usuario.setRespostaSecreta(txtRespostaSecreta.getText());
            usuario.setInteresses(txtInteresses.getText());
            usuario.setPerfilPublico(chkPerfilPublico.isSelected());
            
            new UsuarioController().cadastrarUsuario(usuario);
            JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
            this.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro no Cadastro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarCamposObrigatorios() throws Exception {
        // RF01 - Validação de email
        if (!ValidacaoService.validarEmail(txtEmail.getText())) {
            throw new Exception("O email deve ser institucional (@alunos.utfpr.edu.br)");
        }
        
        // RF02 - Validação de senha
        String senha = new String(txtSenha.getPassword());
        if (!ValidacaoService.validarSenha(senha)) {
            throw new Exception("A senha deve ter 8+ caracteres e pelo menos 1 símbolo");
        }
        
        if (!senha.equals(new String(txtConfirmarSenha.getPassword()))) {
            throw new Exception("As senhas não coincidem");
        }
        
        // RF07 - Campos obrigatórios
        if (txtNome.getText().trim().isEmpty()) {
            throw new Exception("O nome completo é obrigatório");
        }
        
        if (txtRespostaSecreta.getText().trim().isEmpty()) {
            throw new Exception("A resposta secreta é obrigatória");
        }
    }
}
