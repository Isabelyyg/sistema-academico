package br.utfpr.academia.dao;

import br.utfpr.academia.model.Publicacao;
import br.utfpr.academia.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicacaoDAO {

    private Connection connection;

    public PublicacaoDAO() {
        this.connection = Conexao.getConexao();
    }

    // RF08 - Criação de publicações
    public void criarPublicacao(Publicacao publicacao) {
        String sql = "INSERT INTO publicacoes (usuario_id, conteudo) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, publicacao.getUsuarioId());
            stmt.setString(2, publicacao.getConteudo());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    publicacao.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar publicação", e);
        }
    }

    // RF09 - Obter publicações (com controle de privacidade RF11)
    public List<Publicacao> listarPublicacoes(int usuarioId, boolean mesmoCurso) {
        List<Publicacao> publicacoes = new ArrayList<>();
        String sql;

        if (mesmoCurso) {
            sql = "SELECT p.* FROM publicacoes p JOIN usuarios u ON p.usuario_id = u.id "
                    + "WHERE u.id = ? OR u.perfil_publico = TRUE";
        } else {
            sql = "SELECT p.* FROM publicacoes p JOIN usuarios u ON p.usuario_id = u.id "
                    + "WHERE u.perfil_publico = TRUE";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Publicacao p = new Publicacao();
                p.setId(rs.getInt("id"));
                p.setUsuarioId(rs.getInt("usuario_id"));
                p.setConteudo(rs.getString("conteudo"));
//                p.setImagem(rs.getString("imagem"));
//                p.setLink(rs.getString("link"));
//                p.setTipoMidia(rs.getString("tipo_midia"));
                publicacoes.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar publicações", e);
        }
        return publicacoes;
    }

    public List<Publicacao> listarTodasPublicacoes() {
        List<Publicacao> publicacoes = new ArrayList<>();
        String sql = "SELECT p.*, u.nome FROM publicacoes p "
                + "JOIN usuarios u ON p.usuario_id = u.id "
                + "ORDER BY p.data_publicacao DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Publicacao p = new Publicacao();
                p.setId(rs.getInt("id"));
                p.setUsuarioId(rs.getInt("usuario_id"));
                p.setConteudo(rs.getString("conteudo"));

                Usuario u = new Usuario();
                u.setNome(rs.getString("nome"));

                p.setUsuario(u);
                publicacoes.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todas as publicações", e);
        }
        return publicacoes;
    }

    // RF09 - Adicionar interação
    public boolean adicionarInteracao(int publicacaoId, int usuarioId, String tipo, String conteudo) {
        String sql = "INSERT INTO interacoes (publicacao_id, usuario_id, tipo, conteudo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, publicacaoId);
            stmt.setInt(2, usuarioId);
            stmt.setString(3, tipo);
//            stmt.setString(4, ModeracaoService.filtrarPalavroes(conteudo)); // RF12

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar interação", e);
        }
    }
}
