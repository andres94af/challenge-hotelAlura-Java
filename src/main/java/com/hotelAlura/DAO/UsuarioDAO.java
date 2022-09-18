package com.hotelAlura.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UsuarioDAO {

	private Connection con;
	static int intentos = 0;
	
	public UsuarioDAO(Connection con) {
		this.con = con;
	}
	
	public void validarUsuario() {
		System.out.println("Usuario:");
		String datoObtenidoUsuario = new Scanner(System.in).nextLine();
		System.out.println("Contraseña:");
		String datoobtenidoContraseña = new Scanner(System.in).nextLine();

		try {
			final PreparedStatement st = con.prepareStatement("SELECT NOMBRE_USUARIO, CONTRASEÑA FROM USUARIOS");
			try (st) {
				st.execute();
				ResultSet resultSet = st.getResultSet();

				while (resultSet.next()) {
					String usuario = resultSet.getString("NOMBRE_USUARIO");
					String contraseña = resultSet.getString("CONTRASEÑA");

					if (datoObtenidoUsuario.equals(usuario) && datoobtenidoContraseña.equals(contraseña)) {
						System.out.println("Se logueo correctamente");
						return;
					}
				}
					if (intentos == 2) {
						System.out.println("No puede seguir intentando");
						System.exit(0);
					} else {
						System.out.println("Usuario o contraseña invalidos. Intente de nuevo.");
						intentos++;
						validarUsuario();
					}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
