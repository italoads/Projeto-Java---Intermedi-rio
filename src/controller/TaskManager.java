
package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Tarefa;
import repository.TarefaRepository;

public class TaskManager<T extends Tarefa> {

    private TarefaRepository repository;

    public TaskManager() {
        repository = new TarefaRepository();
    }

    public void adicionarTarefa(T tarefa) {
        try {
            repository.salvar(tarefa);
            System.out.println("Tarefa adicionada com sucesso!");
        } catch (SQLException erro) {
            System.out.println("Erro ao salvar a tarefa no banco de dados.");
            erro.printStackTrace();
        }
    }

    public void listarTarefas() {
        try {
            ArrayList<Tarefa> tarefas = repository.listar();

            if (tarefas.isEmpty()) {
                System.out.println("Nenhuma tarefa cadastrada.");
            } else {
                for (Tarefa tarefa : tarefas) {
                    System.out.println(tarefa);
                    System.out.println("-------------------------");
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao listar tarefas.");
            erro.printStackTrace();
        }
    }

    public void concluirTarefa(int id) {
        try {
            ArrayList<Tarefa> tarefas = repository.listar();

            for (Tarefa tarefa : tarefas) {
                if (tarefa.getId() == id) {
                    tarefa.setConcluida(true);
                    repository.atualizar(tarefa);
                    System.out.println("Tarefa concluída com sucesso!");
                    break;
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao concluir a tarefa.");
            erro.printStackTrace();
        }
    }

    public void editarTarefa(int id, String titulo, String descricao) {
        try {
            ArrayList<Tarefa> tarefas = repository.listar();

            for (Tarefa tarefa : tarefas) {
                if (tarefa.getId() == id) {
                    tarefa.setTitulo(titulo);
                    tarefa.setDescricao(descricao);
                    repository.atualizar(tarefa);
                    System.out.println("Tarefa editada com sucesso!");
                    break;
                }
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao editar a tarefa.");
            erro.printStackTrace();
        }
    }

    public void removerTarefa(int id) {
        try {
            ArrayList<Tarefa> tarefas = repository.listar();

            Tarefa tarefaRemover = null;

            for (Tarefa tarefa : tarefas) {
                if (tarefa.getId() == id) {
                    tarefaRemover = tarefa;
                    break;
                }
            }

            if (tarefaRemover != null) {
                repository.excluir(tarefaRemover.getId());
                System.out.println("Tarefa removida com sucesso!");
            } else {
                System.out.println("Tarefa não encontrada.");
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao remover a tarefa.");
            erro.printStackTrace();
        }
    }

    public void adicionarTodos(List<? extends T> tarefas) {
        for (T tarefa : tarefas) {
            adicionarTarefa(tarefa);
        }
    }

    public static int quantidade(List<?> lista) {
        return lista.size();
    }

    public void copiarPara(List<? super T> destino, List<? extends T> origem) {
        for (T tarefa : origem) {
            destino.add(tarefa);
        }
    }
}