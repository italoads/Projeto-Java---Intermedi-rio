package model;

import exceptions.TarefaException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TarefaTest {

    @Test
    public void deveCriarTarefa() {
        Tarefa tarefa = new Tarefa(1, "Estudar Java", "Estudar junit");

        assertEquals(1, tarefa.getId());
        assertEquals("Estudar Java", tarefa.getTitulo());
        assertEquals("Estudar junit", tarefa.getDescricao());
        assertFalse(tarefa.isConcluida());
    }

    @Test
    public void deveAlterarDadosDaTarefa() {
        Tarefa tarefa = new Tarefa(1, "Estudar Java", "Estudar junit");

        tarefa.setTitulo("Estudar JUnit 4");
        tarefa.setDescricao("Aprender testes unitários em Java");

        assertEquals("Estudar JUnit 4", tarefa.getTitulo());
        assertEquals("Aprender testes unitários em Java", tarefa.getDescricao());
    }

    @Test
    public void deveAlterarIdDaTarefa() {
        Tarefa tarefa = new Tarefa(1, "Estudar Java", "Estudar junit");

        tarefa.setId(99);

        assertEquals(99, tarefa.getId());
    }

    @Test
    public void deveMostrarTarefaConcluidaNoTexto() {
        Tarefa tarefa = new Tarefa(1, "Estudar Java", "Estudar junit");
        
        tarefa.setConcluida(true);

        String textoFormatado = tarefa.toString();

        assertTrue("O toString deve conter 'Status: Concluída'", textoFormatado.contains("Status: Concluída"));
    }

    @Test(expected = TarefaException.class)
    public void deveRejeitarTarefaSemTitulo() throws TarefaException {
        // Cria a tarefa com título vazio e chama o método validar() para disparar a exceção
        Tarefa tarefa = new Tarefa(1, "", "Descrição válida");
        tarefa.validar();
    }

    @Test
    public void deveAceitarTarefaComTitulo() throws TarefaException {
        Tarefa tarefa = new Tarefa(1, "Título Válido", "Descrição válida");
        
        // Se o título for válido, o método validar() executa sem lançar exceção
        tarefa.validar();

        assertNotNull(tarefa);
        assertEquals("Título Válido", tarefa.getTitulo());
    }
}