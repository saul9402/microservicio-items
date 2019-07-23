package com.formacionbdi.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.service.ItemService;

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

	@GetMapping(value = "/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemService.findById(id, cantidad);
	}

}
