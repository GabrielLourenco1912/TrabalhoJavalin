package org.example;

import io.javalin.Javalin;
import org.example.Utils.JavalinUtils;
import org.example.controllers.DisciplinaController;
import org.example.controllers.EscolaController;
import org.example.controllers.IndexController;

public class App {
    public static void main(String[] args) {
        var app = JavalinUtils.makeApp(7070);

        IndexController indexController = new IndexController();
        DisciplinaController disciplinaController = new DisciplinaController();
        EscolaController escolaController = new EscolaController();

        app.get("/", indexController.get);

        app.get("/indexDisciplina", disciplinaController.get);
        app.post("/matricularAluno", disciplinaController.postMatricularAluno);
        app.post("/procurarAlunoDisciplina", disciplinaController.postProcurarAlunoDisciplina);
        app.post("/desmatricularAluno", disciplinaController.postMatricularAluno);
        app.post("/listarAlunosDisciplina", disciplinaController.postListarAlunos);
        app.post("/matricularProfessor", disciplinaController.postMatricularProfessor);
        app.post("/desmatricularProfessor", disciplinaController.postMatricularProfessor);
        app.post("/listarProfessoresDisciplina", disciplinaController.postListarProfessores);

        app.get("/indexEscola", escolaController.get);
        app.post("/adicionarAluno", escolaController.postAdicionarAluno);
        app.post("/adicionarProfessor", escolaController.postAdicionarProfessor);
        app.post("/removerAluno", escolaController.postRemoverAluno);
        app.post("/removerProfesso", escolaController.postRemoverProfessor);
        app.post("/buscarAluno", escolaController.postBuscarAluno);
        app.post("/buscarAlunoCPF", escolaController.postBuscarAlunoCPF);
        app.post("/listarAlunos", escolaController.postListarAlunos);
        app.post("/listarProfessores", escolaController.postListarProfessores);
        app.post("/adicionarDisciplina", escolaController.postAdicionarDisciplina);
        app.post("/contarAlunos", escolaController.postContarAlunos);
        app.post("/contarProfessores", escolaController.postContarProfessores);

    }
}