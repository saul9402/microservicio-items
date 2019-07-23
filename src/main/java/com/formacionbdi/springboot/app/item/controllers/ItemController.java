package com.formacionbdi.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;
import com.formacionbdi.springboot.app.item.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItemController {

	@Autowired
	/**
	 * Solo como ejemplo para ver que también se puede usar balanceo de carga con
	 * RestTemplate; @Qualifier("serviceRestTemplate")
	 */
	private ItemService itemService;

	@GetMapping(value = "/listar")
	public List<Item> listar() {
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping(value = "/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}

	/**
	 * Este es el metodo alternativo en caso de que el metodo detalle falle por
	 * alguna excepcion, error o bien que el servicio no esté disponible.
	 * 
	 * @param id
	 * @param cantidad
	 * @return
	 */
	public Item metodoAlternativo(Long id, Integer cantidad) {
		Producto producto = new Producto();
		producto.setId(id);
		producto.setNombre("Camara Sony");
		producto.setPrecio(500.00);
		Item item = new Item(producto, cantidad);
		return item;
	}

}
