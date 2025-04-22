package jru_apl.service;

import jakarta.persistence.EntityNotFoundException;
import jru_apl.domain.Status;
import jru_apl.domain.Task;
import jru_apl.dto.TaskDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import jru_apl.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jru_apl.repositories.TaskRepository;

@Service
@Transactional
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    private TaskMapper mapper;

    public void createTask(TaskDTO taskDTO) {
        taskRepository.save(mapper.toEntity(taskDTO));
    }
    public void updateDescription(Long taskId, String description) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        task.setDescription(description);
    }

    public Page<Task> getAllTasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskRepository.findAll(pageable);
    }
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    public void updateStatus(Long taskId, Status newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(newStatus);
        taskRepository.save(task);
    }
}
