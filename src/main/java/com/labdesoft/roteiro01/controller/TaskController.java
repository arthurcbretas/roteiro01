package com.labdesoft.roteiro01.controller;

import com.labdesoft.roteiro01.entity.Task;
import com.labdesoft.roteiro01.entity.TaskData;
import com.labdesoft.roteiro01.entity.TaskLivre;
import com.labdesoft.roteiro01.entity.TaskPrazo;
import com.labdesoft.roteiro01.repository.TaskRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/task")
    @Operation(summary = "Lista todas as tarefas da lista")
    public ResponseEntity<List<Task>> listarTodas() {
        try {
            List<Task> listaTarefas = new ArrayList<>();
            taskRepository.findAll().forEach(listaTarefas::add);
            if (listaTarefas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(listaTarefas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/task")
    @Operation(summary = "Adiciona uma nova tarefa")
    public ResponseEntity<Task> adicionarTarefa(@Valid @RequestBody Task task) {
        try {
            Task novaTarefa = taskRepository.save(task);
            return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/task/{id}/completar")
    @Operation(summary = "Marca uma tarefa como completa")
    public ResponseEntity<Task> marcarTarefaComoCompleta(@PathVariable Long id) {
        try {
            Optional<Task> tarefaOptional = taskRepository.findById(id);
            if (tarefaOptional.isPresent()) {
                Task tarefaExistente = tarefaOptional.get();
                tarefaExistente.setCompleta(true);
                Task tarefaAtualizada = taskRepository.save(tarefaExistente);
                return ResponseEntity.ok(tarefaAtualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/task/{id}")
    @Operation(summary = "Exclui uma tarefa")
    public ResponseEntity<HttpStatus> excluirTarefa(@PathVariable Long id) {
        try {
            taskRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/task/data")
    @Operation(summary = "Adiciona uma nova tarefa do tipo Data")
    public ResponseEntity<Task> adicionarTarefaData(@RequestParam String descricao, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPrevista) {
        TaskData novaTarefa = new TaskData(descricao);
        novaTarefa.setDueDate(dataPrevista);
        try {
            Task novaTarefaSalva = taskRepository.save(novaTarefa);
            return new ResponseEntity<>(novaTarefaSalva, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/task/prazo")
    @Operation(summary = "Adiciona uma nova tarefa do tipo Prazo")
    public ResponseEntity<Task> adicionarTarefaPrazo(@RequestParam String descricao, @RequestParam int prazoDias) {
        TaskPrazo novaTarefa = new TaskPrazo(descricao);
        novaTarefa.setDeadlineDays(prazoDias);
        try {
            Task novaTarefaSalva = taskRepository.save(novaTarefa);
            return new ResponseEntity<>(novaTarefaSalva, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/task/livre")
    @Operation(summary = "Adiciona uma nova tarefa do tipo Livre")
    public ResponseEntity<Task> adicionarTarefaLivre(@RequestParam String descricao) {
        TaskLivre novaTarefa = new TaskLivre(descricao);
        try {
            Task novaTarefaSalva = taskRepository.save(novaTarefa);
            return new ResponseEntity<>(novaTarefaSalva, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
