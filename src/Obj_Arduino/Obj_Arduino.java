package Obj_Arduino;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;

public class Obj_Arduino
{
	String ma�ana;
	String mediodia;
	String tarde;
	
	public Obj_Arduino()
	{
		this.ma�ana="";
		this.mediodia="";
		this.tarde="";
	}

	public String getMa�ana() {
		return ma�ana;
	}

	public void setMa�ana(String ma�ana) {
		this.ma�ana = ma�ana;
	}

	public String getMediodia() {
		return mediodia;
	}

	public void setMediodia(String mediodia) {
		this.mediodia = mediodia;
	}

	public String getTarde() {
		return tarde;
	}

	public void setTarde(String tarde) {
		this.tarde = tarde;
	}
	
	public Obj_Arduino Buscar(){
		try {
			return new BuscarSQL().Buscar_Arduino();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
}
