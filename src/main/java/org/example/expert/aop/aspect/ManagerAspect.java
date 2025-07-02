package org.example.expert.aop.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.aop.dto.SaveLogDTO;
import org.example.expert.aop.enums.Message;
import org.example.expert.aop.service.LogService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ManagerAspect {

    private final HttpServletRequest request;
    private final LogService logService;

    // Point cut
    @Pointcut("@annotation(org.example.expert.aop.annotation.ManagerRegistrationCheck)")
    public void managerCheck(){}

    // Advice
    @Around("managerCheck()")
    public Object enrollManager(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();

        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        Message message = Message.MANAGER_REGISTRATION;
        String korMeesage = Message.MANAGER_REGISTRATION.getMessage();
        LocalDateTime requestTime = LocalDateTime.now();



        try{
            log.info("Request: Method : {} requestURI : {}  requestTime : {}", requestMethod, requestURI , requestTime);
            
            // 메서드 실행
            Object result = joinPoint.proceed();

            log.info("Response : requestURI :  {} message : {}", requestURI , korMeesage);

            return result;
        }
        catch(Exception e){
            // 매니저 등록 실패 시
            korMeesage = Message.MANAGER_REGISTRATION_FAILED.getMessage();
            log.info("exception : message : {}", korMeesage );
            throw e;
        }finally {
            long endTime = System.currentTimeMillis();
            long execution = endTime - startTime;
            log.info(":: execution time : {} ms", execution);

            SaveLogDTO saveLogDTO = new SaveLogDTO(message, korMeesage, requestMethod, requestURI, execution, requestTime);

            logService.logSave(saveLogDTO);
        }
    }



}
