package br.com.karatedopi.controllers;

import br.com.karatedopi.controllers.dtos.AddressDTO;
import br.com.karatedopi.controllers.dtos.CityDTO;
import br.com.karatedopi.entities.enums.StateAbbreviation;
import br.com.karatedopi.services.AddressService;
import br.com.karatedopi.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/addresses")
@RequiredArgsConstructor
public class AddressController {

	private final AddressService addressService;
	
	@GetMapping
	public ResponseEntity<Page<AddressDTO>> getPagedAddresses(
		@RequestParam(required = false) String city,
		@RequestParam(required = false) String state,
		@PageableDefault(sort="street", direction = Direction.ASC) Pageable pageable
	){
		Page<AddressDTO> pagedAddresses = addressService.getPagedAddresses(city, state, pageable);
		return ResponseEntity.ok().body(pagedAddresses);
	}
}