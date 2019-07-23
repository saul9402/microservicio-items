package com.formacionbdi.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.item.clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.item.models.Item;

@Service
@Primary
public class ItemServiceFeign implements ItemService {
	@Autowired
	private ProductoClienteRest clienteFeign;

	@Override
	public List<Item> findAll() {
		/**
		 * Por defecto el timeout está configurado a 1 segundo. Si tarda más de eso
		 * marcara error
		 */
		return clienteFeign.listar().stream().map(producto -> {
			return new Item(producto, 1);
		}).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		/**
		 * Por defecto el timeout está configurado a 1 segundo. Si tarda más de eso
		 * marcara error
		 */
		return new Item(clienteFeign.detalle(id), cantidad);
	}

}
