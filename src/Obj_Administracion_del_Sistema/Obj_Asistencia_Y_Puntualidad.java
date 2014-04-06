package Obj_Administracion_del_Sistema;

import java.sql.SQLException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Asistencia_Y_Puntualidad {
	private float ValorAsistencia;
	private float ValorPuntualidad;
	private float valorGafete;
	private int existe;

	public Obj_Asistencia_Y_Puntualidad() {
		this.ValorAsistencia=0; this.ValorPuntualidad=0; this.existe=0;
	}

	public float getValorAsistencia() {
		return ValorAsistencia;
	}

	public void setValorAsistencia(float valorAsistencia) {
		ValorAsistencia = valorAsistencia;
	}

	public float getValorPuntualidad() {
		return ValorPuntualidad;
	}

	public void setValorPuntualidad(float valorPuntualidad) {
		ValorPuntualidad = valorPuntualidad;
	}
	
	public int getExiste() {
		return existe;
	}

	public void setExiste(int existe) {
		this.existe = existe;
	}
	
	public float getValorGafete() {
		return valorGafete;
	}

	public void setValorGafete(float valorGafete) {
		this.valorGafete = valorGafete;
	}

	public boolean guardar(){ return new GuardarSQL().Asistencia_Puntualidad(this); }
	
	public boolean actualizar(int folio){ return new ActualizarSQL().Asistecia_Puntualidad(this,folio); }
	
	public Obj_Asistencia_Y_Puntualidad nuevo() throws SQLException { return new BuscarSQL().Asistencia_Puntualidad(); }
	
	public Obj_Asistencia_Y_Puntualidad buscar(int folio) throws SQLException { return new BuscarSQL().Asistencia_Puntualidad(folio); }

}
