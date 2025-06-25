package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class QueryDslRepositoryImpl {

    private final JPAQueryFactory queryFactory;

    public Optional<Todo> getTodo(Long todoId) {

        // select t from Todo t
        // left join user u on t.user_id = u.id
        // where t.id = :todoId

        return Optional.ofNullable(queryFactory.selectFrom(todo)
                .leftJoin(todo.user, user)
                .fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchFirst());
    }




}
