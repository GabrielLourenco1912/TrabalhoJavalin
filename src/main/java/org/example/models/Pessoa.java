package org.example.models;

public abstract class Pessoa {
    private String Nome;
    private final String CPF;
    private String Email;
    private String anoNascimento;

    public Pessoa(String nome, String CPF, String email, String anoNascimento) {
        this.Nome = nome;
        this.CPF = CPF;
        this.Email = email;
        this.anoNascimento = anoNascimento;
    }
    public String getNome() {
        return Nome;
    }
    public void setNome(String Nome) {
        this.Nome = Nome;
    }
    public String getCPF() {
        return CPF;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getAnoNascimento() {
        return anoNascimento;
    }
    public void setAnoNascimento(String anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public String toString() {
        return "Nome: " + this.Nome + " Cpf: " + this.CPF + " Email: " + this.Email + " Ano de Nascimento: " + this.anoNascimento;
    }
}
