package org.example.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.models.Aluno;
import org.example.models.Professor;
import org.example.models.Disciplina;

import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.models.Aluno;
import org.example.models.Professor;
import org.example.models.Disciplina;
import org.example.models.Escola;

import java.util.HashMap;
import java.util.Map;

public class DisciplinaController {

    private static Escola escola = new Escola("Minha Escola", "123456789");

    public Handler get = (Context ctx)->{
        ctx.render("indexDisciplina.html");
    };

    public Handler postMatricularAluno = (Context ctx) -> {
        String codigo = ctx.formParam("codigo_disciplina");

        escola.buscarDisciplina(codigo);

        Map<String, Object> dados = new HashMap<>();
        if (escola.getDisciplinas().isEmpty()) {
            dados.put("mensagem", "Disciplina não encontrada. Matrícula não pode ser realizada.");
            ctx.render("resultadoMatricula.html", dados);
            return;
        }
        Disciplina disciplina = escola.getDisciplinas().get(0);

        String nome          = ctx.formParam("nome");
        String cpf           = ctx.formParam("cpf");
        String email         = ctx.formParam("email");
        String anoNascimento = ctx.formParam("anoNascimento");

        Aluno aluno = new Aluno(nome, cpf, email, anoNascimento);
        boolean sucesso = disciplina.matricularAluno(aluno, codigo);

        if (sucesso) {
            dados.put("mensagem", "Aluno matriculado com sucesso!");
        } else {
            dados.put("mensagem", "Falha na matrícula do aluno (talvez já matriculado ou não cadastrado na escola).");
        }
        ctx.render("resultadoMatricula.html", dados);
    };

    public Handler postProcurarAlunoDisciplina = (Context ctx) -> {
        String codigo = ctx.formParam("codigo_disciplina");
        escola.buscarDisciplina(codigo);

        Map<String, Object> dados = new HashMap<>();
        if (escola.getDisciplinas().isEmpty()) {
            dados.put("mensagem", "Disciplina não encontrada.");
            ctx.render("resultadoMatricula.html", dados);
            return;
        }
        Disciplina disciplina = escola.getDisciplinas().get(0);

        String cpf = ctx.formParam("cpf");
        boolean encontrado = disciplina.procurarAlunoDisciplina(codigo, cpf);

        if (encontrado) {
            dados.put("mensagem", "Aluno encontrado na disciplina!");
            dados.put("alunos", disciplina.getPessoas());
        } else {
            dados.put("mensagem", "Aluno não está matriculado nesta disciplina.");
        }
        ctx.render("resultadoMatricula.html", dados);
    };

    public Handler postDesmatricularAluno = (Context ctx) -> {
        String codigo = ctx.formParam("codigo_disciplina");
        escola.buscarDisciplina(codigo);

        Map<String, Object> dados = new HashMap<>();
        if (escola.getDisciplinas().isEmpty()) {
            dados.put("mensagem", "Disciplina não encontrada.");
            ctx.render("resultadoMatricula.html", dados);
            return;
        }
        Disciplina disciplina = escola.getDisciplinas().get(0);

        String cpf = ctx.formParam("cpf");
        boolean sucesso = disciplina.desmatricularAluno(cpf);

        if (sucesso) {
            dados.put("mensagem", "Aluno desmatriculado com sucesso!");
        } else {
            dados.put("mensagem", "Falha ao desmatricular aluno.");
        }
        ctx.render("resultadoMatricula.html", dados);
    };

    public Handler postListarAlunos = (Context ctx) -> {
        String codigo = ctx.formParam("codigo_disciplina");
        escola.buscarDisciplina(codigo);

        Map<String, Object> dados = new HashMap<>();
        if (escola.getDisciplinas().isEmpty()) {
            dados.put("mensagem", "Disciplina não encontrada.");
            ctx.render("listaAlunosDisciplina.html", dados);
            return;
        }
        Disciplina disciplina = escola.getDisciplinas().get(0);

        disciplina.listarAlunos(codigo);
        dados.put("alunos", disciplina.getPessoas());
        ctx.render("listaAlunosDisciplina.html", dados);
    };

    public Handler postMatricularProfessor = (Context ctx) -> {
        String codigo = ctx.formParam("codigo_disciplina");
        escola.buscarDisciplina(codigo);

        Map<String, Object> dados = new HashMap<>();
        if (escola.getDisciplinas().isEmpty()) {
            dados.put("mensagem", "Disciplina não encontrada. Matrícula não pode ser realizada.");
            ctx.render("resultadoMatricula.html", dados);
            return;
        }
        Disciplina disciplina = escola.getDisciplinas().get(0);

        String nome          = ctx.formParam("nome");
        String cpf           = ctx.formParam("cpf");
        String email         = ctx.formParam("email");
        String anoNascimento = ctx.formParam("anoNascimento");
        String salario       = ctx.formParam("salario");
        String cargaHoraria  = ctx.formParam("cargaHoraria");

        Professor professor = new Professor(nome, cpf, email, anoNascimento, salario, cargaHoraria);
        boolean sucesso = disciplina.matricularProfessor(professor, disciplina.getCodigo());

        if (sucesso) {
            dados.put("mensagem", "Professor matriculado com sucesso!");
        } else {
            dados.put("mensagem", "Falha na matrícula do professor ou a disciplina já possui um professor.");
        }
        ctx.render("resultadoMatricula.html", dados);
    };

    public Handler postDesmatricularProfessor = (Context ctx) -> {
        String codigo = ctx.formParam("codigo_disciplina");
        escola.buscarDisciplina(codigo);

        Map<String, Object> dados = new HashMap<>();
        if (escola.getDisciplinas().isEmpty()) {
            dados.put("mensagem", "Disciplina não encontrada.");
            ctx.render("resultadoMatricula.html", dados);
            return;
        }
        Disciplina disciplina = escola.getDisciplinas().get(0);

        String cpf = ctx.formParam("cpf");
        boolean sucesso = disciplina.desmatriularProfessor(cpf);

        if (sucesso) {
            dados.put("mensagem", "Professor desmatriculado com sucesso!");
        } else {
            dados.put("mensagem", "Falha ao desmatricular professor.");
        }
        ctx.render("resultadoMatricula.html", dados);
    };

    public Handler postListarProfessores = (Context ctx) -> {
        String codigo = ctx.formParam("codigo_disciplina");
        escola.buscarDisciplina(codigo);

        Map<String, Object> dados = new HashMap<>();
        if (escola.getDisciplinas().isEmpty()) {
            dados.put("mensagem", "Disciplina não encontrada.");
            ctx.render("listaProfessoresDisciplina.html", dados);
            return;
        }
        Disciplina disciplina = escola.getDisciplinas().get(0);

        disciplina.listarProfessores(codigo);
        dados.put("professores", disciplina.getPessoas());
        ctx.render("listaProfessoresDisciplina.html", dados);
    };
}

