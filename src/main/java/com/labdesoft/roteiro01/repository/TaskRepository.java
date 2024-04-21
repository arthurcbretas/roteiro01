package com.labdesoft.roteiro01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.labdesoft.roteiro01.entity.Task;
import com.labdesoft.roteiro01.entity.enums.*;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTipo(TipoTarefa tipo);
    List<Task> findByPrioridade(Prioridade prioridade);
}
