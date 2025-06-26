package br.utfpr.academia.controller;

import br.utfpr.academia.dao.UsuarioDAO;
import br.utfpr.academia.model.Usuario;

public class LoginController {
    private UsuarioDAO usuarioDAO;
    
    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public Usuario autenticar(String email, String senha) {
        try {
            return usuarioDAO.buscarPorEmailESenha(email, senha);
        } catch (Exception e) {
            throw new RuntimeException("Email ou senha inválidos");
        }
    }
    
    public String recuperarSenha(String email, String respostaSecreta) {
        return usuarioDAO.recuperarSenha(email, respostaSecreta);
    }
}
