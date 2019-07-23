package com.formacionbdi.springboot.app.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
 * Habilitamos los clientes feign que se tengan implementados en el proyecto y
 * permite inyectar los clientes en los controladores o cualquier componente.
 */
@EnableFeignClients

/*
 * Con esto se habilita el balanceo de carga del lado del cliente. Hay dos
 * anotaciones @RibbonClient y @RibbonClients una es para un cliente y la otra
 * es para multiples clientes, en este caso solo hay uno ProductoClienteRest. En
 * el name le indicas el microservicio al que requieres conectarte tanto Feign
 * como Ribbon se integran para hacer el balance de carga.
 * 
 * 
 * Se comenta ya que ahora es eureka quien administra toda esta conifguración
 */
//@RibbonClient(name = "servicio-productos")

/*
 * Con esta anotación se habilita este proyecto como cliente de eureka; en el
 * video dice que no es obligatorio hacerlo ya que con el solo hecho de tener la
 * dependencia se configura pero recomienda hacerlo
 */
@EnableEurekaClient

@SpringBootApplication
public class SpringbootServicioItemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioItemApplication.class, args);
	}

}
