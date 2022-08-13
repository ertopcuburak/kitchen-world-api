package com.bertopcu.KitchenWorld.service;

import com.bertopcu.KitchenWorld.model.User;
import com.bertopcu.KitchenWorld.jpa_repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> listAllUser() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User loginViaEmail(String email, String pwd) {
        return userRepository.findByEmailAndPwd(email, pwd);
    }

    public User loginViaUserName(String userName, String pwd) {
        return userRepository.findByUnameAndPwd(userName, pwd);
    }
}