package com.hotelAlura.pruebas;

import java.sql.Connection;

import com.hotelAlura.DAO.HuespedDAO;
import com.hotelAlura.DAO.ReservaDAO;
import com.hotelAlura.controllers.Controllers;
import com.hotelAlura.factory.ConnectionFactory;

public class PruebaControllers {
	private final static Connection con = new ConnectionFactory().recibeConeccion();
	public static void main(String[] args) {
		Controllers controllers = new Controllers();
		HuespedDAO huesped = new HuespedDAO(con);
		ReservaDAO reserva = new ReservaDAO(con);
		
//		controllers.seleccionListados(huesped, reserva);
		
//		controllers.verOpciones(huesped, reserva);
		
//		huesped.modificarHuesped();
		
//		reserva.modificarReserva();
		
		reserva.modificarReserva();
		
	}
}
