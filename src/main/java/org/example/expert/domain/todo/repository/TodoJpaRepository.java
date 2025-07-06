package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface TodoJpaRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u WHERE t.weather LIKE CONCAT('%', :weather, '%' ) ")
    Page<Todo> findAllByWeather(@Param("weather") String weather,
                                Pageable pageable);

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u " +
            "WHERE t.modifiedAt BETWEEN :startTime AND :endTime")
    Page<Todo> findAllByModifiedAtBetween(@Param("startTime")LocalDateTime startTime,
                                          @Param("endTime")LocalDateTime endTime,
                                          Pageable pageable);

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u " +
            "WHERE t.weather LIKE CONCAT('%' ,:weather, '%')  AND t.modifiedAt BETWEEN :startTime AND :endTime")
    Page<Todo> findAllByWeatherAndModifiedAtBetween(@Param("weather") String weather,
                                                    @Param("startTime")LocalDateTime startTime,
                                                    @Param("endTime")LocalDateTime endTime,
                                                    Pageable pageable);

//    @Query("SELECT t FROM Todo t " +
//            "LEFT JOIN t.user " +
//            "WHERE t.id = :todoId")
//    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
