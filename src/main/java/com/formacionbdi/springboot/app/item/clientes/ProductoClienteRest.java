package com.formacionbdi.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.formacionbdi.springboot.app.item.models.Producto;

//Se indica que esto es un cleinte feign
//en el "name" indicas a que servicio te vas a conectar, 
//este nombre se define en el application.yml del servicio a conectarse
@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	// aqui es donde se define el metodo y la ruta del servicio que va a consumir
	// este metodo
	@GetMapping(value = "/listar")
	public List<Producto> listar();

	@GetMapping(value = "/ver/{id}")
	public Producto detalle(@PathVariable Long id);
}
