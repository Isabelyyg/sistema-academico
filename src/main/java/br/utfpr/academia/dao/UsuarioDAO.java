package br.utfpr.academia.dao;

import br.utfpr.academia.model.Usuario;
import br.utfpr.academia.service.ValidacaoService;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    private final Connection connection;

    public UsuarioDAO() {
        this.connection = Conexao.getConexao();
    }

    // RF01 + RF05 - Cadastro com validações
    public boolean cadastrarUsuario(Usuario usuario) throws IllegalArgumentException {
        if (!ValidacaoService.validarEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Domínio de email inválido. Use @alunos.utfpr.edu.br");
        }

        if (!ValidacaoService.validarSenha(usuario.getSenha())) {
            throw new IllegalArgumentException("Senha deve ter 8+ caracteres e pelo menos 1 símbolo");
        }

        String sql = "INSERT INTO usuarios (email, senha, nome, curso, semestre, pergunta_secreta, resposta_secreta, perfil_publico) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.getCurso());
            stmt.setInt(5, usuario.getSemestre());
            stmt.setString(6, usuario.getPerguntaSecreta());
            stmt.setString(7, usuario.getRespostaSecreta());
            stmt.setBoolean(8, true);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha no cadastro, nenhuma linha afetada");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                    return true;
                }
            }
            return false;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Código de erro para duplicatas
                throw new IllegalArgumentException("Email já cadastrado no sistema");
            }
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro no cadastro", e);
            throw new RuntimeException("Erro ao cadastrar usuário", e);
        }
    }

    // RF03 - Alteração de senha com confirmação
    public boolean alterarSenha(int usuarioId, String senhaAtual, String novaSenha) throws IllegalArgumentException {
        if (!ValidacaoService.validarSenha(novaSenha)) {
            throw new IllegalArgumentException("Nova senha não atende aos requisitos mínimos");
        }

        String sql = "UPDATE usuarios SET senha = ? WHERE id = ? AND senha = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setInt(2, usuarioId);
            stmt.setString(3, senhaAtual);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalArgumentException("Senha atual incorreta ou usuário não encontrado");
            }
            return true;

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao alterar senha", e);
            throw new RuntimeException("Erro ao atualizar senha", e);
        }
    }

    // RF04 - Recuperação de senha
    public String recuperarSenha(String email, String respostaSecreta) throws IllegalArgumentException {
        String sql = "SELECT senha FROM usuarios WHERE email = ? AND resposta_secreta = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, respostaSecreta);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("senha");
            }

            throw new IllegalArgumentException("Combinação email/resposta secreta inválida");
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro na recuperação", e);
            throw new RuntimeException("Erro ao recuperar senha", e);
        }
    }

    // RF06 + RF07 - Atualização de perfil
    public boolean atualizarPerfil(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, curso = ?, semestre = ?, foto = ?, interesses = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCurso());
            stmt.setInt(3, usuario.getSemestre());
            stmt.setBytes(4, usuario.getFoto());
            stmt.setString(5, usuario.getInteresses());
            stmt.setInt(6, usuario.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao atualizar perfil", e);
            throw new RuntimeException("Erro ao atualizar perfil", e);
        }
    }

    // RF11 - Controle de privacidade
    public boolean atualizarPrivacidade(int usuarioId, boolean perfilPublico) {
        String sql = "UPDATE usuarios SET perfil_publico = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, perfilPublico);
            stmt.setInt(2, usuarioId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao atualizar privacidade", e);
            throw new RuntimeException("Erro ao atualizar configurações de privacidade", e);
        }
    }

    // Métodos auxiliares
    public Usuario buscarPorEmailESenha(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return criarUsuarioFromResultSet(rs);
            } else {
                throw new RuntimeException("Usuário ou senha inválidos.");
            }
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro no login", e);
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
    }

    public boolean existeEmail(String email) {
        String sql = "SELECT 1 FROM usuarios WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            return stmt.executeQuery().next();

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao verificar email", e);
            throw new RuntimeException("Erro ao verificar email", e);
        }
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return criarUsuarioFromResultSet(rs);
            }
            return null; // Não encontrou
        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao buscar usuário por ID", e);
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
    }

    private Usuario criarUsuarioFromResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setNome(rs.getString("nome"));
        usuario.setCurso(rs.getString("curso"));
        usuario.setSemestre(rs.getInt("semestre"));
        usuario.setPerguntaSecreta(rs.getString("pergunta_secreta"));
        usuario.setRespostaSecreta(rs.getString("resposta_secreta"));
        usuario.setPerfilPublico(rs.getBoolean("perfil_publico"));
        return usuario;
    }
}
