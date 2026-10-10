package app;

import controller.TaskManager;
import database.DatabaseInitializer;
import model.Tarefa;
import java.util.Scanner;
import java.util.InputMismatchException;


public class Main {

    public static void main(String[] args) {

        DatabaseInitializer.inicializar();

        TaskManager<Tarefa> manager = new TaskManager<>();

        Scanner scanner = new Scanner(System.in);

        int opcao;

        while (true) {

            try {

                System.out.println("===== GERENCIADOR DE TAREFAS =====");
                System.out.println("1. Criar nova tarefa");
                System.out.println("2. Listar tarefas");
                System.out.println("3. Concluir tarefa");
                System.out.println("4. Editar tarefa");
                System.out.println("5. Remover tarefa");
                System.out.println("6. Sair");
                System.out.print("Escolha uma opção: ");

                opcao = scanner.nextInt();

                switch (opcao) {

                    case 1:

                        System.out.print("Digite o ID da tarefa: ");
                        int id = scanner.nextInt();

                        scanner.nextLine();

                        System.out.print("Digite o título da tarefa: ");
                        String titulo = scanner.nextLine();

                        System.out.print("Digite a descrição da tarefa: ");
                        String descricao = scanner.nextLine();

                        Tarefa tarefa = new Tarefa(id, titulo, descricao);

                        manager.adicionarTarefa(tarefa);

                        break;

                    case 2:

                        manager.listarTarefas();

                        break;

                    case 3:

                        System.out.print("Digite o ID da tarefa que deseja concluir: ");
                        int idConclusao = scanner.nextInt();

                        manager.concluirTarefa(idConclusao);

                        break;

                    case 4:

                        System.out.print("Digite o ID da tarefa que deseja editar: ");
                        int idEdicao = scanner.nextInt();

                        scanner.nextLine();

                        System.out.print("Digite o novo título: ");
                        String novoTitulo = scanner.nextLine();

                        System.out.print("Digite a nova descrição: ");
                        String novaDescricao = scanner.nextLine();

                        manager.editarTarefa(
                                idEdicao,
                                novoTitulo,
                                novaDescricao
                        );

                        break;

                    case 5:

                        System.out.print("Digite o ID da tarefa que deseja remover: ");
                        int idRemover = scanner.nextInt();

                        manager.removerTarefa(idRemover);

                        break;

                    case 6:

                        System.out.println("Saindo do sistema...");

                        scanner.close();

                        System.exit(0);

                        break;

                    default:

                        System.out.println("Opção inválida!");

                        break;
                }

            } catch (InputMismatchException erro) {

                System.out.println("Digite apenas números!");

                scanner.nextLine();
            }
        }
    }
}