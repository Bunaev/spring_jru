package jru_apl.mapper;

import jru_apl.domain.Task;
import jru_apl.dto.TaskDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDTO (Task task);
    Task toEntity (TaskDTO taskDTO);
}
