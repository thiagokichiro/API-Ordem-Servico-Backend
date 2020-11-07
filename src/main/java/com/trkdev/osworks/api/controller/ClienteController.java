package com.trkdev.osworks.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trkdev.osworks.domain.model.Cliente;
import com.trkdev.osworks.domain.repository.ClienteRepository;
import com.trkdev.osworks.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CadastroClienteService cadastroCliente;

	@GetMapping
	public List<Cliente> listar() {
		// return clienteRepository.findAll();
		// return clienteRepository.findByNomeContaining("Thiago");
		return clienteRepository.findAll();
	}

	@GetMapping("{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);

		// Se exuste o cliente, retorna status OK (código 200(
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}

		// Se não, retorna como não encontrado (código 404)
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return cadastroCliente.salvar(cliente);
	}

	@PutMapping("{clienteId}")
	public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente) {

		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}

		cliente.setId(clienteId);
		cliente = cadastroCliente.salvar(cliente);

		return ResponseEntity.ok(cliente);

	}

	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
		if (!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}

		cadastroCliente.excluir(clienteId);

		return ResponseEntity.noContent().build();
	}

}
