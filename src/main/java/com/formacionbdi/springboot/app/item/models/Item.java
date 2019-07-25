package com.formacionbdi.springboot.app.item.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.formacionbdi.springboot.app.commons.models.entity.Producto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	private Producto producto;
	private Integer cantidad;

	public Double getTotal() {
		return producto.getPrecio() * cantidad.doubleValue();
	}

}
