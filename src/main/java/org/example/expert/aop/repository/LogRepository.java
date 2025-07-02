package org.example.expert.aop.repository;

import org.example.expert.aop.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
