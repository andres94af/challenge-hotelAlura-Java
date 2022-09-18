package com.hotelAlura.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hotelAlura.modelo.Huespedes;

public class HuespedDAO {

	private final Connection con;
	Scanner scan = new Scanner(System.in);

	// CONSTRUCTOR DONDE INICIALIZA LA CONECCION
	public HuespedDAO(Connection con) {
		this.con = con;
	}

	// CREA UN NUEVO HUESPED Y DEVUELVE EL NUMERO DE ID
	public int nuevoHuesped() {
		System.out.println("Comencemos...");
		System.out.println("Primero los datos del huesped.");
		int resultado = 0;
		try {
			final PreparedStatement st = con
					.prepareStatement("INSERT INTO HUESPEDES(NOMBRE,APELLIDO,FECHA_NACIMIENTO,NACIONALIDAD,TELEFONO) "
							+ "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			System.out.println("Ingrese nombre:");
			st.setString(1, scan.nextLine());
			System.out.println("Ingrese apellido:");
			st.setString(2, scan.nextLine());
			System.out.println("Ingrese fecha de nacimiento (AAA-MM-DD):");
			st.setString(3, scan.nextLine());
			System.out.println("Ingrese nacionalidad:");
			st.setString(4, scan.nextLine());
			System.out.println("Ingrese telefono(xxxxxxxxx):");
			st.setString(5, scan.nextLine());

			try (st) {
				st.execute();
				ResultSet resultSet = st.getGeneratedKeys();
				while (resultSet.next()) {
					int int1 = resultSet.getInt(1);
					System.out.println("Se creo el huesped con ID: " + int1);
					return resultado = int1;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

	// IMPRIME EN CONSOLA EL LISTADO DE HUESPEDES COMPLETO
	public void verListadoDeHuespedes() {
		try {
			final PreparedStatement st = con.prepareStatement(
					"SELECT ID,NOMBRE,APELLIDO,FECHA_NACIMIENTO,NACIONALIDAD,TELEFONO FROM HUESPEDES");
			try (st) {
				st.execute();
				ResultSet resultSet = st.getResultSet();
				List<Huespedes> resultado = new ArrayList<Huespedes>();
				while (resultSet.next()) {
					Huespedes fila = new Huespedes(resultSet.getInt("ID"), resultSet.getString("NOMBRE"),
							resultSet.getString("APELLIDO"), resultSet.getDate("FECHA_NACIMIENTO"),
							resultSet.getString("NACIONALIDAD"), resultSet.getString("TELEFONO"));
					resultado.add(fila);
				}
				resultado.forEach(x -> System.out.println(x));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// MODIFICA LOS DATOS DEL HUESPED
	public void modificarHuesped() {
		System.out.println("¿Que huesped de la lista desea modificar?");
		verListadoDeHuespedes();
		System.out.println("Ingrese numero ID del huesped a modificar:");
		int id = new Scanner(System.in).nextInt();
		System.out.println("Ingrese que campo desea modificar:");
		String columna = new Scanner(System.in).nextLine();

		try {
			final PreparedStatement st = con
					.prepareStatement("UPDATE HUESPEDES SET " + columna + " = ? WHERE ID = " + id);
			System.out.println("Ingrese nuevo valor de " + columna + ":");
			st.setString(1, new Scanner(System.in).nextLine());

			try (st) {
				st.execute();
				int resultSet = st.getUpdateCount();
				System.out.println(resultSet);
				if (resultSet == 1) {
					System.out.println("Se modifico correctamente el huesped con ID: " + id);
				} else {
					System.out.println("No se modifico. Hubo un error intente luego.");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
