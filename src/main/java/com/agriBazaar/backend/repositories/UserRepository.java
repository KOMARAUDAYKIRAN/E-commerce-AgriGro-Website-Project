package com.agriBazaar.backend.repositories;

import com.agriBazaar.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
