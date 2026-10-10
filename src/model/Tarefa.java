package model;

import exceptions.TarefaException;

public class Tarefa {

    private int id;
    private String titulo;
    private String descricao;
    private boolean concluida;

    public Tarefa(int id, String titulo, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = false;
    }

    public void validar() throws TarefaException {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new TarefaException(
                    "O título da tarefa é obrigatório."
            );
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    @Override
    public String toString() {

        String status;

        if (concluida) {
            status = "Concluída";
        } else {
            status = "Pendente";
        }

        return "ID: " + id
                + "\nTítulo: " + titulo
                + "\nDescricao: " + descricao
                + "\nStatus: " + status;
    }
}