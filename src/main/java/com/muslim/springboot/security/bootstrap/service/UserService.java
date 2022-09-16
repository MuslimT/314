package com.muslim.springboot.security.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.muslim.springboot.security.bootstrap.model.Role;
import com.muslim.springboot.security.bootstrap.model.User;
import com.muslim.springboot.security.bootstrap.repository.RoleRepository;
import com.muslim.springboot.security.bootstrap.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> index() {
        return userRepository.findAll();
    }


    public User showUser(long id) {
        return userRepository.getReferenceById(id);
    }

    @Transactional
    public User showUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public boolean saveUser(User user) {
        User userFromDb = userRepository.findUserByUsername(user.getUsername());
        if (userFromDb != null){
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public void updateUser(User user) {
        User userFromDb = userRepository.getReferenceById(user.getId());

        if (user.getPassword().equals(userFromDb.getPassword()) &&
                user.getPassword().startsWith("$2a$12$") && user.getPassword().length() == 60){
            userRepository.save(user);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }


    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return user;
    }

}
