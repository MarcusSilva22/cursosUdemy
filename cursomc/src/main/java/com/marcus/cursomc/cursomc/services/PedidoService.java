package com.marcus.cursomc.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcus.cursomc.cursomc.domain.Pedido;
import com.marcus.cursomc.cursomc.repositories.PedidoRepository;
import com.marcus.cursomc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id){
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
