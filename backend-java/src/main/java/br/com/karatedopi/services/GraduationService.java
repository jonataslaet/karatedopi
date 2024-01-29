package br.com.karatedopi.services;

import br.com.karatedopi.entities.Graduation;
import br.com.karatedopi.repositories.GraduationRepository;
import br.com.karatedopi.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GraduationService {

	private final GraduationRepository graduationRepository;

	@Transactional(readOnly = true)
	public Graduation getGraduation(String belt) {
		Graduation foundGraduation = graduationRepository.findGraduationByBelt(belt);
		if (Objects.isNull(foundGraduation)) {
			throw new ResourceNotFoundException("Graduação não encontrada para a faixa " + belt);
		}
		return foundGraduation;
	}
}

