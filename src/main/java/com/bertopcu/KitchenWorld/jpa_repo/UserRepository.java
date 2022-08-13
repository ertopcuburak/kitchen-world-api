package com.bertopcu.KitchenWorld.jpa_repo;

import com.bertopcu.KitchenWorld.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>{
    @Query(value = "SELECT * FROM user WHERE email = ?1 AND pwd = ?2", nativeQuery = true)
    User findByEmailAndPwd(String email, String pwd);

    @Query(value = "SELECT * FROM user WHERE u_name = ?1 AND pwd = ?2", nativeQuery = true)
    User findByUnameAndPwd(String userName, String pwd);
}