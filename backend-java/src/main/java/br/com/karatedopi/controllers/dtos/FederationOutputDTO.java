package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Federation;
import br.com.karatedopi.entities.enums.OrganizationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(allowGetters = true)
public class FederationOutputDTO {

    private Long id;
    private String businessName;
    private String tradeName;
    private String federationAbbreviation;
    private LocalDate foundationDate;
    private String ein;
    private String email;
    private OrganizationStatus status;

    @Builder.Default
    private Set<String> phoneNumbers = new HashSet<>();

    private String presidentName;

    @JsonProperty("address")
    private AddressDTO addressDTO;

    public static FederationOutputDTO getFederationOutputDTO(Federation federation) {
        return FederationOutputDTO.builder()
                .id(federation.getId())
                .federationAbbreviation(federation.getFederationAbbreviation())
                .businessName(federation.getBusinessName())
                .tradeName(federation.getTradeName())
                .foundationDate(federation.getFoundationDate())
                .ein(federation.getEin())
                .email(Objects.isNull(federation.getEmail()) ?
                        null:federation.getEmail())
                .presidentName(Objects.isNull(federation.getPresident()) ?
                        null:federation.getPresident().getFullname())
                .status(federation.getStatus())
                .phoneNumbers(federation.getPhoneNumbers())
                .addressDTO(AddressDTO.getAddressDTO(federation.getAddress()))
                .build();
    }
}
