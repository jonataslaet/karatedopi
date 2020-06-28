package br.com.karatedopi.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.karatedopi.controllers.cadastros.CadastroTransferenciaDeFaixa;
import br.com.karatedopi.controllers.dtos.TransferenciaDeFaixaDto;
import br.com.karatedopi.controllers.errors.ObjectNotFoundException;
import br.com.karatedopi.domain.Perfil;
import br.com.karatedopi.domain.TransferenciaDeFaixa;
import br.com.karatedopi.domain.Usuario;
import br.com.karatedopi.repositories.PerfilRepository;
import br.com.karatedopi.repositories.TransferenciaDeFaixaRepository;
import br.com.karatedopi.repositories.UsuarioRepository;

@Service
public class TransferenciaDeFaixaService {
	@Autowired
	TransferenciaDeFaixaRepository tr;
	
	@Autowired
	UsuarioRepository ur;
	
	@Autowired
	PerfilRepository pr;
	
	// Retorna todas as transferências
	public ResponseEntity<List<TransferenciaDeFaixaDto>> retornaTransferenciasDeFaixa() {
		List<TransferenciaDeFaixaDto> transferenciasEncontradas = TransferenciaDeFaixaDto
				.transferenciaDeFaixaDto(tr.findAll());
		return ResponseEntity.ok().body(transferenciasEncontradas);
	}

	// Retorna uma transferência em específico
	public ResponseEntity<TransferenciaDeFaixaDto> retornaTransferenciaDeFaixa(Long id) {
		Optional<TransferenciaDeFaixa> transfers = tr.findById(id);
		if (!transfers.isPresent()) {
			throw new ObjectNotFoundException("Transferência não encontrada");
		}
		TransferenciaDeFaixaDto localidadeDto = new TransferenciaDeFaixaDto(transfers.get());
		return ResponseEntity.ok().body(localidadeDto);
	}

	// Cadastra uma nova transferência
	public ResponseEntity<TransferenciaDeFaixaDto> criaTransferenciaDeFaixa(
			CadastroTransferenciaDeFaixa cadastroTransferenciaDeFaixa) {

		// Usuario logado
		Usuario usuarioLogado = ur.findById(1L).get();
		
		//Perfil logado
		Perfil perfilLogado = pr.findById(1L).get();

		// Monta a transferência e já relaciona ao perfil logado
		TransferenciaDeFaixa transfer = new TransferenciaDeFaixa(cadastroTransferenciaDeFaixa.getFaixaOrigem(),
				cadastroTransferenciaDeFaixa.getFaixaDestino(),
				cadastroTransferenciaDeFaixa.getDataTransferenciaDeFaixa());
		transfer.setPerfil(perfilLogado);
		perfilLogado.getTransferenciasDeFaixa().add(transfer);

		// Salva a transferência
		tr.save(transfer);

		// Cria o dto da transferência
		TransferenciaDeFaixaDto transferDto = new TransferenciaDeFaixaDto(transfer);

		// Gera o link da nova transferência
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(transferDto.getId()).toUri();

		// Envia na resposta o código de criação e no header envie o link gerado da nova
		// transferência
		return ResponseEntity.created(location).build();
	}
}
