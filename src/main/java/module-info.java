module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports helloworld;
    exports helloworld.model;
    exports helloworld.view;
    exports helloworld.controller;
    exports helloworld.dao;
    exports helloworld.builder;
    exports helloworld.db;
    exports helloworld.service;
    exports helloworld.memory;

    opens helloworld.view to javafx.fxml;
}