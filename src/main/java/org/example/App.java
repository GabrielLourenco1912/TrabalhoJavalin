package org.example;

import io.javalin.Javalin;
import org.example.Utils.JavalinUtils;
import org.example.controllers.IndexController;

public class App {
    public static void main(String[] args) {
        var app = JavalinUtils.makeApp(7070);

        IndexController indexController = new IndexController();

        app.get("/", indexController.get);

    }
}