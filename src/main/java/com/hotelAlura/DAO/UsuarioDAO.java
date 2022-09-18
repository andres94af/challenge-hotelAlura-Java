package com.hotelAlura.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class UsuarioDAO {

	private Connection con;
	static int intentos = 0;

	public UsuarioDAO(Connection con) {
		this.con = con;
	}

	public void validarUsuario() {
		String datoObtenidoUsuario = JOptionPane.showInputDialog("Usuario:");
		String datoObtenidoContraseña = JOptionPane.showInputDialog("Contraseña:");

		try {
			final PreparedStatement st = con.prepareStatement("SELECT NOMBRE_USUARIO, CONTRASEÑA FROM USUARIOS");
			try (st) {
				st.execute();
				ResultSet resultSet = st.getResultSet();

				while (resultSet.next()) {
					String usuario = resultSet.getString("NOMBRE_USUARIO");
					String contraseña = resultSet.getString("CONTRASEÑA");

					if (datoObtenidoUsuario.equals(usuario) && datoObtenidoContraseña.equals(contraseña)) {
						JOptionPane.showMessageDialog(null, "Se logueo correctamente!",
								  "Hotel Alura", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
				if (intentos == 2) {
					JOptionPane.showMessageDialog(null, "No puede seguir intentando.\n    Se cerrara el programa",
							  "Hotel Alura", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña invalidos. Intente de nuevo.","Hotel Alura", JOptionPane.WARNING_MESSAGE);
					intentos++;
					validarUsuario();
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
