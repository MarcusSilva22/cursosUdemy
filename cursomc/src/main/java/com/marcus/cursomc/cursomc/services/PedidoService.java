package com.marcus.cursomc.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcus.cursomc.cursomc.domain.ItemPedido;
import com.marcus.cursomc.cursomc.domain.PagamentoComBoleto;
import com.marcus.cursomc.cursomc.domain.Pedido;
import com.marcus.cursomc.cursomc.domain.enums.EstadoPagamento;
import com.marcus.cursomc.cursomc.repositories.ItemPedidoRepository;
import com.marcus.cursomc.cursomc.repositories.PagamentoRepository;
import com.marcus.cursomc.cursomc.repositories.PedidoRepository;
import com.marcus.cursomc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private ProdutoService produtoService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoService.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido pedido) {
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);

		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, pedido.getInstante());
		}

		pedidoService.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());

		for (ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.0);
			item.setPreco(produtoService.find(item.getProduto().getId()).getPreco());
			item.setPedido(pedido);
		}

		itemPedidoRepository.saveAll(pedido.getItens());

		return pedido;

	}

}
