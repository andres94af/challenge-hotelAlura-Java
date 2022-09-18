package com.hotelAlura.controllers;

import java.sql.Connection;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.hotelAlura.DAO.HuespedDAO;
import com.hotelAlura.DAO.ReservaDAO;
import com.hotelAlura.DAO.UsuarioDAO;
import com.hotelAlura.factory.ConnectionFactory;

public class Controllers {
	
	private final static Connection con = new ConnectionFactory().recibeConeccion();
	Scanner scan = new Scanner (System.in);
	HuespedDAO huesped = new HuespedDAO(con);
	ReservaDAO reserva = new ReservaDAO(con);
	UsuarioDAO usuario = new UsuarioDAO(con);
	
	public void verOpciones() {
		String seleccion = JOptionPane.showInputDialog(null,"Seleccione que desea hacer.",
				   "Hotel Alura", JOptionPane.QUESTION_MESSAGE, null,
				  new Object[] { "Iniciar una reserva","Anular una reserva", "Modificar datos de un huesped",
						  "Modificar datos de una reserva", "Ver listados","Cerrar sesion y salir del programa", },
				  			"Iniciar una reserva").toString();
		switch (seleccion) {
		case "Iniciar una reserva":
			int nroHuesped = huesped.nuevoHuesped();	
			reserva.nuevaReserva(nroHuesped);
			verOpciones();
			break;
		case "Anular una reserva":
			reserva.verListadoDeReservas();
			int id=Integer.parseInt(JOptionPane.showInputDialog("¿Que reserva desea eliminar?\nIngrese el numero de ID de la reserva a eliminar"));
			reserva.eliminarReserva(id);
			verOpciones();
			break;
		case "Modificar datos de un huesped":
			huesped.modificarHuesped();
			verOpciones();
			break;
		case "Modificar datos de una reserva":
			reserva.modificarReserva();
			verOpciones();
			break;
		case "Ver listados":
			seleccionListados();
			break;
		case "Cerrar sesion y salir del programa":
			System.exit(0);
			break;
		}
	}

	public void seleccionListados() {
		String seleccion = JOptionPane.showInputDialog(null,"Seleccione que desea hacer.",
				   "Hotel Alura", JOptionPane.QUESTION_MESSAGE, null,
				  new Object[] {"Ver listado de huespedes","Ver listado de reservas", "Volver a las opciones",},
				  "Ver listado de reservas").toString();
		switch (seleccion) {
		case "Ver listado de huespedes":
			huesped.verListadoDeHuespedes();
			verOpciones();
			break;
		case "Ver listado de reservas":
			reserva.verListadoDeReservas();
			verOpciones();
			break;
		case "Volver a las opciones":
			verOpciones();
			break;
		}
	}

	public void validaUsuario() {
		usuario.validarUsuario();
	}
}