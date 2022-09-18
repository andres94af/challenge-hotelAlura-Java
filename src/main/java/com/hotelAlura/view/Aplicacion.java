package com.hotelAlura.view;

import javax.swing.JOptionPane;
import com.hotelAlura.controllers.Controllers;

public class Aplicacion {

	public static void main(String[] args) {

		Controllers controllers = new Controllers();
		JOptionPane.showMessageDialog(null, "  Bienvenido al Hotel Alura!\nPara loguearse pulse aceptar",
				  "Hotel Alura", JOptionPane.INFORMATION_MESSAGE);	
		controllers.validaUsuario();
		controllers.verOpciones();
	}
}
