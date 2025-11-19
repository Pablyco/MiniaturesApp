package com.example.miniatures.repository;

import com.example.miniatures.model.MiniatureClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiniatureClientRepository extends JpaRepository<MiniatureClient, Long> {
}
