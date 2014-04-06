package Obj_Lista_de_Raya;

import java.sql.SQLException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Fue_Sodas_AUXF {
	private int folio;
	private String ticket;
	private String nombre_completo;
    private double cantidad;
    private String fecha;
    private int status_ticket;
    
    public Obj_Fue_Sodas_AUXF(){
    	this.ticket = "";
    	this.nombre_completo = "";
    	this.cantidad =0.0;
    	this.fecha = "";
    	this.status_ticket=0;
    }

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombreCompleto) {
		nombre_completo = nombreCompleto;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String folio) {
		this.ticket = folio;
	}

	public String getNombre_Completo() {
		return nombre_completo;
	}

	public void setNombre_Completo(String nombreCompleto) {
		nombre_completo = nombreCompleto;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public int getStatus_ticket() {
		return status_ticket;
	}

	public void setStatus_ticket(int statusTicket) {
		status_ticket = statusTicket;
	}
	
	// Funcion Guardar Empleado
	public boolean guardar(){ return new GuardarSQL().Guardar_fuente_sodas_auxf(this); }
	
	public boolean actualizar(int folio){ return new ActualizarSQL().fuente_sodas_auxf(this,folio); }
	
	public Obj_Fue_Sodas_AUXF buscar(int folio) throws SQLException{ return new BuscarSQL().fuente_sodas_ax(folio); }
	
	public Obj_Fue_Sodas_AUXF maximo() throws SQLException{ return new BuscarSQL().MaximoFuente_auxf(); }
	
	public boolean borrar(int Id){ return new ActualizarSQL().eliminarListaFuenteSodas_auxf(Id); }
	
	

}
