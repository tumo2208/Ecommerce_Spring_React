package com.spring.ecommerce.Repositories;

import com.spring.ecommerce.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
