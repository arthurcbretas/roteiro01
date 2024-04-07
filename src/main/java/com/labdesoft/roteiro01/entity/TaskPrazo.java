package com.labdesoft.roteiro01.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Schema(description = "Entidade para uma tarefa do tipo Prazo.")
public class TaskPrazo extends Task {
    @Schema(description = "Prazo previsto de conclusão da tarefa em dias.")
    private int diaPrazo;

    public TaskPrazo(String description) {
        super(description);
    }

    public int getDeadlineDays() {
        return diaPrazo;
    }

    public void setDeadlineDays(int diaPrazo) {
        this.diaPrazo = diaPrazo;
    }
}