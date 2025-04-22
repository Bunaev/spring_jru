package jru_apl.repositories;

import jru_apl.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {

}
