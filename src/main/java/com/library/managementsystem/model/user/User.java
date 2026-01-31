package com.library.managementsystem.model.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "name must not be null or blank")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "email must not be null or blank")
    @Column(unique = true, name = "email")
    private String email;


    @Column(name = "password_hash", nullable = false)
    @JsonIgnore
    private String passwordHash;

    //@NotBlank(message = "role must not be null or blank")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String name, String email, UserRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    //potentially add things so that the # of books checkedout appear
}
