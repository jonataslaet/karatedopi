package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.services.exceptions.ForbiddenOperationException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(allowSetters = true)
public class FederationInputDTO {

    private String businessName;
    private String tradeName;
    private String federationAbbreviation;
    private String ein;
    private String email;

    @Builder.Default
    private Set<String> phoneNumbers = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate foundationDate;

    @JsonProperty("address")
    private AddressDTO addressDTO;

    public void setFoundationDate(String foundationDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.foundationDate = LocalDate.parse(foundationDate, formatter);
        } catch (DateTimeException e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            this.foundationDate = LocalDate.parse(foundationDate, formatter);
        } catch (Exception e) {
            throw new ForbiddenOperationException(e.getLocalizedMessage());
        }
    }

}
