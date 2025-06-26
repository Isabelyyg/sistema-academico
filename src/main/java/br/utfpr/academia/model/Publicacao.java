package br.utfpr.academia.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Publicacao {
    // Core attributes (RF08)
    private int id;
    private int usuarioId;
    private String conteudo;
    private byte[] imagem;
    private String linkVideo;
    private TipoMidia tipoMidia;
    private Timestamp dataPublicacao;
    
    // Interactions (RF09)
    private List<Curtida> curtidas;
    private List<Comentario> comentarios;
    
    // Moderation (RF12)
    private boolean moderada;
    private String motivoModeracao;
    
    // Privacy (RF11)
    private Visibilidade visibilidade;
    
    public enum TipoMidia {
        TEXTO, IMAGEM, VIDEO, LINK
    }
    
    public enum Visibilidade {
        PUBLICO, PRIVADO, CURSO
    }

    // Constructors
    public Publicacao() {
        this.curtidas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.tipoMidia = TipoMidia.TEXTO;
        this.visibilidade = Visibilidade.PUBLICO;
    }

    public Publicacao(int usuarioId, String conteudo) {
        this();
        this.usuarioId = usuarioId;
        this.conteudo = conteudo;
        this.dataPublicacao = new Timestamp(System.currentTimeMillis());
    }

    // Getters and Setters (organized alphabetically)
    public String getConteudo() {
        return conteudo;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public Timestamp getDataPublicacao() {
        return dataPublicacao;
    }

    public int getId() {
        return id;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public String getMotivoModeracao() {
        return motivoModeracao;
    }

    public TipoMidia getTipoMidia() {
        return tipoMidia;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public Visibilidade getVisibilidade() {
        return visibilidade;
    }

    public List<Curtida> getCurtidas() {
        return curtidas;
    }

    public boolean isModerada() {
        return moderada;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public void setDataPublicacao(Timestamp dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
        this.tipoMidia = TipoMidia.IMAGEM;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
        this.tipoMidia = TipoMidia.VIDEO;
    }

    public void setModerada(boolean moderada) {
        this.moderada = moderada;
    }

    public void setMotivoModeracao(String motivoModeracao) {
        this.motivoModeracao = motivoModeracao;
    }

    public void setTipoMidia(TipoMidia tipoMidia) {
        this.tipoMidia = tipoMidia;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setVisibilidade(Visibilidade visibilidade) {
        this.visibilidade = visibilidade;
    }

    // Business Logic Methods
    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
    }

    public void adicionarCurtida(Curtida curtida) {
        this.curtidas.add(curtida);
    }

    public void marcarComoInadequado(String motivo) {
        this.moderada = true;
        this.motivoModeracao = motivo;
    }

    // Utility Methods
    public int getTotalCurtidas() {
        return curtidas.size();
    }

    public int getTotalComentarios() {
        return comentarios.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publicacao that = (Publicacao) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Publicacao{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", tipoMidia=" + tipoMidia +
                ", visibilidade=" + visibilidade +
                '}';
    }

    // Nested Class for Comments (RF09)
    public static class Comentario {
        private int id;
        private int usuarioId;
        private String conteudo;
        private Timestamp data;
        private boolean moderado;

        // Getters/Setters
        public String getConteudo() {
            return conteudo;
        }

        public Timestamp getData() {
            return data;
        }

        public int getId() {
            return id;
        }

        public int getUsuarioId() {
            return usuarioId;
        }

        public boolean isModerado() {
            return moderado;
        }

        public void setConteudo(String conteudo) {
            this.conteudo = conteudo;
        }

        public void setData(Timestamp data) {
            this.data = data;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setModerado(boolean moderado) {
            this.moderado = moderado;
        }

        public void setUsuarioId(int usuarioId) {
            this.usuarioId = usuarioId;
        }
    }

    // Nested Class for Likes
    public static class Curtida {
        private int usuarioId;
        private Timestamp data;

        // Getters/Setters
        public Timestamp getData() {
            return data;
        }

        public int getUsuarioId() {
            return usuarioId;
        }

        public void setData(Timestamp data) {
            this.data = data;
        }

        public void setUsuarioId(int usuarioId) {
            this.usuarioId = usuarioId;
        }
    }
}
