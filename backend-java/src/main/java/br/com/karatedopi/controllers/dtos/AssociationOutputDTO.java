package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Association;
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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(allowGetters = true)
public class AssociationOutputDTO {

    private Long id;
    private String businessName;
    private String tradeName;
    private String associationAbbreviation;
    private String federationAbbreviation;
    private LocalDate foundationDate;
    private String ein;
    private String email;
    private String presidentName;
    private OrganizationStatus status;

    @Builder.Default
    private Set<String> phoneNumbers = new HashSet<>();

    @JsonProperty("address")
    private AddressDTO addressDTO;

    public static AssociationOutputDTO getAssociationOutputDTO(Association association) {
        return AssociationOutputDTO.builder()
                .id(association.getId())
                .associationAbbreviation(association.getAssociationAbbreviation())
                .businessName(association.getBusinessName())
                .tradeName(association.getTradeName())
                .foundationDate(association.getFoundationDate())
                .ein(association.getEin())
                .email(Objects.isNull(association.getEmail()) ?
                        null:association.getEmail())
                .presidentName(Objects.isNull(association.getPresident()) ?
                        null:association.getPresident().getFullname())
                .status(association.getStatus())
                .phoneNumbers(association.getPhoneNumbers())
                .addressDTO(AddressDTO.getAddressDTO(association.getAddress()))
                .federationAbbreviation(Objects.isNull(association.getFederation())?
                        null:association.getFederation().getFederationAbbreviation())
                .build();
    }

}
