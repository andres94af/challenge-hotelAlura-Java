package com.hotelAlura.modelo;

import java.sql.Date;

public class Reservas {

	private int id;
	private int idHuesped;
	private Date fechaEntrada;
	private Date fechaSalida;
	private int valor;
	private String formaPago;
	
//	private static int precio = 30;

	public Reservas(int id, int idHuesped, Date fechaEntrada, Date fechaSalida, int valor, String formaPago) {
		this.id = id;
		this.idHuesped = idHuesped;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.formaPago = formaPago;
		this.valor = valor;
//		int dias = (int) ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
//		this.valor = dias * precio;
	}
	

	public int getId() {
		return id;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public int getValor() {
		return valor;
	}

	public String getFormaPago() {
		return formaPago;
	}

	@Override
	public String toString() {
		return String.format("{ID Reserva: %d | ID Huesped: %d | Fecha de entrada: %s | Fecha de salida: %s | Valor: €%d | Forma de pago: %s}",
				this.id,
				this.idHuesped,
				this.fechaEntrada,
				this.fechaSalida,
				this.valor,
				this.formaPago);
	}
	
}
