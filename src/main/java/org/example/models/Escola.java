package org.example.models;

import java.sql.*;
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

        try(Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "INSERT INTO Professor_Escola(nome,cpf,email,ano_nascimento,salario,carga_horaria) VALUES (?,?,?,?,?,?)";
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

            String sql = "DELETE FROM Professor_Escola WHERE cpf = ?";
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
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "SELECT * FROM Aluno_Escola WHERE nome like ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%Nome%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Aluno aluno = new Aluno("default", "default", "default", "default");
                aluno.setNome(rs.getString("nome"));
                aluno.setCPF(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setAnoNascimento(rs.getString("ano_nascimento"));

                pessoas.clear();
                pessoas.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
    public void buscarAlunoCPF(String cpf) {
        try(Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "SELECT * FROM Aluno_Escola WHERE cpf = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Aluno aluno = new Aluno("default", "default", "default", "default");
                aluno.setNome(rs.getString("nome"));
                aluno.setCPF(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setAnoNascimento(rs.getString("ano_nascimento"));

                pessoas.clear();
                pessoas.add(aluno);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
    public boolean removerAluno(String cpf) {
        try(Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "DELETE FROM Aluno_Escola WHERE cpf = ?";
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
    public void listarAlunos() {
        try(Connection con = getConnection()) {
            pessoas.clear();
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "SELECT * FROM Aluno_Escola";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Aluno aluno = new Aluno("default", "default", "default", "default");
                aluno.setNome(rs.getString("nome"));
                aluno.setCPF(rs.getString("cpf"));
                aluno.setEmail(rs.getString("email"));
                aluno.setAnoNascimento(rs.getString("ano_nascimento"));

                pessoas.add(aluno);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
    public void listarProfessores() {
        try(Connection con = getConnection()) {
            pessoas.clear();
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "SELECT * FROM Professor_Escola";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Professor professor = new Professor("default", "default", "default", "default", "default", "default");
                professor.setNome(rs.getString("nome"));
                professor.setCPF(rs.getString("cpf"));
                professor.setEmail(rs.getString("email"));
                professor.setAnoNascimento(rs.getString("ano_nascimento"));
                professor.setSalario(rs.getString("salario"));
                professor.setCargaHoraria(rs.getString("carga_horario"));


                pessoas.add(professor);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public int contarAlunos() {
        try (Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");
            String sql = "SELECT COUNT(*) FROM Aluno_Escola";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
        return 0;
    }
    public int contarProfessores() {
        try (Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");
            String sql = "SELECT COUNT(*) FROM Professor_Escola";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
        return 0;
    }

    public boolean adicionarDisciplina(Disciplina disciplina) {

        try(Connection con = getConnection()) {

            String sql = "INSERT INTO Disciplinas_Escola(nome,codigo_disciplina,carga_horaria) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, disciplina.getNome());
            ps.setString(2, disciplina.getCodigo());
            ps.setInt(3, disciplina.getCargaHoraria());
            int res = ps.executeUpdate();

            if (res > 1) {
                System.out.println("Registro adicionado com sucesso");
            } else {
                System.out.println("Deu ruim!");
            }

            disciplinas.add(disciplina);
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public void buscarDisciplina(String Codigo) {
        try(Connection con = getConnection()) {
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "SELECT * FROM Disciplinas_Escola WHERE codigo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Disciplina disciplina = new Disciplina("default", "default", 0);
                disciplina.setNome(rs.getString("nome_disciplina"));
                disciplina.setCodigo(rs.getString("codigo_disciplina"));
                disciplina.setCargaHoraria(rs.getInt("carga_horaria"));

                disciplinas.clear();
                disciplinas.add(disciplina);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public void listarDisciplinas() {
        try(Connection con = getConnection()) {
            disciplinas.clear();
            System.out.println("Conexão estabelecida com sucesso!");

            String sql = "SELECT * FROM Disciplinas_Escola";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Disciplina disciplina = new Disciplina("default", "default", 0);
                disciplina.setNome(rs.getString("nome_disciplina"));
                disciplina.setCodigo(rs.getString("codigo_disciplina"));
                disciplina.setCargaHoraria(rs.getInt("carga_horaria"));

                disciplinas.add(disciplina);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public String toString() {
        return "Nome da Escola: " + Nome + "\nTelefone da Escola: " + Telefone + "\nQuantidade de alunos: " + contarAlunos() + "\n";

    }
}

