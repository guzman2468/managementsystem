package com.library.managementsystem.model.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.managementsystem.model.MessageReponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends MessageReponse {

    private Integer id;
    private String name;
    private String email;
    private String role;

    //figure out how to clean up the response payload
    public UserResponse(String message) {
        super(message);
    }

    public UserResponse(String email, Integer id, String name, String role) {
        super(null);
        this.email = email;
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
