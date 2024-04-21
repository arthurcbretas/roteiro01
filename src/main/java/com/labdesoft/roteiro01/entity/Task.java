package com.labdesoft.roteiro01.entity;

import com.labdesoft.roteiro01.entity.enums.Prioridade;
import com.labdesoft.roteiro01.entity.enums.TipoTarefa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Todos os detalhes sobre uma tarefa.")
public class Task {


    @Id
    private Long id;

    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    private String description;

    private Boolean completa;

    // Novos campos
    @Enumerated(EnumType.STRING)
    private TipoTarefa tipo;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    public Task(String description, TipoTarefa tipo, Prioridade prioridade) {
        this.description = description;
        this.tipo = tipo;
        this.prioridade = prioridade;
        this.completa = false;
    }


}
