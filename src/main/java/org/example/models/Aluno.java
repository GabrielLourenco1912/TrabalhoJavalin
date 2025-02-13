package org.example.models;

public class Aluno extends Pessoa {

    public Aluno(String Nome, String CPF, String Email, String anoNascimento) {
        super(Nome, CPF, Email, anoNascimento);
    }

    public String toString() {
        return super.toString();
    }
}
