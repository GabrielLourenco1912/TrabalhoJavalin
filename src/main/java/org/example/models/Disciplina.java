package org.example.models;

class Disciplina {
    private String codigo;
    private String nome;
    private Pessoa[] pessoas;
    private int cargaHoraria;

    public Disciplina(String codigo, String nome , int cargaHoraria) {
        this.codigo = codigo;
        this.nome = nome;
        this.pessoas = new Pessoa[21];
        this.cargaHoraria = cargaHoraria;
    }
    public Pessoa[] getPessoas() {
        return pessoas;
    }
    public void setPessoas(Pessoa[] pessoas) {
        this.pessoas = pessoas;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getCargaHoraria() {
        return cargaHoraria;
    }
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    public boolean adicionarAluno(Pessoa aluno) {
        for (int i = 0; i < pessoas.length; i++) {
            if (pessoas[i] != null) {
                if (pessoas[i].getCPF().equals(aluno.getCPF())) {
                    return false;
                }
            }
        }
        for (int i = 0; i < pessoas.length; i++) {
            if (pessoas[i] == null) {
                pessoas[i] = aluno;
                return true;
            }
        }
        return false;
    }

    public boolean removerAluno(String CPF) {
        for (int i = 0; i < pessoas.length; i++) {
            if (pessoas[i] != null) {
                if (pessoas[i].getCPF().equals(CPF)) {
                    pessoas[i] = null;
                    return true;
                }
            }
        }
        return false;
    }
    public String listarAlunos() {
        boolean vazio = true;
        String listarAlunos = "";

        for (Pessoa aluno : pessoas) {
            if (aluno instanceof Aluno) {
                listarAlunos += aluno + "\n";
                vazio = false;
            }
        }
        return vazio ? null : listarAlunos;
    }

    public boolean adicionarProfessor(Pessoa prof) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Professor) {
                return false;
            }
        }
        pessoas[pessoas.length - 1] = prof;
        return false;
    }
    public boolean removerProfessor(String CPF) {
        for (int i = 0; i < pessoas.length; i++) {
            if (pessoas[i] != null) {
                if (pessoas[i].getCPF().equals(CPF)) {
                    pessoas[i] = null;
                    return true;
                }
            }
        }
        return false;
    }
    public String listarProfessores() {
        boolean vazio = true;
        String listarProfessores = "";

        for (Pessoa prof : pessoas) {
            if (prof instanceof Professor) {
                listarProfessores += prof + "\n";
                vazio = false;
            }
        }
        return vazio ? null : listarProfessores;
    }
    public String toString() {
        return "\nCódigo da disciplina: " + codigo + ", Nome da disciplina: " + nome + ", Carga horária: " + cargaHoraria;
    }
}
