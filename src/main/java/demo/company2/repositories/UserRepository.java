package demo.company2.repositories;


import demo.company2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

    List<User> findUsersByNameContaining(String name);

}
