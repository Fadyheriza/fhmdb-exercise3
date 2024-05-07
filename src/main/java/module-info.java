module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires ormlite.core;
    requires java.sql;
    requires ormlite.jdbc;

    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson, javafx.base;
    opens at.ac.fhcampuswien.fhmdb.database to ormlite.core, javafx.base;
    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.ui to javafx.fxml;

    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb.ui;
}
