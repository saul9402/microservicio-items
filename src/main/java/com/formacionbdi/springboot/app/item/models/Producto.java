package com.formacionbdi.springboot.app.item.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

	private Long id;

	private String nombre;

	private Double precio;

	private Date createAt;

	private Integer port;

}
