package com.marcus.cursomc.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.marcus.cursomc.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pgto, Date instanciaDoPedido){
		Calendar calendario = Calendar.getInstance();		
		calendario.setTime(instanciaDoPedido);
		calendario.add(Calendar.DAY_OF_MONTH, 7);
		pgto.setDataVencimento(calendario.getTime());
	}

}
