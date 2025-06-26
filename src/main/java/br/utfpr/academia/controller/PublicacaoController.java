package br.utfpr.academia.controller;

import br.utfpr.academia.dao.PublicacaoDAO;
import br.utfpr.academia.model.Publicacao;
import java.util.List;

public class PublicacaoController {
    private PublicacaoDAO publicacaoDAO;
    
    public PublicacaoController() {
        this.publicacaoDAO = new PublicacaoDAO();
    }
    
    public boolean criarPublicacao(Publicacao publicacao) {
        return publicacaoDAO.criarPublicacao(publicacao);
    }
    
    public List<Publicacao> listarPublicacoes(int usuarioId, boolean mesmoCurso) {
        return publicacaoDAO.listarPublicacoes(usuarioId, mesmoCurso);
    }
    
    public void adicionarInteracao(int publicacaoId, int usuarioId, String tipo, String conteudo) {
        publicacaoDAO.adicionarInteracao(publicacaoId, usuarioId, tipo, conteudo);
    }
}
