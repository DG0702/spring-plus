package org.example.expert.aop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.expert.aop.enums.Message;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Message message;

    @Column(nullable = false)
    private String korMessage;

    @Column(nullable = false)
    private String requestMethod;

    @Column(nullable = false)
    private String requestURI;

    @Column(nullable = false)
    private long executionTime;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Log(Message message, String korMessage, String requestMethod, String requestURI, long executionTime, LocalDateTime createdAt) {
        this.message = message;
        this.korMessage = korMessage;
        this.requestMethod = requestMethod;
        this.requestURI = requestURI;
        this.executionTime = executionTime;
        this.createdAt = createdAt;
    }




}
