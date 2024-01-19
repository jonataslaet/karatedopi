package br.com.karatedopi.services;

import br.com.karatedopi.entities.PasswordRecovery;
import br.com.karatedopi.entities.User;
import br.com.karatedopi.repositories.PasswordRecoveryRepository;
import br.com.karatedopi.services.exceptions.ResourceStorageException;
import br.com.karatedopi.services.exceptions.TokenExpirationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private final PasswordRecoveryRepository passwordRecoveryRepository;


    public List<PasswordRecovery> getValidPasswordRecoveries(String uuidToken, Instant now) {
        List<PasswordRecovery> passwordRecoveries =
                passwordRecoveryRepository.findValidPasswordRecoveries(uuidToken, now);
        validPasswordRecoveries(passwordRecoveries);
        return passwordRecoveries;
    }

    private void validPasswordRecoveries(List<PasswordRecovery> passwordRecoveries) {
        if (Objects.isNull(passwordRecoveries) || passwordRecoveries.isEmpty()) {
            throw new TokenExpirationException("O token de recuperação é inválido. Por favor, tente novamente.");
        }
    }

    @Transactional
    public PasswordRecovery savePasswordRecovery(PasswordRecovery passwordRecovery) {
        try {
            return passwordRecoveryRepository.save(passwordRecovery);
        } catch (Exception e) {
            throw new ResourceStorageException("Problema desconhecido ao salvar recuperação de senha");
        }
    }
}
