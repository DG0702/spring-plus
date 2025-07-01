package org.example.expert.domain.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SearchRequest {

    private String keyword;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
