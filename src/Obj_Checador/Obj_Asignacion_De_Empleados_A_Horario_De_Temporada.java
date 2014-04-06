package Obj_Checador;

import Conexiones_SQL.GuardarTablasModel;

public class Obj_Asignacion_De_Empleados_A_Horario_De_Temporada {
	
	Object[][] tabla_model;
	String fecha_inicial;
	String fecha_final;
	
	public Obj_Asignacion_De_Empleados_A_Horario_De_Temporada(){
		this.tabla_model = null;	this.fecha_inicial="";	this.fecha_final="";
	}

	public Object[][] getTabla_model() {
		return tabla_model;
	}

	public void setTabla_model(Object[][] tabla_model) {
		this.tabla_model = tabla_model;
	}

	public String getFecha_inicial() {
		return fecha_inicial;
	}

	public void setFecha_inicial(String fecha_inicial) {
		this.fecha_inicial = fecha_inicial;
	}

	public String getFecha_final() {
		return fecha_final;
	}

	public void setFecha_final(String fecha_final) {
		this.fecha_final = fecha_final;
	}
	public boolean guardar(Object[][] tabla, String fechaIn,String fechaFin,String horarioTemporal){
		return new GuardarTablasModel().tabla_horario_temporado(tabla,fechaIn,fechaFin,horarioTemporal);
	}
	
	public boolean borrar(int folio, String establecimiento,String puesto,String fecha1,String fecha2){
		return new GuardarTablasModel().borrar_empleado_con_horario_temporal(folio, establecimiento, puesto, fecha1, fecha2);
	}
}
