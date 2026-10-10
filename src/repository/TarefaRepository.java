package repository;

import database.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Tarefa;

public class TarefaRepository {

    private final Connection conexao;

  
    public TarefaRepository() {
        try {
            this.conexao = Conexao.conectar();
        } catch (SQLException erro) {
            throw new RuntimeException(
                    "Erro ao conectar ao banco de dados.",
                    erro
            );
        }
    }

    // Conexão utilizada pelos testes
    public TarefaRepository(Connection conexao) {
        this.conexao = conexao;
    }

    public void salvar(Tarefa tarefa) throws SQLException {

        boolean autoIncrement = (tarefa.getId() <= 0);

        String sql;
        if (autoIncrement) {
            sql = """
                    INSERT INTO tarefas (titulo, descricao, concluida)
                    VALUES (?, ?, ?)
                    """;
        } else {
            sql = """
                    INSERT INTO tarefas (id, titulo, descricao, concluida)
                    VALUES (?, ?, ?, ?)
                    """;
        }

        try (PreparedStatement comando = autoIncrement
                ? conexao.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)
                : conexao.prepareStatement(sql)) {

            if (autoIncrement) {
                comando.setString(1, tarefa.getTitulo());
                comando.setString(2, tarefa.getDescricao());
                comando.setInt(3, tarefa.isConcluida() ? 1 : 0);
            } else {
                comando.setInt(1, tarefa.getId());
                comando.setString(2, tarefa.getTitulo());
                comando.setString(3, tarefa.getDescricao());
                comando.setInt(4, tarefa.isConcluida() ? 1 : 0);
            }

            comando.executeUpdate();

            if (autoIncrement) {
                try (ResultSet chaves = comando.getGeneratedKeys()) {
                    if (chaves.next()) {
                        tarefa.setId(chaves.getInt(1));
                    }
                }
            }
        }
    }

    public ArrayList<Tarefa> listar() throws SQLException {

        ArrayList<Tarefa> tarefas =
                new ArrayList<>();

        String sql = """
                SELECT id, titulo, descricao, concluida
                FROM tarefas
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql);
             ResultSet resultado =
                     comando.executeQuery()) {

            while (resultado.next()) {

                int id =
                        resultado.getInt("id");

                String titulo =
                        resultado.getString("titulo");

                String descricao =
                        resultado.getString("descricao");

                boolean concluida =
                        resultado.getBoolean("concluida");

                Tarefa tarefa =
                        new Tarefa(
                                id,
                                titulo,
                                descricao
                        );

                tarefa.setConcluida(concluida);

                tarefas.add(tarefa);
            }
        }

        return tarefas;
    }

    public void atualizar(Tarefa tarefa)
            throws SQLException {

        String sql = """
                UPDATE tarefas
                SET titulo = ?,
                    descricao = ?,
                    concluida = ?
                WHERE id = ?
                """;

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(
                    1,
                    tarefa.getTitulo()
            );

            comando.setString(
                    2,
                    tarefa.getDescricao()
            );

            comando.setInt(
                    3,
                    tarefa.isConcluida() ? 1 : 0
            );

            comando.setInt(
                    4,
                    tarefa.getId()
            );

            comando.executeUpdate();
        }
    }

    public void excluir(int id)
            throws SQLException {

        String sql =
                "DELETE FROM tarefas WHERE id = ?";

        try (PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            comando.executeUpdate();
        }
    }

    public void salvarComTransacao(ArrayList<Tarefa> tarefas) throws SQLException {
        boolean autoCommitOriginal = conexao.getAutoCommit();
        conexao.setAutoCommit(false);
        try {
            for (Tarefa tarefa : tarefas) {
                salvar(tarefa);
            }
            conexao.commit();
        } catch (SQLException erro) {
            conexao.rollback();
            throw erro;
        } finally {
            conexao.setAutoCommit(autoCommitOriginal);
        }
    }
}