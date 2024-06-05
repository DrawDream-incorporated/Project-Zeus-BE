package com.konada.Konada.repository;

import com.konada.Konada.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

}

