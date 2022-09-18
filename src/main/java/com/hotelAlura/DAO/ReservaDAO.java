package com.hotelAlura.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hotelAlura.modelo.Reservas;

public class ReservaDAO {

	private final Connection con;
	private static int valorPorDia = 30;

	// CONTRUCTOR INICIALIZA CONECCION
	public ReservaDAO(Connection con) {
		this.con = con;
	}

	// CREA NUEVA RESERVA A PARTIR DEL ID DEL NUEVO HUESPED
	public int nuevaReserva(int id) {
		int resultado = 0;
		try {
			final PreparedStatement st = con
					.prepareStatement("INSERT INTO RESERVAS(HUESPED, FECHA_ENTRADA,FECHA_SALIDA, VALOR, FORMA_PAGO) "
							+ "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, id);

			System.out.println("Ingrese fecha de ingreso(AAAA-MM-DD):");
			String fechaIngreso = new Scanner(System.in).nextLine();
			LocalDate fecha1 = LocalDate.parse(fechaIngreso);
			st.setString(2, fechaIngreso);

			System.out.println("Ingrese fecha de salida(AAAA-MM-DD):");
			String fechaSalida = new Scanner(System.in).nextLine();
			LocalDate fecha2 = LocalDate.parse(fechaSalida);
			st.setString(3, fechaSalida);

			int dif = (int) ChronoUnit.DAYS.between(fecha1, fecha2);
			st.setInt(4, dif * valorPorDia);

			System.out.println("Ingrese forma de pago (Efectivo o Tarjeta):");
			st.setString(5, new Scanner(System.in).nextLine());

			try (st) {
				st.execute();
				ResultSet resultSet = st.getGeneratedKeys();
				while (resultSet.next()) {
					int int1 = resultSet.getInt(1);
					System.out.println("Se creo correctamente su reserva con el identificador nº " + int1);
					return resultado = int1;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

	// MODIFICAR VALOR DE RESERVA
	private int valorActualizadoReserva(int id) {
		int valor;
		try {
			final PreparedStatement st = con
					.prepareStatement("SELECT FECHA_ENTRADA, FECHA_SALIDA FROM RESERVAS WHERE ID =" + id);
			try (st) {
				st.execute();
				ResultSet resultSet = st.getResultSet();
				String fechaIngreso = "";
				String fechaSalida = "";
				while (resultSet.next()) {
					fechaIngreso = resultSet.getString("FECHA_ENTRADA");
					fechaSalida = resultSet.getString("FECHA_SALIDA");
				}
				LocalDate fecha1 = LocalDate.parse(fechaIngreso);
				LocalDate fecha2 = LocalDate.parse(fechaSalida);
				int dif = (int) ChronoUnit.DAYS.between(fecha1, fecha2);
				valor = dif * valorPorDia;

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return valor;
	}

	// MUESTRA EN CONSOLA LISTADO DE RESERVAS
	public void verListadoDeReservas() {
		try {
			final PreparedStatement st = con
					.prepareStatement("SELECT ID,HUESPED, FECHA_ENTRADA,FECHA_SALIDA, VALOR, FORMA_PAGO FROM RESERVAS");
			try (st) {
				st.execute();
				ResultSet resultSet = st.getResultSet();
				List<Reservas> resultado = new ArrayList<Reservas>();
				while (resultSet.next()) {
					Reservas fila = new Reservas(resultSet.getInt("ID"), resultSet.getInt("HUESPED"),
							resultSet.getDate("FECHA_ENTRADA"), resultSet.getDate("FECHA_SALIDA"),
							resultSet.getInt("VALOR"), resultSet.getString("FORMA_PAGO"));
					resultado.add(fila);
				}
				resultado.forEach(x -> System.out.println(x));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ELIMINA HUESPED QUE CORRESPONDE AL ID QUE RECIBE POR PARAMETRO
	// TAMBIEN ELIMINA A LA RESERVA QUE TIENE ASOCIADA
	public void eliminarReserva(int id) {
		try {
			final PreparedStatement st = con.prepareStatement("DELETE FROM HUESPEDES WHERE ID= ?");
			try (st) {
				st.setInt(1, id);
				st.execute();
				System.out.println("La reserva con ID: " + id + " fue eliminada.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// MODIFICA LOS DATOS DEL HUESPED
	public void modificarReserva() {
		System.out.println("¿Que reserva de la lista desea modificar?");
		verListadoDeReservas();
		System.out.println("Ingrese numero ID del la reserva a modificar:");
		int id = new Scanner(System.in).nextInt();
		System.out.println("Ingrese que campo desea modificar:");
		String columna = new Scanner(System.in).nextLine().toLowerCase();
		if (columna.equals("id") || columna.equals("huesped") || columna.equals("valor")) {
			System.out.println("No es posible modificar los campos de ID, Huesped y Valor. Seleccione otra por favor.");
			modificarReserva();
		}

		try {
			final PreparedStatement st = con
					.prepareStatement("UPDATE RESERVAS SET " + columna + " = ? WHERE ID = " + id);
			System.out.println("Ingrese nuevo valor de " + columna + ":");
			st.setString(1, new Scanner(System.in).nextLine());

			try (st) {
				st.execute();
				int resultSet = st.getUpdateCount();
				if (resultSet == 1) {
					try {
						final PreparedStatement st2 = con
								.prepareStatement("UPDATE RESERVAS SET VALOR = ? WHERE ID = " + id);
						st2.setInt(1, valorActualizadoReserva(id));

						try (st2) {
							st2.execute();
							int resultSet2 = st2.getUpdateCount();
							if (resultSet2 != 1) {
								System.out.println("No se pudo modificar el valor.");
							}
						}
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					System.out.println("Se modifico correctamente la reserva con ID: " + id);
				} else {
					System.out.println("No se pudo modificar satisfactoriamente, intente luego.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
