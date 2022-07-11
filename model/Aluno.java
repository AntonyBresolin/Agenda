package com.antonybresolin.agenda.model;

import androidx.annotation.NonNull;

import java.io.Serializable;


//Serializable -> Converte os dados para byte
public class Aluno implements Serializable {
    //0 = não tem id. Semelhante ao NOTNULL
    private int id = 0;
    private String nome;
    private String telefone;

    public Aluno() {

    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private  String email;

    public Aluno(String nome,
                 String telefone,
                 String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return nome + " - "+ telefone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
