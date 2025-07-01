package org.example.expert.domain.todo.repository;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.SearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository{

    private final TodoJpaRepository jpaRepository;
    private final QueryDslRepositoryImpl queryDslRepository;

    @Override
    public Todo save(Todo todo) {
        return jpaRepository.save(todo);
    }

    @Override
    public Optional<Todo> findById(Long todoId) {
        return jpaRepository.findById(todoId);
    }

    @Override
    public Page<Todo> allCase(String weather, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return jpaRepository.findAllByWeatherAndModifiedAtBetween(weather, startTime, endTime, pageable);
    }

    @Override
    public Page<Todo> onlyWeather(String weather, Pageable pageable) {
        return jpaRepository.findAllByWeather(weather,pageable);
    }

    @Override
    public Page<Todo> caseDate(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return jpaRepository.findAllByModifiedAtBetween(startTime,endTime,pageable);
    }

    @Override
    public Page<Todo> noCase(Pageable pageable) {
        return jpaRepository.findAllByOrderByModifiedAtDesc(pageable);
    }

    @Override
    public Optional<Todo> oneCase(long todoId) {
        return queryDslRepository.getTodo(todoId);
    }

    @Override
    public Page<SearchResponse> search(String keyword, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        return queryDslRepository.search(keyword,startTime,endTime,pageable);
    }
}
