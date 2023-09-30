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
public class RegisterDTO {

    private Long id;
    private String email;
    private String password;
    private String fullname;
    private String father;
    private String mother;
    private String zipCode;
    private String street;
    private String number;
    private String neighbourhood;
    private String city;
    private String state;
    private String bloodType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Builder.Default
    private LocalDateTime updatedOn = LocalDateTime.now();

    private String cpf;
    private String rg;

    @Builder.Default
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
