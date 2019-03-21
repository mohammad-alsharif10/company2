package demo.company2.repositories;

import demo.company2.entities.Task;
import demo.company2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository  extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
}
