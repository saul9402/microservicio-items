package com.formacionbdi.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.formacionbdi.springboot.app.commons.models.entity.Producto;

/**
 * Se indica que esto es un cleinte feign, en el "name" indicas a que servicio
 * te vas a conectar, este nombre se define en el application.yml del servicio a
 * conectarse
 */
@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	/**
	 * aqui es donde se define el verbo http y la ruta del servicio que va a
	 * consumir este método así como lo que va a devolver despues de haber consumido
	 * dicho servicio
	 * 
	 */
	@GetMapping(value = "/listar")
	public List<Producto> listar();

	@GetMapping(value = "/ver/{id}")
	public Producto detalle(@PathVariable Long id);

	@PostMapping(value = "/crear")
	public Producto crear(@RequestBody Producto producto);

	@PutMapping(value = "/editar/{id}")
	public Producto update(@RequestBody Producto producto, @PathVariable Long id);

	@DeleteMapping(value = "/eliminar/{id}")
	public void eliminar(@PathVariable Long id);
}
