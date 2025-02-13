package org.example.models;

public class Professor extends Pessoa{
    private String Salario;
    private String cargaHoraria;

    public Professor(String Nome, String CPF, String Email, String anoNascimento , String Salario, String cargaHoraria){
        super(Nome, CPF, Email, anoNascimento);
        this.Salario = Salario;
        this.cargaHoraria = cargaHoraria;
    }

    public String getSalario(){
        return Salario;
    }
    public String getCargaHoraria(){
        return cargaHoraria;
    }
    public void setSalario(String Salario){
        this.Salario = Salario;
    }
    public void setCargaHoraria(String cargaHoraria){
        this.cargaHoraria = cargaHoraria;
    }
    public String toString(){
        return super.toString() + " Salário: " + this.Salario + " Carga Horaria: " + this.cargaHoraria;
    }
}
