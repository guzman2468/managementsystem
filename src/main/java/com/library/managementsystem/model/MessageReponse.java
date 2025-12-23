package com.library.managementsystem.model;

public class MessageReponse {

    private String message;

    public MessageReponse(String message) {
        this.message = message;
    }

    public MessageReponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
