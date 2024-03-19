package br.com.karatedopi.controllers.dtos;

import br.com.karatedopi.entities.Address;
import br.com.karatedopi.entities.City;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    private Long id;

    private String street;
    private String number;
    private String zipCode;
    private String neighbourhood;
    private CityDTO city;

    public static AddressDTO getAddressDTO(Address address) {
        City city = address.getCity();
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .zipCode(address.getZipCode())
                .neighbourhood(address.getNeighbourhood())
                .city(CityDTO.getCityDTO(city))
                .build();
    }
}

