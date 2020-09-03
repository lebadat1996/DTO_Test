package com.codegym.dto.service;

import com.codegym.dto.entity.User;
import com.codegym.dto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User create(User model) {
        return userRepository.save(model);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isDeleted(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.get().getStatus() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
