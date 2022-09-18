package com.hotelAlura.controllers;

import java.util.Scanner;

import com.hotelAlura.DAO.HuespedDAO;
import com.hotelAlura.DAO.ReservaDAO;

public class Controllers {
	
	Scanner scan = new Scanner (System.in);
	
	public void verOpciones(HuespedDAO huesped, ReservaDAO reserva) {
		System.out.println("Que desea hacer? Ingrese:");
		System.out.println("1 -> Iniciar una reserva.");
		System.out.println("2 -> Anular una reserva.");
		System.out.println("3 -> Modificar datos de un huesped.");
		System.out.println("4 -> Modificar datos de una reserva.");
		System.out.println("5 -> Ver listados.");
		System.out.println("6 -> Cerrar sesion y salir del programa.");
		int seleccion = scan.nextInt();
		switch (seleccion) {
		case 1:
			int nroHuesped = huesped.nuevoHuesped();	
			System.out.println("Ahora los datos para su reserva");	
			int nroReserva = reserva.nuevaReserva(nroHuesped);
			verOpciones(huesped, reserva);
			break;
		case 2:
			System.out.println("¿Que reserva desea eliminar?");
			reserva.verListadoDeReservas();
			System.out.println("Ingrese el numero de ID de la reserva a eliminar:");
			int id = scan.nextInt();
			reserva.eliminarReserva(id);
			verOpciones(huesped, reserva);
			break;
		case 3:
			huesped.modificarHuesped();
			verOpciones(huesped, reserva);
			break;
		case 4:
			reserva.modificarReserva();
			verOpciones(huesped, reserva);
			break;
		case 5:
			seleccionListados(huesped, reserva);
			break;
		case 6:
			System.exit(0);
			break;

		default:
			System.out.println("No ingreso ningun numero de la lista. Vuelva a intentar");
			verOpciones(huesped, reserva);
		}
	}

	public void seleccionListados(HuespedDAO huesped, ReservaDAO reserva) {
		System.out.println("1 -> Ver listado de huespedes.");
		System.out.println("2 -> Ver listado de reservas.");
		System.out.println("3 -> Volver a las opciones.");
		int seleccion = scan.nextInt();
		switch (seleccion) {
		case 1:
			huesped.verListadoDeHuespedes();
			verOpciones(huesped, reserva);
			break;
		case 2:
			reserva.verListadoDeReservas();
			verOpciones(huesped, reserva);
			break;
		case 3:
			verOpciones(huesped, reserva);
			break;
		default:
			System.out.println("No ingreso ningun numero de la lista. Vuelva a intentar");
			seleccionListados(huesped, reserva);
		}
	}
}
