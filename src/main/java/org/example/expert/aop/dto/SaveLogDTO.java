package org.example.expert.aop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.aop.entity.Log;
import org.example.expert.aop.enums.Message;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaveLogDTO {
    private Message message;
    private String korMessage;
    private String method;
    private String URI;
    private long executionTime;
    private LocalDateTime createAt;

    public Log toEntity(Message message, String korMessage , String method, String URI ,long executionTime, LocalDateTime createAt) {
        return new Log(message, korMessage , method, URI, executionTime, createAt);
    }
}
