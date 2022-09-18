package com.hotelAlura.modelo;

import java.sql.Date;

public class Huespedes {

	private int id;
	private String nombre;
	private String apellido;
	private Date fechaNacimiento;
	private String nacionalidad;
	private String telefono;

	public Huespedes(int id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return String.format("{ID Huesped: %d | Nombre: %s %s | Fecha nac: %s | Nacionalidad: %s | Tel: %s}", this.id,
				this.nombre, this.apellido, this.fechaNacimiento, this.nacionalidad, this.telefono);
	}
}
