package app;

import database.DatabaseInitializer;

public class Launcher {

    public static void main(String[] args) {
        DatabaseInitializer.inicializar();
        MainApplication.main(args);
    }
}