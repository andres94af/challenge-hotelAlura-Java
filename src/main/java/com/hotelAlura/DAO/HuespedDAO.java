package com.hotelAlura.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
		JOptionPane.showMessageDialog(null, "Primero ingresará los datos del huesped","Hotel Alura", JOptionPane.INFORMATION_MESSAGE);
		int resultado = 0;
		try {
			final PreparedStatement st = con
					.prepareStatement("INSERT INTO HUESPEDES(NOMBRE,APELLIDO,FECHA_NACIMIENTO,NACIONALIDAD,TELEFONO) "
							+ "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, JOptionPane.showInputDialog("Ingrese nombre del huesped:"));
			st.setString(2, JOptionPane.showInputDialog("Ingrese apellido:"));
			st.setString(3, JOptionPane.showInputDialog("Ingrese fecha de nacimiento (AAA-MM-DD):"));
			st.setString(4, JOptionPane.showInputDialog("Ingrese nacionalidad:"));
			st.setString(5, JOptionPane.showInputDialog("Ingrese telefono de contacto:"));

			try (st) {
				st.execute();
				ResultSet resultSet = st.getGeneratedKeys();
				while (resultSet.next()) {
					int int1 = resultSet.getInt(1);
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
		verListadoDeHuespedes();
		int id=Integer.parseInt(JOptionPane.showInputDialog("¿Que huesped de la lista desea modificar?\n        Ingrese el numero de ID:"));
		String columna = JOptionPane.showInputDialog(null,"Seleccione el campo a modificar:",
				   "Hotel Alura", JOptionPane.QUESTION_MESSAGE, null,
				  new Object[] { "Nombre", "Apellido", "Fecha_Nacimiento", "Nacionalidad", "Telefono"},"Nombre").toString().toLowerCase();

		try {
			final PreparedStatement st = con
					.prepareStatement("UPDATE HUESPEDES SET " + columna + " = ? WHERE ID = " + id);
			System.out.println("Ingrese nuevo valor de " + columna + ":");
			st.setString(1, JOptionPane.showInputDialog("Ingrese nuevo valor de " + columna + ":"));

			try (st) {
				st.execute();
				int resultSet = st.getUpdateCount();
				System.out.println(resultSet);
				if (resultSet == 1) {
					JOptionPane.showMessageDialog(null, "Se modifico correctamente la el huesped con ID: " + id,"Hotel Alura", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "No se pudo modificar satisfactoriamente, intente luego.","Hotel Alura", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
