package org.example.models;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Arrays;

class Disciplina {
    private String codigo;
    private String nome;
    private Pessoa[] pessoas;
    private int cargaHoraria;

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
    public boolean matricularAluno(@NotNull Aluno aluno, String codigo) {
        try (Connection con = getConnection()) {
            System.out.println("Verificando se o aluno está cadastrado na escola...");

            String verificaSql = "SELECT * FROM Aluno_Escola WHERE cpf = ?";
            PreparedStatement verificaPs = con.prepareStatement(verificaSql);
            verificaPs.setString(1, aluno.getCPF());
            ResultSet verificaRs = verificaPs.executeQuery();

            if (!verificaRs.next()) {
                System.out.println("Aluno não encontrado na escola. Matrícula não realizada.");
                return false;
            }

            System.out.println("Aluno encontrado. Prosseguindo com a matrícula...");

            String verificaMatriculaSql = "SELECT * FROM Matriculas_Aluno WHERE cpf = ? AND codigo_disciplina = ?";
            PreparedStatement verificaMatriculaPs = con.prepareStatement(verificaMatriculaSql);
            verificaMatriculaPs.setString(1, aluno.getCPF());
            verificaMatriculaPs.setString(2, codigo);
            ResultSet verificaMatriculaRs = verificaMatriculaPs.executeQuery();

            if (verificaMatriculaRs.next()) {
                System.out.println("Aluno já está matriculado nesta disciplina.");
                return false;
            }

            String matriculaSql = "INSERT INTO Matriculas_Aluno (cpf, codigo_disciplina) VALUES (?, ?)";
            PreparedStatement matriculaPs = con.prepareStatement(matriculaSql);
            matriculaPs.setString(1, aluno.getCPF());
            matriculaPs.setString(2, codigo);

            int rowsInserted = matriculaPs.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Aluno matriculado com sucesso!");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao matricular aluno", e);
        }
        return false;
    }

    public boolean procurarAlunoDisciplina(String codigo, String cpf) {
        try(Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "SELECT * FROM Matriculas_Aluno WHERE cpf = ? and codigo_disciplina = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%Nome%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Aluno aluno = new Aluno("default", "default", "default", "default");
                aluno.setNome(rs.getString("nome"));
                aluno.setCPF(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setAnoNascimento(rs.getString("ano_nascimento"));

                Arrays.fill(pessoas, null);
                for (int i = 0; i < pessoas.length; i++) {
                    if (pessoas[i] == null) {
                        pessoas[i] = aluno;
                    }
                }
                 return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
        return false;
    }

    public boolean desmatricularAluno(String cpf) {
        try(Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "DELETE FROM Matriculas_Aluno WHERE cpf = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, cpf);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Aluno excluído com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum aluno encontrado com o CPF fornecido.");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
    public void listarAlunos(String codigo) {
        try(Connection con = getConnection()) {
            Arrays.fill(pessoas, null);
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "SELECT * FROM Matriculas_Aluno WHERE codigo_disciplina = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno("default", "default", "default", "default");
                aluno.setNome(rs.getString("nome"));
                aluno.setCPF(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setAnoNascimento(rs.getString("ano_nascimento"));

                for (int i = 0; i < pessoas.length; i++) {
                    if (pessoas[i] == null) {
                        pessoas[i] = aluno;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean matricularProfessor(Professor prof) {
        try (Connection con = getConnection()) {
            System.out.println("Verificando se o professor está cadastrado na escola...");

            String verificaSql = "SELECT * FROM Professor_Escola WHERE cpf = ?";
            PreparedStatement verificaPs = con.prepareStatement(verificaSql);
            verificaPs.setString(1, prof.getCPF());
            ResultSet verificaRs = verificaPs.executeQuery();

            if (!verificaRs.next()) {
                System.out.println("Professor não encontrado na escola. Matrícula não realizada.");
                return false;
            }

            System.out.println("Professor encontrado. Prosseguindo com a matrícula...");

            String verificaMatriculaSql = "SELECT * FROM Matriculas_Professor WHERE codigo_disciplina = ?";
            PreparedStatement verificaMatriculaPs = con.prepareStatement(verificaMatriculaSql);
            verificaMatriculaPs.setString(1, codigo);
            ResultSet verificaMatriculaRs = verificaMatriculaPs.executeQuery();

            if (verificaMatriculaRs.next()) {
                System.out.println("Disciplina já possui um professor");
                return false;
            }

            String matriculaSql = "INSERT INTO Matriculas_Aluno (cpf, codigo_disciplina) VALUES (?, ?)";
            PreparedStatement matriculaPs = con.prepareStatement(matriculaSql);
            matriculaPs.setString(1, prof.getCPF());
            matriculaPs.setString(2, codigo);

            int rowsInserted = matriculaPs.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Professor matriculado com sucesso!");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao matricular aluno", e);
        }
        return false;
    }
    public boolean desmatriularProfessor(String cpf) {
        try(Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "DELETE FROM Matriculas_Professor WHERE cpf = ?";
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

    public void listarProfessores(String codigo) {
        try(Connection con = getConnection()) {
            Arrays.fill(pessoas, null);
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "SELECT * FROM Matriculas_Professor WHERE codigo_disciplina = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Professor professor = new Professor("default", "default", "default", "default", "default", "default");
                professor.setNome(rs.getString("nome"));
                professor.setCPF(rs.getString("cpf"));
                professor.setEmail(rs.getString("email"));
                professor.setAnoNascimento(rs.getString("ano_nascimento"));
                professor.setSalario(rs.getString("salario"));
                professor.setCargaHoraria(rs.getString("carga_horario"));

                for (int i = 0; i < pessoas.length; i++) {
                    if (pessoas[i] == null) {
                        pessoas[i] = professor;
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
    public String toString() {
        return "\nCódigo da disciplina: " + codigo + ", Nome da disciplina: " + nome + ", Carga horária: " + cargaHoraria;
    }
}
