package com.formacionbdi.springboot.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.item.models.Item;
import com.formacionbdi.springboot.app.commons.models.entity.Producto;
import com.formacionbdi.springboot.app.item.models.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

/**
 * Esta anotación permitirá actualizar las propiedades que se cambien en los
 * archivos de propiedades sin necesidad de reiniciar la aplicación.
 *
 */
@RefreshScope

@RestController
@Slf4j
public class ItemController {

	@Autowired
	private Environment env;

	@Autowired
	/**
	 * Solo como ejemplo para ver que también se puede usar balanceo de carga con
	 * RestTemplate; @Qualifier("serviceRestTemplate")
	 */
	private ItemService itemServiceFeign;

	@Value("${configuracion.texto}")
	private String texto;

	@GetMapping(value = "/listar")
	public List<Item> listar() {
		return itemServiceFeign.findAll();
	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping(value = "/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		return itemServiceFeign.findById(id, cantidad);
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

	@GetMapping(value = "/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto) {

		log.info(texto);

		Map<String, String> json = new HashMap<String, String>();
		json.put("texto", texto);
		json.put("puerto", env.getProperty("local.server.port"));

		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}

	@PostMapping(value = "/crear")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return itemServiceFeign.save(producto);
	}

	@PutMapping(value = "/editar/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		return itemServiceFeign.update(producto, id);
	}

	@DeleteMapping(value = "/eliminar/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		itemServiceFeign.delete(id);
	}

}
