package Obj_Auditoria;

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.GuardarTablasModel;

public class Obj_Calculo_Asignaciones {
	
	Object[][] tabla_model;
	
	public Obj_Calculo_Asignaciones(){
		this.tabla_model = null;
	}

	public Object[][] getTabla_model() {
		return tabla_model;
	}

	public void setTabla_model(Object[][] tabla_model) {
		this.tabla_model = tabla_model;
	}
	
	public Object[][] get_tabla_model(){
		return new BuscarTablasModel().tabla_model_persecciones();
	}
	
	public boolean guardar(Object[][] tabla){
		return new GuardarTablasModel().tabla_model_persecciones(tabla);
	}
}
