package br.com.karatedopi.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRegistrationResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String fullname;
    private String father;
    private String mother;
    private String hometown;
    private String cpf;
    private String rg;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime registrationDate;

    private Set<String> phoneNumbers = new HashSet<>();
}
