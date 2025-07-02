package org.example.expert.aop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.expert.aop.dto.SaveLogDTO;
import org.example.expert.aop.entity.Log;
import org.example.expert.aop.repository.LogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    @Transactional
    public void logSave(SaveLogDTO dto){
        Log entity = dto.toEntity(
                dto.getMessage(),
                dto.getKorMessage(),
                dto.getMethod(),
                dto.getURI(),
                dto.getExecutionTime(),
                dto.getCreateAt());

        logRepository.save(entity);
    }

}
