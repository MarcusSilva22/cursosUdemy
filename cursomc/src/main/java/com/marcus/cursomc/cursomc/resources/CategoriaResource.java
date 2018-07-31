package com.marcus.cursomc.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.marcus.cursomc.cursomc.domain.Categoria;
import com.marcus.cursomc.cursomc.dto.CategoriaDTO;
import com.marcus.cursomc.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="limit", defaultValue="24") Integer limit, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		Page<Categoria> listCategorias = service.findPage(page, limit, orderBy, direction);
		Page<CategoriaDTO> listDTO = listCategorias.map(categoria -> new CategoriaDTO(categoria));
		return ResponseEntity.ok().body(listDTO);		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> find(){
		List<Categoria> listCategorias = service.findAlll();		
		List<CategoriaDTO> listDTO = listCategorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		Categoria obj = service.find(id);
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)	
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO){
		Categoria categoria = service.fromDTO(categoriaDTO);
		categoria = service.insert(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoriaDTO.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)	
	public ResponseEntity<Void> update(@RequestBody Categoria categoria, @PathVariable Integer id){
		categoria = service.update(categoria);		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
