package com.labdesoft.roteiro01.controller;

import com.labdesoft.roteiro01.entity.Task;
import com.labdesoft.roteiro01.entity.enums.*;
import com.labdesoft.roteiro01.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    @Operation(summary = "Lista todas as tarefas")
    public ResponseEntity<List<Task>> listarTodas() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Adiciona uma nova tarefa")
    public ResponseEntity<Task> adicionarTarefa(@Valid @RequestBody Task task) {
        Task novaTarefa = taskRepository.save(task);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/completar")
    @Operation(summary = "Marca uma tarefa como completa")
    public ResponseEntity<Task> marcarTarefaComoCompleta(@PathVariable Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task tarefaExistente = optionalTask.get();
            tarefaExistente.setCompleta(true);
            Task tarefaAtualizada = taskRepository.save(tarefaExistente);
            return ResponseEntity.ok(tarefaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma tarefa")
    public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Lista tarefas por tipo")
    public ResponseEntity<List<Task>> listarTarefasPorTipo(@PathVariable TipoTarefa tipo) {
        List<Task> tasks = taskRepository.findByTipo(tipo);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/prioridade/{prioridade}")
    @Operation(summary = "Lista tarefas por prioridade")
    public ResponseEntity<List<Task>> listarTarefasPorPrioridade(@PathVariable Prioridade prioridade) {
        List<Task> tasks = taskRepository.findByPrioridade(prioridade);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
