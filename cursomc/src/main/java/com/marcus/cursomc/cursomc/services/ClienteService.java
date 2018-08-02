package com.marcus.cursomc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcus.cursomc.cursomc.domain.Cidade;
import com.marcus.cursomc.cursomc.domain.Cliente;
import com.marcus.cursomc.cursomc.domain.Endereco;
import com.marcus.cursomc.cursomc.domain.enums.TipoCliente;
import com.marcus.cursomc.cursomc.dto.ClienteDTO;
import com.marcus.cursomc.cursomc.dto.ClienteNewDTO;
import com.marcus.cursomc.cursomc.repositories.ClienteRepository;
import com.marcus.cursomc.cursomc.repositories.EnderecoRepository;
import com.marcus.cursomc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id){
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Page<Cliente> findPage(Integer page, Integer limit, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, limit, Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}

	public List<Cliente> findAll() {	
		return repo.findAll();
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {		
		cliente = repo.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}

	public Cliente update(Cliente cliente) {
		Cliente oldCliente = find(cliente.getId());
		updateData(oldCliente, cliente);
		return repo.save(oldCliente);
	}

	public void delete(Integer id) {
		find(id);
		try{
			repo.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir porque há entidades relacionadas.");
		}
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteDTO) {			
 		Cliente cli = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteDTO.getTipo()));
		
 		Cidade cid = new Cidade(clienteDTO.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(clienteDTO.getTelefone1());
		
		if (clienteDTO.getTelefone2()!=null) {
			cli.getTelefones().add(clienteDTO.getTelefone2());
		}
		if (clienteDTO.getTelefone3()!=null) {
			cli.getTelefones().add(clienteDTO.getTelefone3());
		}
		
		return cli;		
	}
	
	private void updateData(Cliente oldCliente, Cliente cliente){
		oldCliente.setNome(cliente.getNome());
		oldCliente.setEmail(cliente.getEmail());
	}

}
