package com.library.managementsystem.service;

import com.library.managementsystem.model.user.User;
import com.library.managementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User with id " + id + " was not found"));
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public boolean checkDupeUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByNameAndEmail(String name, String email) {
        return userRepository.findByNameAndEmail(name, email);
    }

    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }
}
