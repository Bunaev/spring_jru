package jru_apl.dto;

import jakarta.validation.constraints.NotNull;
import jru_apl.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDTO {

    @NotBlank(message = "Описание не может быть пустым!")
    @Size(max = 100, message = "Значение поля не может превышать 100 символов.")
    private String description;
    @NotNull(message = "Поле 'Статус' не может быть пустым!")
    private Status status;
}
