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

import javax.swing.JOptionPane;

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
		JOptionPane.showMessageDialog(null, "Ahora, los datos para su reserva","Hotel Alura", JOptionPane.INFORMATION_MESSAGE);
		int resultado = 0;
		try {
			final PreparedStatement st = con
					.prepareStatement("INSERT INTO RESERVAS(HUESPED, FECHA_ENTRADA,FECHA_SALIDA, VALOR, FORMA_PAGO) "
							+ "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, id);
			String fechaIngreso = JOptionPane.showInputDialog("Ingrese fecha de ingreso (AAAA-MM-DD):");
			LocalDate fecha1 = LocalDate.parse(fechaIngreso);
			st.setString(2, fechaIngreso);

			String fechaSalida = JOptionPane.showInputDialog("Ingrese fecha de salida (AAAA-MM-DD):");
			LocalDate fecha2 = LocalDate.parse(fechaSalida);
			st.setString(3, fechaSalida);

			int dif = (int) ChronoUnit.DAYS.between(fecha1, fecha2);
			st.setInt(4, dif * valorPorDia);

			st.setString(5, JOptionPane.showInputDialog(null,"Seleccione forma de pago:",
					   "Hotel Alura", JOptionPane.QUESTION_MESSAGE, null,
						  new Object[] { "Efectivo","Tarjeta", "Transferencia"},"Efectivo").toString());

			try (st) {
				st.execute();
				ResultSet resultSet = st.getGeneratedKeys();
				while (resultSet.next()) {
					int int1 = resultSet.getInt(1);
					JOptionPane.showMessageDialog(null, "Se creo correctamente su reserva con el ID nº: " + int1,
							  "Hotel Alura", JOptionPane.INFORMATION_MESSAGE);
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
				JOptionPane.showMessageDialog(null,"La reserva con ID: " + id + " fue eliminada.",
						  "Hotel Alura", JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// MODIFICA LOS DATOS DEL HUESPED
	public void modificarReserva() {
		verListadoDeReservas();
		int id=Integer.parseInt(JOptionPane.showInputDialog("¿Que reserva de la lista desea modificar?\n          Ingrese el numero de ID:"));
		String columna = JOptionPane.showInputDialog(null,"Seleccione el campo a modificar:",
				   "Hotel Alura", JOptionPane.QUESTION_MESSAGE, null,
				  new Object[] { "Fecha_Entrada","Fecha_Salida", "Forma_Pago",},"Fecha_Entrada").toString().toLowerCase();

		try {
			final PreparedStatement st = con.prepareStatement("UPDATE RESERVAS SET " + columna + " = ? WHERE ID = " + id);
			
			if (columna.equals("forma_pago")) {
				st.setString(1, JOptionPane.showInputDialog(null,"Seleccione nueva forma de pago:",
						   "Hotel Alura", JOptionPane.QUESTION_MESSAGE, null,
							  new Object[] { "Efectivo","Tarjeta", "Transferencia"},"Efectivo").toString());
			}else {
				st.setString(1, JOptionPane.showInputDialog("Ingrese nuevo valor de : " + columna));
			}

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
								JOptionPane.showMessageDialog(null, "No se pudo modificar el valor","Hotel Alura", JOptionPane.ERROR_MESSAGE);
							}
						}
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					JOptionPane.showMessageDialog(null, "Se modifico correctamente la reserva con ID: " + id,"Hotel Alura", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "No se pudo modificar satisfactoriamente, intente luego.","Hotel Alura", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
