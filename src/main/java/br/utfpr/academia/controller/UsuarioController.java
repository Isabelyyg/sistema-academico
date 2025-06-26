package br.utfpr.academia.controller;

import br.utfpr.academia.dao.UsuarioDAO;
import br.utfpr.academia.model.Usuario;
import br.utfpr.academia.service.ValidacaoService;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void cadastrarUsuario(Usuario usuario) {
        if (!ValidacaoService.validarEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email institucional inválido");
        }

        if (!ValidacaoService.validarSenha(usuario.getSenha())) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres e 1 símbolo.");
        }

        usuarioDAO.cadastrarUsuario(usuario);
    }

    public Usuario buscarUsuarioPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }
}
