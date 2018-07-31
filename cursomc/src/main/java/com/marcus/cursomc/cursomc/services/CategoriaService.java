package com.marcus.cursomc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcus.cursomc.cursomc.domain.Categoria;
import com.marcus.cursomc.cursomc.dto.CategoriaDTO;
import com.marcus.cursomc.cursomc.repositories.CategoriaRepository;
import com.marcus.cursomc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repo;
	
	public List<Categoria> findAlll(){		
		return repo.findAll();		
	}
	
	public Categoria find(Integer id){
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria categoria){
		return repo.save(categoria);
	}
	
	public Categoria update(Categoria categoria){
		find(categoria.getId());
		return repo.save(categoria);
	}
	
	public void delete(Integer id){
		find(id);
		try{
			repo.deleteById(id);	
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir uma categoria que possui produtos");
		}
		
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO){
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}

}
