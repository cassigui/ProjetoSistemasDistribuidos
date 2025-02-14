package org.api.entities;

public class Message {
    private String operacao;
    private String token;
    private String nome;
    private String ra;
    private String senha;
    private int id;
    private Integer status;
    private User usuario;
    private Category categoria;

    public User getUsuario() {
        return usuario;
    }

    public Category getCategoria() {
        return categoria;
    }

    public Integer getStatus() {
        return status;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}


