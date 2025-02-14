package org.example.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.example.models.Aluno;
import org.example.models.Professor;
import org.example.models.Escola;
import org.example.models.Disciplina;

import java.util.HashMap;
import java.util.Map;

public class EscolaController {

    private static Escola escola = new Escola("Minha Escola", "123456789");

    public Handler get = (Context ctx)->{
        ctx.render("indexEscola.html");
    };

    public Handler postAdicionarAluno = (Context ctx) -> {
        String nome          = ctx.formParam("nome");
        String cpf           = ctx.formParam("cpf");
        String email         = ctx.formParam("email");
        String anoNascimento = ctx.formParam("anoNascimento");

        Aluno aluno = new Aluno(nome, cpf, email, anoNascimento);

        boolean sucesso = escola.adicionarAluno(aluno);

        Map<String, Object> dados = new HashMap<>();
        if (sucesso) {
            dados.put("mensagem", "Aluno adicionado com sucesso!");
        } else {
            dados.put("mensagem", "Falha ao adicionar aluno. Verifique se o CPF já existe.");
        }
        ctx.render("resultadoAluno.html", dados);
    };

    public Handler postAdicionarProfessor = (Context ctx) -> {
        String nome         = ctx.formParam("nome");
        String cpf          = ctx.formParam("cpf");
        String email        = ctx.formParam("email");
        String anoNascimento= ctx.formParam("anoNascimento");
        String salario      = ctx.formParam("salario");
        String cargaHoraria = ctx.formParam("cargaHoraria");

        Professor professor = new Professor(nome, cpf, email, anoNascimento, salario, cargaHoraria);
        boolean sucesso = escola.adicionarProfessor(professor);

        Map<String, Object> dados = new HashMap<>();
        if (sucesso) {
            dados.put("mensagem", "Professor adicionado com sucesso!");
        } else {
            dados.put("mensagem", "Falha ao adicionar professor.");
        }
        ctx.render("resultadoProfessor.html.html", dados);
    };

    public Handler postRemoverAluno = (Context ctx) -> {
        String cpf = ctx.formParam("cpf");

        boolean sucesso = escola.removerAluno(cpf);

        Map<String, Object> dados = new HashMap<>();
        if (sucesso) {
            dados.put("mensagem", "Aluno removido com sucesso!");
        } else {
            dados.put("mensagem", "Nenhum aluno encontrado com o CPF informado.");
        }
        ctx.render("resultadoAluno.html", dados);
    };

    public Handler postRemoverProfessor = (Context ctx) -> {
        String cpf = ctx.formParam("cpf");

        boolean sucesso = escola.removerProfessor(cpf);

        Map<String, Object> dados = new HashMap<>();
        if (sucesso) {
            dados.put("mensagem", "Professor removido com sucesso!");
        } else {
            dados.put("mensagem", "Nenhum professor encontrado com o CPF informado.");
        }
        ctx.render("resultadoProfessor.html.html", dados);
    };

    public Handler postBuscarAluno = (Context ctx) -> {
        String nome = ctx.formParam("nome");
        escola.buscarAluno(nome);

        Map<String, Object> dados = new HashMap<>();
        dados.put("alunos", escola.getPessoas());
        ctx.render("listaAlunos.html", dados);
    };

    public Handler postBuscarAlunoCPF = (Context ctx) -> {
        String cpf = ctx.formParam("cpf");
        escola.buscarAlunoCPF(cpf);

        Map<String, Object> dados = new HashMap<>();
        dados.put("alunos", escola.getPessoas());
        ctx.render("listaAlunos.html", dados);
    };

    public Handler postListarAlunos = (Context ctx) -> {
        escola.listarAlunos();

        Map<String, Object> dados = new HashMap<>();
        dados.put("alunos", escola.getPessoas());
        ctx.render("listaAlunos.html", dados);
    };

    public Handler postListarProfessores = (Context ctx) -> {
        escola.listarProfessores();

        Map<String, Object> dados = new HashMap<>();
        dados.put("professores", escola.getPessoas());
        ctx.render("listaProfessores.html", dados);
    };

    public Handler postAdicionarDisciplina = (Context ctx) -> {
        String codigo      = ctx.formParam("codigo");
        String nome        = ctx.formParam("nome");
        String cargaHoraria   = ctx.formParam("cargaHoraria");

        Disciplina disciplina = new Disciplina(codigo, nome, cargaHoraria);
        boolean sucesso = escola.adicionarDisciplina(disciplina);

        Map<String, Object> dados = new HashMap<>();
        if (sucesso) {
            dados.put("mensagem", "Disciplina adicionada com sucesso!");
        } else {
            dados.put("mensagem", "Falha ao adicionar disciplina.");
        }
        ctx.render("resultadoDisciplina.html", dados);
    };

    public Handler postContarAlunos = (Context ctx) -> {
        int total = escola.contarAlunos();

        Map<String, Object> dados = new HashMap<>();
        dados.put("mensagem", "Total de alunos: " + total);
        ctx.render("resultadoContagem.html", dados);
    };

    public Handler postContarProfessores = (Context ctx) -> {
        int total = escola.contarProfessores();

        Map<String, Object> dados = new HashMap<>();
        dados.put("mensagem", "Total de professores: " + total);
        ctx.render("resultadoContagem.html", dados);
    };
}

