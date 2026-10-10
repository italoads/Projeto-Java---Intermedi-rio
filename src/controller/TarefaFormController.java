package controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Tarefa;

public class TarefaFormController {

    @FXML
    private TextField campoTitulo;

    @FXML
    private TextArea campoDescricao;

    @FXML
    private CheckBox checkConcluida;

    private Tarefa tarefa;
    private boolean salvo = false;

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;

        if (tarefa != null) {
            campoTitulo.setText(tarefa.getTitulo());
            campoDescricao.setText(tarefa.getDescricao());
            checkConcluida.setSelected(tarefa.isConcluida());
        }
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public boolean isSalvo() {
        return salvo;
    }

    @FXML
    private void salvar() {
        String titulo = campoTitulo.getText();
        String descricao = campoDescricao.getText();
        boolean concluida = checkConcluida.isSelected();

        if (titulo == null || titulo.trim().isEmpty()) {
            return;
        }

        if (tarefa == null) {
            tarefa = new Tarefa(0, titulo, descricao);
        } else {
            tarefa.setTitulo(titulo);
            tarefa.setDescricao(descricao);
        }

        tarefa.setConcluida(concluida);
        salvo = true;

        fecharJanela();
    }

    @FXML
    private void cancelar() {
        salvo = false;
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) campoTitulo.getScene().getWindow();
        stage.close();
    }
}