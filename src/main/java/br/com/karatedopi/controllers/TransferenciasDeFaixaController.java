package br.com.karatedopi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.karatedopi.controllers.cadastros.CadastroTransferenciaDeFaixa;
import br.com.karatedopi.controllers.dtos.TransferenciaDeFaixaDto;
import br.com.karatedopi.services.TransferenciaDeFaixaService;

@RestController
@RequestMapping(value="/transferenciasdefaixa")
public class TransferenciasDeFaixaController {
	
	@Autowired
	TransferenciaDeFaixaService ts;
	
	@GetMapping(value ="/{id}")
	public ResponseEntity<TransferenciaDeFaixaDto> buscaTransferenciaDeFaixa(@PathVariable Long id){
		return ts.retornaTransferenciaDeFaixa(id);
	}
	
	// Retorna todos os usuários
	@GetMapping
	public ResponseEntity<List<TransferenciaDeFaixaDto>> listarTransferenciaDeFaixaDto(){
		return ts.retornaTransferenciasDeFaixa();
	}

	// Cadastra um novo usuario
	@PostMapping
	public ResponseEntity<TransferenciaDeFaixaDto> criaPerfil(@Valid @RequestBody CadastroTransferenciaDeFaixa transfer) {
		return ts.criaTransferenciaDeFaixa(transfer);
	}

}
