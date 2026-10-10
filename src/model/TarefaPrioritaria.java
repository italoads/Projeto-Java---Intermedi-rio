package model;


public class TarefaPrioritaria extends Tarefa {
    private String prioridade;
    
    public TarefaPrioritaria(
            int id,
            String titulo,
            String descricao,
            String prioridade
    ){
        
        super(id, titulo, descricao);
        this.prioridade = prioridade;
        

    }   
    
    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

}