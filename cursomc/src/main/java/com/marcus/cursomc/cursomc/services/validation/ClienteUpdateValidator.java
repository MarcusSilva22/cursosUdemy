package com.marcus.cursomc.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.marcus.cursomc.cursomc.domain.Cliente;
import com.marcus.cursomc.cursomc.dto.ClienteDTO;
import com.marcus.cursomc.cursomc.repositories.ClienteRepository;
import com.marcus.cursomc.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
	
		List<FieldMessage> list = new ArrayList<>();
	
		Cliente cliente = clienteRepository.findByEmail(objDto.getEmail());
		
		if ( !objDto.getId().equals(cliente.getId()) && cliente != null){
			list.add(new FieldMessage("email", "Email já existente."));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}