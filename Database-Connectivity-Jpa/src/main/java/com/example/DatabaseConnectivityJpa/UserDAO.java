package com.example.DatabaseConnectivityJpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Integer> {
}
