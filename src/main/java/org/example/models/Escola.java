package org.example.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

class Escola {
    private String Nome;
    private String Telefone;
    private ArrayList<Pessoa> pessoas;
    private ArrayList<Disciplina> disciplinas;

    public Escola(String Nome, String Telefone) {
        this.Nome = Nome;
        this.Telefone = Telefone;
        this.pessoas = new ArrayList<>();
        this.disciplinas = new ArrayList<>();
    }

    public Connection getConnection() {
        String jdbcUrl = "jdbc:mysql://wagnerweinert.com.br:3306/tads24_lourenco";
        String username = "tads24_lourenco";
        String password = "tads24_lourenco";

        try {
            Connection con = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Conexão estabelecida com sucesso!");
            return con;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean adicionarAluno(Aluno aluno) {

        for (Pessoa Aluno : pessoas) {
            if (Aluno.getCPF().equals(aluno.getCPF())) {
                return false;
            }
        }

        try(Connection con = getConnection()) {

            String sql = "INSERT INTO Aluno_Escola(nome,CPF,email,ano_nascimento) VALUES (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getCPF());
            ps.setString(3, aluno.getEmail());
            ps.setString(4, aluno.getAnoNascimento());
            int res = ps.executeUpdate();

            if (res > 1) {
                System.out.println("Registro adicionado com sucesso");
            } else {
                System.out.println("Deu ruim!");
            }

            pessoas.add(aluno);
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean adicionarProfessor(Professor professor) {
        for (Pessoa Professor : pessoas) {
            if (Professor.getCPF().equals(professor.getCPF())) {
                return false;
            }
        }

        try(Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "INSERT INTO Professor_Escola(nome,CPF,email,ano_nascimento,salario,carga_horaria) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, professor.getNome());
            ps.setString(2, professor.getCPF());
            ps.setString(3, professor.getEmail());
            ps.setString(4, professor.getAnoNascimento());
            ps.setString(5, professor.getSalario());
            ps.setString(6, professor.getCargaHoraria());
            int res = ps.executeUpdate();

            if (res > 1) {
                System.out.println("Registro adicionado com sucesso");
            } else {
                System.out.println("Deu ruim!");
            }

            pessoas.add(professor);
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean removerProfessor(String cpf) {

        try(Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "DELETE FROM Professor_Escola WHERE CPF = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cpf);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Professor excluído com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum professor encontrado com o CPF fornecido.");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public void buscarAluno(String Nome) {

        try(Connection con = getConnection()) {

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
    public Pessoa buscarAlunoCPF(String CPF) {
        for (Pessoa Aluno : pessoas) {
            if (Aluno.getCPF().equalsIgnoreCase(CPF)) {
                return Aluno;
            }
        }
        return null;
    }
    public boolean removerAluno(String CPF) {
        for (Pessoa Aluno : pessoas) {
            if (Aluno.getCPF().equalsIgnoreCase(CPF)) {
                pessoas.remove(Aluno);
                return true;
            }
        }
        return false;
    }
    public String listarAlunos() {
        if (pessoas.size() == 0) {
            return null;
        }
        String listarAlunos = "";
        for (Pessoa aluno : pessoas) {
            if (aluno instanceof Aluno) {
                listarAlunos += aluno + "\n";
            }
        }
        return listarAlunos;
    }
    public String listarProfessores() {
        if (pessoas.size() == 0) {
            return null;
        }
        String listarProfessores = "";
        for (Pessoa professor : pessoas) {
            if (professor instanceof Professor) {
                listarProfessores += professor + "\n";
            }
        }
        return listarProfessores;
    }

    public int contarAlunos() {
        int contadorProfessores = 0;
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Professor) {
                contadorProfessores++;
            }
        }
        return contadorProfessores;
    }

    public int contarProfessores() {
        int contadorAluno = 0;
        for (Pessoa pessoa : pessoas) {
            if (pessoa instanceof Aluno) {
                contadorAluno++;
            }
        }
        return contadorAluno;
    }

    public boolean adicionarDisciplina(Disciplina nova) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equalsIgnoreCase(nova.getCodigo())) {
                return false;
            }
        }
        disciplinas.add(nova);
        return true;
    }
    public Disciplina buscarDisciplina(String Codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equalsIgnoreCase(Codigo)) {
                return disciplina;
            }
        }
        return null;
    }
    public boolean matricularalunoDisciplina(String Codigo , String CPF) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equalsIgnoreCase(Codigo)) {
                for (Pessoa aluno : pessoas) {
                    if (aluno.getCPF().equalsIgnoreCase(CPF)) {
                        disciplina.adicionarAluno(aluno);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean desmatricularalunoDisciplina(String Codigo , String CPF) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equalsIgnoreCase(Codigo)) {
                for (Pessoa aluno : pessoas) {
                    if (aluno.getCPF().equalsIgnoreCase(CPF)) {
                        disciplina.removerAluno(CPF);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String listarAlunosDisciplina(String Codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equalsIgnoreCase(Codigo)) {
                return disciplina.listarAlunos();
            }
        }
        return null;
    }
    public boolean matricularprofessorDisciplina(String Codigo , String CPF) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equalsIgnoreCase(Codigo)) {
                for (Pessoa professor : pessoas) {
                    if (professor.getCPF().equalsIgnoreCase(CPF)) {
                        disciplina.adicionarProfessor(professor);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean desmatricularprofessorDisciplina(String Codigo , String CPF) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equalsIgnoreCase(Codigo)) {
                for (Pessoa professor : pessoas) {
                    if (professor.getCPF().equalsIgnoreCase(CPF)) {
                        disciplina.removerProfessor(CPF);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String listarprofessorDisciplina(String Codigo) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getCodigo().equalsIgnoreCase(Codigo)) {
                return disciplina.listarProfessores();
            }
        }
        return null;
    }

    public String listarDisciplinas() {
        if (disciplinas.isEmpty()) {
            return null;
        }
        String listarDisciplinas = "";
        for (Disciplina disciplina : disciplinas) {
            listarDisciplinas += disciplina + "\n";
        }
        return listarDisciplinas;
    }

    public String toString() {
        return "Nome da Escola: " + Nome + "\nTelefone da Escola: " + Telefone + "\nQuantidade de alunos: " + contarAlunos() + "\n";

    }
}

