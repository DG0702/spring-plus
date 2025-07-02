package org.example.expert.aop.enums;

public enum Message {
    MANAGER_REGISTRATION("매니저 등록이 되었습니다"),
    MANAGER_REGISTRATION_FAILED("매니저 등록이 실패 하였습니다.");

    private final String message;

    Message(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

}
