/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.utfpr.academia.session;

import br.utfpr.academia.model.Usuario;

/**
 * Singleton para guardar o usuário que logou na aplicação
 * @Author Isabely Aparecedia Gomes da Costa RA: 2337541
 * @Author Luana Monteiro Ferreira
 */
public class SessaoUsuario {

    private static SessaoUsuario instancia;
    private Usuario usuarioLogado;

    private SessaoUsuario() {
    } // Construtor privado

    public static SessaoUsuario getInstance() {
        if (instancia == null) {
            instancia = new SessaoUsuario();
        }
        return instancia;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public void encerrarSessao() {
        usuarioLogado = null;
    }
}
