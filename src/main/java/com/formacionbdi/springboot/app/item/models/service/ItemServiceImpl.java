package com.formacionbdi.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.item.models.Producto;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

	@Autowired
	RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays
				/**
				 * Al usar el balanceo de carga en el rest template (usando la
				 * anotacion @LoadBalanced en la clase AppConfig) en vez de usar el
				 * http://localhost:8001 (todo hardocodeado) se usa el nombre del servicio como
				 * ruta, :3. Ahora esa configuración queda en el application.yml en la parte de
				 * listOfServers de este microservicio
				 */
				.asList(clienteRest.getForObject("http://servicio-productos/listar", Producto[].class));
		return productos.stream().map(producto -> {
			return new Item(producto, 1);
		}).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Producto producto = clienteRest.getForObject("http://servicio-productos/ver/{id}", Producto.class,
				pathVariables);
		return new Item(producto, cantidad);
	}

}
