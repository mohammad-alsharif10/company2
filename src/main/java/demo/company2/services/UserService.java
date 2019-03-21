package demo.company2.services;


import demo.company2.entities.Role;
import demo.company2.entities.User;
import demo.company2.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole = new Role("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void createAdmin(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole = new Role("ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isUserPresent(String email) {
        // TODO Auto-generated method stub
        User u = userRepository.findByEmail(email);
        return u != null;
    }

    public List<User> findAll() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }

    public List<User> findByName(String name) {
        // TODO Auto-generated method stub
        return  userRepository.findUsersByNameContaining(name);
    }


}
