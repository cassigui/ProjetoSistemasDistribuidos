package org.api.entities;

public class User {
    private String ra;
    private String senha;
    private String nome;

    // Getters e Setters
    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String toString() {
        return "User{" +
                ", ra='" + ra + '\'' +
                ", senha='" + senha + '\'' +
                "nome='" + nome + '\'' +
                '}';
    }
}
