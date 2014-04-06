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
public class Cat_Reporte_General_de_Asistencia_Por_Establecimiento_Mas_Registros_Faltantes extends JFrame {

	@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Reporte_General_de_Asistencia_Por_Establecimiento_Mas_Registros_Faltantes(String fecha_inicio, String fecha_final, String Establecimiento) {
			String query = "exec sp_Reporte_General_de_Asistencia_Por_Establecimiento_Faltantes '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"';";
			Statement stmt = null;
			try {
				stmt =  new Connexion().conexion().createStatement();
			    ResultSet rs = stmt.executeQuery(query);
			    
				JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_General_de_Asistencia_Por_Establecimiento_Faltantes.jrxml");
				JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
				JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
				JasperViewer.viewReport(print, false);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	
}
