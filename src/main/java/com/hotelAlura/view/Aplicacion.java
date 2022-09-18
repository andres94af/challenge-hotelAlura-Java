package com.hotelAlura.view;

import java.sql.Connection;

import javax.swing.JOptionPane;

import com.hotelAlura.DAO.HuespedDAO;
import com.hotelAlura.DAO.ReservaDAO;
import com.hotelAlura.DAO.UsuarioDAO;
import com.hotelAlura.controllers.Controllers;
import com.hotelAlura.factory.ConnectionFactory;


public class Aplicacion {
	
	private final static Connection con = new ConnectionFactory().recibeConeccion();

	public static void main(String[] args) {
		HuespedDAO huesped = new HuespedDAO(con);
		ReservaDAO reserva = new ReservaDAO(con);
		UsuarioDAO usuario = new UsuarioDAO(con);
		Controllers controllers = new Controllers();
		JOptionPane.showMessageDialog(null, "  Bienvenido al Hotel Alura!\nPara loguearse pulse aceptar",
				  "Hotel Alura", JOptionPane.INFORMATION_MESSAGE);
		
		usuario.validarUsuario();
		
		controllers.verOpciones(huesped, reserva);
		
	}
}
