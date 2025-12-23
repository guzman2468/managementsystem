package com.library.managementsystem.repository;

import com.library.managementsystem.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

    @Query(value = "SELECT * FROM app_user WHERE name = :name AND email = :email", nativeQuery = true)
    User findByNameAndEmail(@Param("name") String name, @Param("email") String email);
}