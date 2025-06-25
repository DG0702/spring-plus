package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository {

    Todo save(Todo todo);

    Optional<Todo> findById(Long todoId);

    Page<Todo> allCase(String weather, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    Page<Todo> onlyWeather(String weather, Pageable pageable);

    Page<Todo> caseDate(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    Page<Todo> noCase(Pageable pageable);

    Optional<Todo> oneCase(long todoId);
}
