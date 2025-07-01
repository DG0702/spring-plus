package org.example.expert.domain.todo.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.SearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
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

    public Page<SearchResponse> search (String keyword, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable) {
        List<SearchResponse> responses = queryFactory.select(Projections.constructor(
                        SearchResponse.class,
                        todo.title,
                        manager.countDistinct(),
                        comment.countDistinct()
                ))
                .from(todo)
                .leftJoin(todo.comments, comment)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .where(
                        keywordContainsInTitleOrNickname(keyword),
                        startTimeGoe(startTime),
                        endTimeLoe(endTime)
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCountLong = queryFactory.select(todo.count())
                .from(todo)
                .leftJoin(todo.comments, comment)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .where(
                        keywordContainsInTitleOrNickname(keyword),
                        startTimeGoe(startTime),
                        endTimeLoe(endTime)
                )
                .fetchOne();

        int totalCount = (totalCountLong != null) ? totalCountLong.intValue() : 0 ;

        return new PageImpl<>(responses,pageable,totalCount);
    }

    private BooleanExpression titleContainsKeyword(String keyword){
        return StringUtils.hasText(keyword) ? todo.title.containsIgnoreCase(keyword) : null;
    }

    private BooleanExpression startTimeGoe(LocalDateTime startTime){
        return startTime != null ? todo.createdAt.goe(startTime) : null;
    }

    private BooleanExpression endTimeLoe(LocalDateTime endTime){
        return endTime != null ? todo.createdAt.loe(endTime) : null;
    }

    private BooleanExpression nicknameContainsKeyword(String keyword){
        return StringUtils.hasText(keyword) ? manager.user.nickname.containsIgnoreCase(keyword) : null;
    }

    private BooleanExpression keywordContainsInTitleOrNickname(String keyword) {
        BooleanExpression title = titleContainsKeyword(keyword);
        BooleanExpression nickname = nicknameContainsKeyword(keyword);

        if (title != null && nickname != null) {
            return title.or(nickname);
        } else if (title != null) {
            return title;
        } else {
            return nickname;
        }
    }

}

