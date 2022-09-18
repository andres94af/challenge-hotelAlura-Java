package com.hotelAlura.modelo;

public class Usuarios {

	private int id;
	private String nombreUsuario;
	private String contraseña;

	public Usuarios(int id, String nombreUsuario, String contraseña) {
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
	}
	
	@Override
	public String toString() {
		return String.format("{Nombre usuario: %s | Contraseña: %s ", this.nombreUsuario, this.contraseña);
	}

}
