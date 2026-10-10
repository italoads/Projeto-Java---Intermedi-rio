package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseInitializer {

    public static void inicializar() {

        String sql = """
                CREATE TABLE IF NOT EXISTS tarefas (
                    id INTEGER PRIMARY KEY,
                    titulo TEXT NOT NULL,
                    descricao TEXT,
                    concluida INTEGER NOT NULL
                )
                """;

        try (Connection conexao = Conexao.conectar();
             Statement statement = conexao.createStatement()) {

            statement.execute(sql);

            System.out.println("Banco de dados inicializado com sucesso!");

        } catch (SQLException erro) {

            System.out.println("Erro ao inicializar o banco de dados.");
            erro.printStackTrace();
        }
    }
}