package com.hotelAlura.pruebas;

import java.sql.Connection;

import com.hotelAlura.DAO.UsuarioDAO;
import com.hotelAlura.factory.ConnectionFactory;


public class PruebaMetodoValidarUsuario {
	private final static Connection con = new ConnectionFactory().recibeConeccion();
	public static void main(String[] args) {
		UsuarioDAO listaUsuarios = new UsuarioDAO(con);
		listaUsuarios.validarUsuario();
	}
}
