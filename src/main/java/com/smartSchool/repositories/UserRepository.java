package com.smartSchool.repositories;

import com.smartSchool.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByFullName(String fullName);

    @Query("SELECT u FROM User u WHERE u.email = :identifier OR u.username = :identifier")
    Optional<User> findByEmailOrUsername(@Param("identifier") String identifier);

    List<User> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String nameKeyword, String emailKeyword);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}