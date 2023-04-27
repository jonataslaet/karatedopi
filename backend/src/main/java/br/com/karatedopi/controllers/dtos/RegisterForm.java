package br.com.karatedopi.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String fullname;
    private String father;
    private String mother;
    private String hometown;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdOn = LocalDateTime.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedOn = LocalDateTime.now();

    private String cpf;
    private String rg;
    private Set<String> phoneNumbers = new HashSet<>();

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
    }
}
