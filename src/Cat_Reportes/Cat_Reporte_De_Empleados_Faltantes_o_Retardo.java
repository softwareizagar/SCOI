package Cat_Reportes;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JFrame;

import Conexiones_SQL.Connexion;


import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Empleados_Faltantes_o_Retardo extends JFrame {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Reporte_De_Empleados_Faltantes_o_Retardo(int reporte,String Establecimiento,int tiempo) {
		
		String query="";
		Statement stmt = null;
//		exec sp_select_empleados_que_no_han_checado
//		 sp_select_empleados_faltantes_de_checar
		switch(reporte){
		case 1:	query = "exec sp_select_empleados_que_no_han_checado '"+Establecimiento+"';";
		System.out.println(query);
				try {
					stmt = new Connexion().conexion().createStatement();
					
				    ResultSet rs = stmt.executeQuery(query);
	
				    JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Empleados_Faltantes_Del_Dia.jrxml");
					JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
					JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
					JasperViewer.viewReport(print, false);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
		break;
		case 2:	query = "exec sp_select_empleados_con_retardo_en_checador '"+Establecimiento+"',"+tiempo+";";
				try {
					stmt =  new Connexion().conexion().createStatement();
				    ResultSet rs = stmt.executeQuery(query);

				    JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Empleados_Con_Retardo_En_Checadas_Del_Dia.jrxml");
					JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
					JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
					JasperViewer.viewReport(print, false);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
		break;
		}
	}
}
