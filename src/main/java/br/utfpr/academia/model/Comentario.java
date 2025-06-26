package br.utfpr.academia.model;

import java.sql.Timestamp;

public class Comentario {
    private int id;
    private int publicacaoId;
    private int usuarioId;
    private String conteudo;
    private Timestamp dataComentario;
    private boolean moderado;

    // Getters
    public String getConteudo() {
        return conteudo;
    }

    public Timestamp getDataComentario() {
        return dataComentario;
    }

    public int getId() {
        return id;
    }

    public boolean getModerado() {
        return moderado;
    }

    public int getPublicacaoId() {
        return publicacaoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    // Setters
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setDataComentario(Timestamp dataComentario) {
        this.dataComentario = dataComentario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModerado(boolean moderado) {
        this.moderado = moderado;
    }

    public void setPublicacaoId(int publicacaoId) {
        this.publicacaoId = publicacaoId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
