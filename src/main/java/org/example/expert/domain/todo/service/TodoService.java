package org.example.expert.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.dto.request.SearchRequest;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.response.SearchResponse;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;


    public TodoSaveResponse saveTodo(AuthUser authUser, TodoSaveRequest todoSaveRequest) {
        User user = User.fromAuthUser(authUser);

        String weather = weatherClient.getTodayWeather();

        Todo newTodo = new Todo(
                todoSaveRequest.getTitle(),
                todoSaveRequest.getContents(),
                weather,
                user
        );
        Todo savedTodo = todoRepository.save(newTodo);

        return new TodoSaveResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getContents(),
                weather,
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    public Page<TodoResponse> getTodos(int page, int size, String weather, LocalDateTime startTime, LocalDateTime endTime) {
        Pageable pageable = PageRequest.of(page - 1, size);

        if((weather != null) && (startTime != null) && (endTime != null)) {
            Page<Todo> todos = todoRepository.allCase(weather, startTime, endTime, pageable);
            return todos.map(todo -> new TodoResponse(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getContents(),
                    todo.getWeather(),
                    new UserResponse(todo.getUser().getId(), todo.getUser().getEmail()),
                    todo.getCreatedAt(),
                    todo.getModifiedAt()
            ));
        }

        if((weather != null) && (!weather.isEmpty())){
            Page<Todo> todos = todoRepository.onlyWeather(weather,pageable);
            return todos.map(todo -> new TodoResponse(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getContents(),
                    todo.getWeather(),
                    new UserResponse(todo.getUser().getId(), todo.getUser().getEmail()),
                    todo.getCreatedAt(),
                    todo.getModifiedAt()
            ));
        }

        if((startTime != null) && (endTime != null)) {
            Page<Todo> todos = todoRepository.caseDate(startTime,endTime,pageable);
            return todos.map(todo -> new TodoResponse(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getContents(),
                    todo.getWeather(),
                    new UserResponse(todo.getUser().getId(), todo.getUser().getEmail()),
                    todo.getCreatedAt(),
                    todo.getModifiedAt()
            ));
        }



        Page<Todo> todos = todoRepository.noCase(pageable);

        return todos.map(todo -> new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(todo.getUser().getId(), todo.getUser().getEmail()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        ));
    }

    public TodoResponse getTodo(long todoId) {
        Todo todo = todoRepository.oneCase(todoId)
                .orElseThrow(() -> new InvalidRequestException("Todo not found"));

        User user = todo.getUser();

        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(user.getId(), user.getEmail()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }

    // 검색 기능
    public Page<SearchResponse> search (int page, int size, SearchRequest searchRequest){
        Pageable pageable = PageRequest.of(page-1, size,Sort.by("createdAt").descending());

        String keyword = searchRequest.getKeyword();
        LocalDateTime startTime = searchRequest.getStartTime();
        LocalDateTime endTime = searchRequest.getEndTime();

        return todoRepository.search(keyword, startTime, endTime, pageable);
    }

}
