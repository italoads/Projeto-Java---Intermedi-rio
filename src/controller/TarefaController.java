package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.stage.Modality;
import javafx.stage.Stage;

import model.Tarefa;
import repository.TarefaRepository;

public class TarefaController {

    @FXML
    private TableView<Tarefa> tabelaTarefas;

    @FXML
    private TableColumn<Tarefa, Number> colunaId;

    @FXML
    private TableColumn<Tarefa, String> colunaTitulo;

    @FXML
    private TableColumn<Tarefa, String> colunaDescricao;

    @FXML
    private TableColumn<Tarefa, Boolean> colunaConcluida;

    private final TarefaRepository repository =
            new TarefaRepository();

    private final ObservableList<Tarefa> tarefas =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        configurarTabela();

        carregarTarefas();
    }

    private void configurarTabela() {

        colunaId.setCellValueFactory(
                data ->
                        new SimpleIntegerProperty(
                                data.getValue().getId()
                        )
        );

        colunaTitulo.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getTitulo()
                        )
        );

        colunaDescricao.setCellValueFactory(
                data ->
                        new SimpleStringProperty(
                                data.getValue().getDescricao()
                        )
        );

        colunaConcluida.setCellValueFactory(
                data ->
                        new SimpleBooleanProperty(
                                data.getValue().isConcluida()
                        ).asObject()
        );

        tabelaTarefas.setItems(tarefas);
    }

    @FXML
    public void carregarTarefas() {

        try {

            List<Tarefa> lista =
                    repository.listar();

            tarefas.setAll(lista);

        } catch (SQLException erro) {

            mostrarErro(
                    "Erro ao carregar tarefas.",
                    erro
            );
        }
    }

    @FXML
    private void novaTarefa() {

        abrirFormulario(null);
    }

    @FXML
    private void editarTarefa() {

        Tarefa selecionada =
                tabelaTarefas
                        .getSelectionModel()
                        .getSelectedItem();

        if (selecionada == null) {

            mostrarAviso(
                    "Selecione uma tarefa para editar."
            );

            return;
        }

        abrirFormulario(selecionada);
    }

    @FXML
    private void concluirTarefa() {

        Tarefa selecionada =
                tabelaTarefas
                        .getSelectionModel()
                        .getSelectedItem();

        if (selecionada == null) {

            mostrarAviso(
                    "Selecione uma tarefa."
            );

            return;
        }

        try {

            selecionada.setConcluida(true);

            repository.atualizar(
                    selecionada
            );

            carregarTarefas();

        } catch (SQLException erro) {

            mostrarErro(
                    "Erro ao concluir tarefa.",
                    erro
            );
        }
    }

    @FXML
    private void removerTarefa() {

        Tarefa selecionada =
                tabelaTarefas
                        .getSelectionModel()
                        .getSelectedItem();

        if (selecionada == null) {

            mostrarAviso(
                    "Selecione uma tarefa para remover."
            );

            return;
        }

        Alert confirmacao =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmacao.setTitle(
                "Remover tarefa"
        );

        confirmacao.setHeaderText(
                "Deseja realmente remover esta tarefa?"
        );

        confirmacao.setContentText(
                selecionada.getTitulo()
        );

        if (confirmacao.showAndWait()
                .orElse(ButtonType.CANCEL)
                == ButtonType.OK) {

            try {

                repository.excluir(
                        selecionada.getId()
                );

                carregarTarefas();

            } catch (SQLException erro) {

                mostrarErro(
                        "Erro ao remover tarefa.",
                        erro
                );
            }
        }
    }

    private void abrirFormulario(
            Tarefa tarefa
    ) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass()
                                    .getResource(
                                            "/view/TarefaForm.fxml"
                                    )
                    );

            Parent root =
                    loader.load();

            TarefaFormController controller =
                    loader.getController();

            controller.setTarefa(tarefa);

            Stage stage =
                    new Stage();

            stage.setTitle(
                    tarefa == null
                            ? "Nova Tarefa"
                            : "Editar Tarefa"
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.showAndWait();

            if (controller.isSalvo()) {

                Tarefa tarefaSalva =
                        controller.getTarefa();

                try {

                    if (tarefa == null) {

                        repository.salvar(
                                tarefaSalva
                        );

                    } else {

                        repository.atualizar(
                                tarefaSalva
                        );
                    }

                    carregarTarefas();

                } catch (SQLException erro) {

                    mostrarErro(
                            "Erro ao salvar tarefa.",
                            erro
                    );
                }
            }

        } catch (IOException erro) {

            mostrarErro(
                    "Erro ao abrir formulário.",
                    erro
            );
        }
    }

    private void mostrarAviso(
            String mensagem
    ) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alerta.setTitle("Aviso");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        alerta.showAndWait();
    }

    private void mostrarErro(
            String mensagem,
            Exception erro
    ) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alerta.setTitle("Erro");
        alerta.setHeaderText(mensagem);
        alerta.setContentText(
                erro.getMessage()
        );

        alerta.showAndWait();
    }
}