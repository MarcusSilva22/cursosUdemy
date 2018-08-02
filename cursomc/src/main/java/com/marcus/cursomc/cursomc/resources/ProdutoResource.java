package com.marcus.cursomc.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcus.cursomc.cursomc.domain.Produto;
import com.marcus.cursomc.cursomc.dto.ProdutoDTO;
import com.marcus.cursomc.cursomc.resources.utils.URL;
import com.marcus.cursomc.cursomc.services.ProdutoService;;;;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		Produto obj = service.find(id);
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="0") String nome, 
			@RequestParam(value="categorias", defaultValue="0") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="limit", defaultValue="24") Integer limit, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction){	
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);		
		Page<Produto> listCategorias = service.search(nomeDecoded, ids, page, limit, orderBy, direction);
		Page<ProdutoDTO> listDTO = listCategorias.map(produto -> new ProdutoDTO(produto));
		return ResponseEntity.ok().body(listDTO);		
	}
}
