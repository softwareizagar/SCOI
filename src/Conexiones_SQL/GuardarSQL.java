package Conexiones_SQL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

import Obj_Administracion_del_Sistema.Obj_Asistencia_Y_Puntualidad;
import Obj_Administracion_del_Sistema.Obj_Configuracion_Base_de_Datos;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Auditoria.Obj_Abono_Clientes;
import Obj_Auditoria.Obj_Actividades_Por_Proyecto;
import Obj_Auditoria.Obj_Actividades_Relacionadas;
import Obj_Auditoria.Obj_Alimentacion_Cortes;
import Obj_Auditoria.Obj_Alimentacion_Denominacion;
import Obj_Auditoria.Obj_Clientes;
import Obj_Auditoria.Obj_Denominaciones;
import Obj_Auditoria.Obj_Divisas_Y_Tipo_De_Cambio;
import Obj_Auditoria.Obj_Movimiento_De_Asignacion;
import Obj_Checador.Obj_Alimentacion_De_Permisos_A_Empleados;
import Obj_Checador.Obj_Dias_Inhabiles;
import Obj_Checador.Obj_Horarios;
import Obj_Checador.Obj_Mensaje_Personal;
import Obj_Checador.Obj_Mensajes;
import Obj_Checador.Obj_Solicitud_De_Empleados;
import Obj_Contabilidad.Obj_Importar_Voucher;
import Obj_Contabilidad.Obj_Proveedores;
import Obj_Evaluaciones.Obj_Actividad;
import Obj_Evaluaciones.Obj_Actividad_Asignadas_Nivel_Jerarquico;
import Obj_Evaluaciones.Obj_Atributos;
import Obj_Evaluaciones.Obj_Captura_Del_Cuadrante_Personal;
import Obj_Evaluaciones.Obj_Cuadrante;
import Obj_Evaluaciones.Obj_Directorios;
import Obj_Evaluaciones.Obj_Empleados_En_Cuadrantes;
import Obj_Evaluaciones.Obj_Equipo_De_Trabajo;
import Obj_Evaluaciones.Obj_Jefatura;
import Obj_Evaluaciones.Obj_Nivel_Critico;
import Obj_Evaluaciones.Obj_Nivel_Jerarquico;
import Obj_Evaluaciones.Obj_Opciones_De_Respuestas;
import Obj_Evaluaciones.Obj_Ponderacion;
import Obj_Evaluaciones.Obj_Temporada;
import Obj_Lista_de_Raya.Obj_Alimentacion_De_Vacaciones;
import Obj_Lista_de_Raya.Obj_Asignacion_Mensajes;
import Obj_Lista_de_Raya.Obj_Bono_Complemento_Sueldo;
import Obj_Lista_de_Raya.Obj_Captura_Fuente_Sodas;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Diferencia_De_Cortes;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Lista_de_Raya.Obj_Grupo_De_Vacaciones;
import Obj_Lista_de_Raya.Obj_Nomina;
import Obj_Lista_de_Raya.Obj_Prestamos;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Lista_de_Raya.Obj_Rango_De_Prestamos;
import Obj_Lista_de_Raya.Obj_Sueldos;
import Obj_Lista_de_Raya.Obj_Tabla_De_Vacaciones;
import Obj_Lista_de_Raya.Obj_Tipo_De_Bancos;
import Obj_Lista_de_Raya.Obj_Fue_Sodas_AUXF;
import Obj_Lista_de_Raya.Obj_Fue_Sodas_DH;
import Obj_Matrices.Obj_Aspectos_De_La_Etapa;
import Obj_Matrices.Obj_Etapas;
import Obj_Matrices.Obj_Unidades_de_Inspeccion;



public class GuardarSQL {
	String Qbitacora ="exec sp_insert_bitacora ?,?,?,?,?";
	PreparedStatement pstmtb = null;
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	public boolean Guardar_Empleado(Obj_Empleados empleado){
		String query = "exec sp_insert_empleado ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
			// insert bitacora
			String pc = InetAddress.getLocalHost().getHostName();
			String ip = InetAddress.getLocalHost().getHostAddress();
			pstmtb = con.prepareStatement(Qbitacora);
			pstmtb.setString(1, pc);
			pstmtb.setString(2, ip);
			pstmtb.setInt(3, usuario.getFolio());
			pstmtb.setString(4, "sp_insert_empleado alta "+empleado.getNombre().toUpperCase()+empleado.getAp_paterno().toUpperCase()+empleado.getAp_materno().toUpperCase());
			pstmtb.setString(5, "Empleados Nuevo");
			pstmtb.executeUpdate();
			
//			private String telefono_cuadrante;
			int i=1;
			pstmt = con.prepareStatement(query);
			pstmt.setString(i,		empleado.getNo_checador());
			pstmt.setString(i+=1, 	empleado.getNombre().toUpperCase());
			pstmt.setString(i+=1,	empleado.getAp_paterno().toUpperCase());
			pstmt.setString(i+=1,	empleado.getAp_materno().toUpperCase());
			pstmt.setString(i+=1,	empleado.getFecha_nacimiento());
			pstmt.setString(i+=1,	empleado.getCalle().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getColonia().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getPoblacion().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getTelefono_familiar().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getTelefono_propio().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getRfc().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getCurp().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getSexo());
			
			FileInputStream stream_foto = new FileInputStream(empleado.getFoto());
			pstmt.setBinaryStream(i+=1, stream_foto, empleado.getFoto().length());
			
//			laboral
			pstmt.setInt(i+=1, 		empleado.getHorario());
			pstmt.setInt(i+=1, 		empleado.getHorario2());
			pstmt.setInt(i+=1, 		empleado.getStatus_h1());
			pstmt.setInt(i+=1, 		empleado.getStatus_h2());
			pstmt.setInt(i+=1, 		empleado.getStatus_rotativo());
			pstmt.setString(i+=1, 	empleado.getFecha_ingreso().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getStatus());	
			pstmt.setInt(i+=1, 		empleado.isCuadrante_parcial() ? 1 : 0);
			pstmt.setInt(i+=1, 		empleado.getDepartameto());	
			pstmt.setString(i+=1, 	empleado.getImss().toUpperCase().trim().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getStatus_imss());
			pstmt.setString(i+=1, 	empleado.getNumero_infonavit().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getEstablecimiento());
			pstmt.setInt(i+=1, 		empleado.getPuesto());
			
//			percepciones y deducciones
			pstmt.setFloat(i+=1, 	empleado.getSalario_diario());
			pstmt.setFloat(i+=1, 	empleado.getSalario_diario_integrado());
			pstmt.setString(i+=1,	empleado.getForma_pago().toUpperCase());
			pstmt.setInt(i+=1,		empleado.getSueldo());
			pstmt.setInt(i+=1, 		empleado.getBono());
			pstmt.setInt(i+=1, 		empleado.getPrestamo());
			pstmt.setFloat(i+=1, 	empleado.getPension_alimenticia());
			pstmt.setFloat(i+=1,	empleado.getInfonavit());
			pstmt.setString(i+=1, 	empleado.getTargeta_nomina().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getTipo_banco());
			pstmt.setBoolean(i+=1, (empleado.isGafete())? true: false);
			pstmt.setBoolean(i+=1, (empleado.isFuente_sodas())? true: false);
			pstmt.setString(i+=1, 	empleado.getObservasiones().toUpperCase());
			
			pstmt.setString(i+=1, 	empleado.getFecha_actualizacion().toUpperCase());
			
			pstmt.setInt(i+=1, empleado.getHorario3());
			pstmt.setInt(i+=1, empleado.getStatus_h3());
			
			pstmt.setString(i+=1, empleado.getFecha_ingreso_imss());
			pstmt.setString(i+=1, empleado.getFecha_vencimiento_licencia());
			
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Empleado ] Insert  SQLException: sp_insert_empleado "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Empleado ] Insert  SQLException: sp_insert_empleado "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Establecimiento(Obj_Establecimiento establecimiento){
		String query = "exec sp_insert_establecimiento ?,?,?,?,? ";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, establecimiento.getEstablecimiento().toUpperCase().trim());
			pstmt.setString(2, establecimiento.getAbreviatura().toUpperCase().trim());
			pstmt.setString(3, establecimiento.getSerie().toUpperCase().trim());
			pstmt.setInt(4, establecimiento.getGrupo_cheque());
			pstmt.setInt(5, establecimiento.getStatus());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Puesto(Obj_Puestos puesto){
		String query = "exec sp_insert_puesto ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, puesto.getPuesto().toUpperCase().trim());
			pstmt.setString(2, puesto.getAbreviatura().toUpperCase().trim());
			pstmt.setString(3, (puesto.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Puesto ] Insert  SQLException: sp_insert_puesto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Puesto ] Insert  SQLException: sp_insert_puesto "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Puesto ] Insert  SQLException: sp_insert_puesto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		}		
		return true;
	}
	
	public boolean Guardar_Dia_Inhabil(Obj_Dias_Inhabiles diaInA){
		String query = "exec sp_insert_diaInHabil ?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, diaInA.getFecha());
			pstmt.setString(2, diaInA.getDescripcion().toUpperCase());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Dia_Inhabil ] Insert  SQLException: sp_insert_diaInHabil "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Dia_Inhabil ] Insert  SQLException: sp_insert_diaInHabil "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Dia_Inhabil ] Insert  SQLException: sp_insert_diaInHabil "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Atributos(Obj_Atributos atrib){
		String query = "exec sp_insert_atributo		 ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, atrib.getDescripcion().toUpperCase());
			pstmt.setFloat(2, atrib.getValor());
			pstmt.setString(3, (atrib.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Atributos ] Insert  SQLException: sp_insert_atributo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Atributos ] Insert  SQLException: sp_insert_atributo "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Atributos ] Insert  SQLException: sp_insert_atributo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Jefatura(Obj_Jefatura jefat){
		String query = "exec sp_insert_jefatura	?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, jefat.getDescripcion().toUpperCase());
			pstmt.setString(2, (jefat.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Jefatura ] Insert  SQLException: sp_insert_jefatura "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Jefatura ] Insert  SQLException: sp_insert_jefatura "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Jefatura ] Insert  SQLException: sp_insert_jefatura "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}

	public boolean Guardar_Eq_Trabajo(Obj_Equipo_De_Trabajo EqTabajo){
		String query = "exec sp_insert_equipo_trabajo ?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, EqTabajo.getDescripcion().toUpperCase());
			pstmt.setString(2, (EqTabajo.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Eq_Trabajo ] Insert  SQLException: sp_insert_equipo_trabajo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Eq_Trabajo ] Insert  SQLException: sp_insert_equipo_trabajo "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Eq_Trabajo ] Insert  SQLException: sp_insert_equipo_trabajo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Ponderacion(Obj_Ponderacion pond){
		String query = "exec sp_insert_ponderacion  ?,?,?,?,?, ?,?,?,?,?, ?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pond.getDescripcion().toUpperCase());
			pstmt.setFloat (2, pond.getValor());
			pstmt.setString(3, pond.getFechaIn());
			pstmt.setString(4, pond.getFechaFin());
			pstmt.setString(5, pond.isDomingo()?"1":"0");
			pstmt.setString(6, pond.isLunes()?"1":"0");
			pstmt.setString(7, pond.isMartes()?"1":"0");
			pstmt.setString(8, pond.isMiercoles()?"1":"0");
			pstmt.setString(9,pond.isJueves()?"1":"0");
			pstmt.setString(10,pond.isViernes()?"1":"0");
			pstmt.setString(11,pond.isSabado()?"1":"0");
			pstmt.setString(12, pond.getStatus()?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ponderacion ] Insert  SQLException: sp_insert_ponderacion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ponderacion ] Insert  SQLException: sp_insert_ponderacion "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ponderacion ] Insert  SQLException: sp_insert_ponderacion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Nivel(Obj_Nivel_Critico nc){
		String query = "exec sp_insert_nivel_critico		 ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, nc.getDescripcion().toUpperCase());
			pstmt.setFloat(2, nc.getValor());
			pstmt.setString(3, (nc.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nivel ] Insert  SQLException: sp_insert_nivel_critico "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nivel ] Insert  SQLException: sp_insert_nivel_critico "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nivel ] Insert  SQLException: sp_insert_nivel_critico "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Relacion_Actividades(Obj_Actividades_Relacionadas relacion){
		String query = "exec sp_insert_relacion_actividad ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt	(1, relacion.getFolio());
			pstmt.setString	(2, relacion.getProyecto().toUpperCase().trim());
			pstmt.setString	(3, relacion.getDescripcion().toUpperCase().trim());
			pstmt.setString	(4, relacion.getNivel_critico().trim());
			pstmt.setInt	(5, relacion.getStatus());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Actividades ] Insert  SQLException: sp_insert_relacion_actividad "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Actividades ] Insert  SQLException: sp_insert_relacion_actividad "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Actividades ] Insert  SQLException: sp_insert_relacion_actividad "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Relacion_Tabla(Obj_Actividades_Relacionadas relacion, String[][] tabla){
		String querytabla = "exec sp_insert_tabla_relacion_actividad ?,?,?,?,?";
				
		Connection con = new Connexion().conexion();
		PreparedStatement pstmtTabla = null;
		try {
			con.setAutoCommit(false);
			
			pstmtTabla = con.prepareStatement(querytabla);
				
			for(int i=0; i<tabla.length; i++){
				
				pstmtTabla.setInt(1, relacion.getFolio());
				pstmtTabla.setInt(2, Integer.parseInt(tabla[i][0].toString().trim()));
				pstmtTabla.setString(3, tabla[i][3].toString().trim().toUpperCase());
				pstmtTabla.setString(4, tabla[i][4].toString().trim());
				pstmtTabla.setInt(5, Boolean.parseBoolean(tabla[i][2]) ? 1 : 0);
				
				pstmtTabla.executeUpdate();
			}
			
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada Guardar_Proyecto_Tabla");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Tabla ] Insert  SQLException: sp_insert_tabla_relacion_actividad "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Tabla ] Insert  SQLException: sp_insert_tabla_relacion_actividad "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Tabla ] Insert  SQLException: sp_insert_tabla_relacion_actividad "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Proyecto(Obj_Actividades_Por_Proyecto proyec){
		String query = "exec sp_insert_proyecto ?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt	(1, proyec.getFolio());
			pstmt.setString	(2, proyec.getProyecto().toUpperCase().trim());
			pstmt.setString	(3, proyec.getDescripcion().toUpperCase().trim());
			pstmt.setString	(4, proyec.getNivel_critico().trim());
			pstmt.setInt	(5, proyec.getStatus());
			pstmt.setString	(6, proyec.getFecha_inicial());
			pstmt.setString	(7, proyec.getFecha_final());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto ] Insert  SQLException: sp_insert_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto ] Insert  SQLException: sp_insert_proyecto "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto ] Insert  SQLException: sp_insert_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Proyecto_Tabla(Obj_Actividades_Por_Proyecto proyect, String[][] tabla){
		String querytabla = "exec sp_insert_tabla_proyecto ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmtTabla = null;
		try {
			con.setAutoCommit(false);
			pstmtTabla = con.prepareStatement(querytabla);
			for(int i=0; i<tabla.length; i++){
				pstmtTabla.setInt(1, proyect.getFolio());
				pstmtTabla.setInt(2, Integer.parseInt(tabla[i][0].toString().trim()));
				pstmtTabla.setString(3, tabla[i][3].toString().trim().toUpperCase());
				pstmtTabla.setString(4, tabla[i][4].toString().trim());
				pstmtTabla.setInt(5, Boolean.parseBoolean(tabla[i][2]) ? 1 : 0);
				pstmtTabla.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto_Tabla ] Insert  SQLException: sp_insert_tabla_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada Guardar_Proyecto_Tabla");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto_Tabla ] Insert  SQLException: sp_insert_tabla_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto_Tabla ] Insert  SQLException: sp_insert_tabla_proyecto "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto_Tabla ] Insert  SQLException: sp_insert_tabla_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Cuadrante(Obj_Cuadrante cuadrante){
		String query = "exec sp_insert_cuadrante ?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, cuadrante.getCuadrante().toUpperCase());
			pstmt.setString(2, cuadrante.getPerfil().toUpperCase());
			pstmt.setString(3, cuadrante.getJefatura());
			pstmt.setString(4, cuadrante.getNivel_gerarquico());
			pstmt.setString(5, cuadrante.getEquipo_trabajo());
			pstmt.setString(6, cuadrante.getEstablecimiento());
			pstmt.setInt(7, cuadrante.getDomingo());
			pstmt.setInt(8, cuadrante.getLunes());
			pstmt.setInt(9, cuadrante.getMartes());
			pstmt.setInt(10, cuadrante.getMiercoles());
			pstmt.setInt(11, cuadrante.getJueves());
			pstmt.setInt(12, cuadrante.getViernes());
			pstmt.setInt(13, cuadrante.getSabado());
			pstmt.setInt(14, cuadrante.getStatus());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante ] Insert  SQLException: sp_insert_cuadrante "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante ] Insert  SQLException: sp_insert_cuadrante "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante ] Insert  SQLException: sp_insert_cuadrante "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Cuadrante_Tabla(Obj_Cuadrante cuadrante, String[][] tabla){
		String querytabla = "exec sp_insert_tabla_cuadrante ?,?,?,?,?,?,?,?";
				
		Connection con = new Connexion().conexion();
		PreparedStatement pstmtTabla = null;
		try {
			con.setAutoCommit(false);
			
			pstmtTabla = con.prepareStatement(querytabla);
				
			for(int i=0; i<tabla.length; i++){
				
				pstmtTabla.setString(1, cuadrante.getCuadrante().toUpperCase());
				pstmtTabla.setInt(2, Integer.parseInt(tabla[i][0].toString().trim()));
				pstmtTabla.setString(3, tabla[i][1].toString().trim().toUpperCase());
				pstmtTabla.setString(4, tabla[i][2].toString().trim());
				pstmtTabla.setInt(5, Boolean.parseBoolean(tabla[i][3]) ? 1 : 0);
				pstmtTabla.setString(6, tabla[i][4]);
				pstmtTabla.setString(7, tabla[i][5]);
				pstmtTabla.setString(8, tabla[i][6]);
				pstmtTabla.executeUpdate();
			}
			
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante_Tabla ] Insert  SQLException: sp_insert_tabla_cuadrante "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada Guardar_Cuadrante_Tabla");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante_Tabla ] Insert  SQLException: sp_insert_tabla_cuadrante "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante_Tabla ] Insert  SQLException: sp_insert_tabla_cuadrante "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante_Tabla ] Insert  SQLException: sp_insert_tabla_cuadrante "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		}		
		return true;
	}
	
	public boolean opcion_respuesta(Obj_Opciones_De_Respuestas respuesta){
		String query = "exec sp_insert_opciones_respuesta	?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, respuesta.getNombre().toUpperCase().trim());
			pstmt.setInt(2, respuesta.getTipo_opcion().equals("Opci�n Libre") ? 0 : 1);
			pstmt.setInt(3, respuesta.isStatus() ? 1 : 0);
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Tipo_Banco(Obj_Tipo_De_Bancos banck){
		String query = "exec sp_insert_tipo_banco ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, banck.getBanco().toUpperCase());
			pstmt.setString(2, banck.getAbreviatura().toUpperCase());
			pstmt.setString(3, (banck.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Divisas(Obj_Divisas_Y_Tipo_De_Cambio divisas){
		String query = "insert into tb_divisas_tipo_de_cambio(nombre_divisas,valor,status) values(?,?,?)";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, divisas.getNombre().toUpperCase());
			pstmt.setFloat (2, divisas.getValor());
			pstmt.setString(3, (divisas.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Denominaciones(Obj_Denominaciones denominaciones){
		String query = "exec sp_insert_alta_denominacion ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, denominaciones.getDenominacion().toUpperCase());
			pstmt.setFloat(2, denominaciones.getValor_denominacion());
			pstmt.setString(3, denominaciones.getMoneda());
			pstmt.setInt(4, (denominaciones.isStatus())?1:0);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Corte(Obj_Alimentacion_Cortes corte){
		String query = "exec sp_insert_corte_caja ?,?,?,?,?,?,?,?,?,?,?,?,?,?";
				
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, corte.getFolio_corte().trim());
			pstmt.setInt(2, corte.getFolio_empleado());
			pstmt.setString(3,corte.getPuesto().toUpperCase());
			pstmt.setString(4, corte.getEstablecimiento().toUpperCase());
			pstmt.setString(5, corte.getAsignacion().toUpperCase());
			pstmt.setFloat(6, corte.getCorte_sistema());
			pstmt.setFloat(7, corte.getDeposito());
			pstmt.setFloat(8, corte.getEfectivo());
			pstmt.setFloat(9, corte.getDiferencia_corte());
			pstmt.setString(10, corte.getFecha().toUpperCase());
			pstmt.setInt(11, (corte.isStatus())?1:0);
			pstmt.setString(12, corte.getComentario().toUpperCase());
			pstmt.setFloat(13, corte.getTiempo_aire());
			pstmt.setFloat(14, corte.getRecibo_luz());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Bono(Obj_Bono_Complemento_Sueldo bono){
		String query = "exec sp_insert_bono ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setFloat(1, bono.getBono());
			pstmt.setString(2, bono.getAbreviatura().toUpperCase());
			pstmt.setString(3, (bono.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Sueldo(Obj_Sueldos sueldo){
		String query = "exec sp_insert_sueldo ?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setFloat(1, sueldo.getSueldo());
			pstmt.setString(2, (sueldo.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacc�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}

	public boolean Guardar_Usuario(Obj_Usuario usuario){
		String query = "exec sp_insert_usuario ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, usuario.getNombre_completo().toUpperCase());
			pstmt.setString(2, usuario.getContrasena());
			pstmt.setInt(3, usuario.getPermiso_id());
			String fecha = new Date().toString();
			pstmt.setString(4, fecha);
			pstmt.setInt(5, usuario.getStatus());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+ e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	
	@SuppressWarnings("rawtypes")
	public boolean Guardar_Usuario(Obj_Usuario usuario,Vector permisos){
		String query = " exec sp_insert_permisos_submenus_usuario_nuevo ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			for(int i=0; i<permisos.size(); i++){
				pstmt.setInt(1, usuario.getFolio());
				String[] arreglo = permisos.get(i).toString().split("/");
				pstmt.setString(2, arreglo[0]);
				pstmt.setString(3, arreglo[1]);
				pstmt.execute();
//				System.out.println(usuario.getFolio());
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+ e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public int getMenuId(String Nombre){
		int filas=0;
		try {
			String query = "select menu_id from tb_submenus where nombre ='"+Nombre+"'";
			Statement s = new Connexion().conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next()){
				filas = rs.getInt(1);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	public boolean Guardar_fuente_sodas_rh(Obj_Fue_Sodas_DH fuentesodasrh){
		String query = "exec sp_insert_fuent_soda_rh ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "0");
			pstmt.setInt(2, fuentesodasrh.getFolio());
			pstmt.setString(3, fuentesodasrh.getNombre_Completo().toUpperCase());
			pstmt.setDouble(4, fuentesodasrh.getCantidad());
			pstmt.setString(5, fuentesodasrh.getFecha());
			pstmt.setString(6, "1");
		 	pstmt.executeUpdate();
		 	con.commit();
		 	
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_fuente_sodas_auxf(Obj_Fue_Sodas_AUXF fuentesodasauxf){
		String query = "exec sp_insert_fuent_soda_auxf ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "0");
			pstmt.setInt(2,fuentesodasauxf.getFolio());
			pstmt.setString(3, fuentesodasauxf.getNombre_Completo().toUpperCase());
			pstmt.setDouble(4, fuentesodasauxf.getCantidad());
			pstmt.setString(5, fuentesodasauxf.getFecha());
			pstmt.setString(6, "1");
		 	pstmt.executeUpdate();
		 	con.commit();
		 	
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_prestamo(Obj_Prestamos pres){
		String query = "exec sp_insert_prestamo ?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,pres.getFolio_empleado());
			pstmt.setString(2, pres.getNombre_Completo().toUpperCase());
			pstmt.setString(3, pres.getFecha());
			pstmt.setDouble(4, pres.getCantidad());
			pstmt.setDouble(5, pres.getDescuento());
			
			pstmt.setString(6, "1");
			pstmt.setDouble(7, pres.getSaldo());
			pstmt.setString(8, "1");
			pstmt.setDouble(9, 0);
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Rango_Prestamos(Obj_Rango_De_Prestamos rango_prestamo){
		String query = "exec sp_insert_rango_prestamo ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setDouble(1, rango_prestamo.getPrestamo_minimo());
			pstmt.setDouble(2, rango_prestamo.getPrestamo_maximo());
			pstmt.setDouble(3, rango_prestamo.getDescuento());
			pstmt.setString(4, (rango_prestamo.isStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();	
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Asistencia_Puntualidad(Obj_Asistencia_Y_Puntualidad asistencia_puntualidad){
		String query = "exec sp_insert_asistencia_puntualidad ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setFloat(1, asistencia_puntualidad.getValorAsistencia());
			pstmt.setFloat(2, asistencia_puntualidad.getValorPuntualidad());
			pstmt.setFloat(3, asistencia_puntualidad.getValorGafete());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortado");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Alimentacion_denominacio(Obj_Alimentacion_Denominacion alim_denom,Object[][] tabla){
		
		String query ="exec sp_insert_denominaciones ?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(query);

			con.setAutoCommit(false);
			
			for(int i=0; i<tabla.length; i++){
				
				pstmt.setString(1, alim_denom.getAsignacion().toUpperCase());
				pstmt.setString(2, alim_denom.getEmpleado().toUpperCase().trim());
				pstmt.setString(3, alim_denom.getFecha());
				pstmt.setString(4, alim_denom.getEstablecimiento().toUpperCase());
				
				pstmt.setInt(5, Integer.parseInt(tabla[i][0].toString().trim()));
				pstmt.setFloat(6, Float.parseFloat(tabla[i][2].toString().trim()));
				pstmt.setFloat(7,Float.parseFloat(tabla[i][3].toString().trim()));
				pstmt.setFloat(8,Float.parseFloat(tabla[i][4].toString().trim()));
				
				pstmt.executeUpdate();
			}
					
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Alimentacion_deposito(Obj_Alimentacion_Denominacion alim_denom,Object[][] tabla){
		
		String query ="exec sp_insert_deposito ?,?,?,?";
		Connection con = new Connexion().conexion();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(query);

			con.setAutoCommit(false);
			
			for(int i=0; i<tabla.length; i++){
				
				pstmt.setString(1, alim_denom.getEstablecimiento().toUpperCase().trim());
				pstmt.setString(2, alim_denom.getEmpleado().toUpperCase().trim());
				
				pstmt.setString(3, tabla[i][0].toString().trim());
				pstmt.setFloat (4, Float.parseFloat(tabla[i][1].toString().trim()));
				
				pstmt.execute();
			}
					
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar(Obj_Diferencia_De_Cortes pres){
		String query = "exec sp_insert_diferencia_cortes ?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,pres.getFolio_empleado());
			pstmt.setString(2, pres.getNombre_Completo().toUpperCase());
			pstmt.setString(3, pres.getFecha());
			pstmt.setDouble(4, pres.getCantidad());
			pstmt.setDouble(5, pres.getDescuento());
			pstmt.setString(6, "1");
			pstmt.setDouble(7, pres.getSaldo());
			pstmt.setString(8, "1");
			pstmt.setDouble(9, 0);
		 	pstmt.executeUpdate();
		 	con.commit();
		 } catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Departamento(Obj_Departamento departamento){
		String query = "exec sp_insert_departamento ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, departamento.getFolio());
			pstmt.setString(2, departamento.getDepartamento().toUpperCase().trim());
			pstmt.setString(3, departamento.getAbreviatura().toUpperCase().trim());
			pstmt.setString(4, (departamento.isStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_ConfigBD(Obj_Configuracion_Base_de_Datos config){
		BufferedWriter bufferedWriter = null;
		String nomArchivo = System.getProperty("user.dir")+"\\Config\\config";
		try{
			File archivo = new File(nomArchivo);
			if(archivo.exists()){
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
				
				bufferedWriter.write(config.getDireccionIPV4()+    		"\n");
				bufferedWriter.write(config.getNombreBD()+      		"\n");
				bufferedWriter.write(config.getUsuario()+ 	    		"\n");
				bufferedWriter.write(config.getContrasena()+       		"\n");
				
			}else{
				File folder = new File(System.getProperty("user.dir")+"\\Config");
				folder.mkdirs();
				archivo.createNewFile();
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
				
				bufferedWriter.write(config.getDireccionIPV4()+    		"\n");
				bufferedWriter.write(config.getNombreBD()+      		"\n");
				bufferedWriter.write(config.getUsuario()+ 	    		"\n");
				bufferedWriter.write(config.getContrasena()+       		"\n");
				
			}
			
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}finally
		{
			try
			{
				if(bufferedWriter!=null)
				{
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			}catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}return true;
	}
	
	public boolean Guardar(Obj_Configuracion_Del_Sistema configs){
		String query = "exec sp_config_sistema ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, (configs.isBono_10_12())? "true" : "false");
			pstmt.setString(2, (configs.isBono_dia_extra())? "true" : "false");
			pstmt.setString(3, (configs.isGuardar_horario())? "true" : "false");
			pstmt.setString(4, (configs.isGuardar_departamento())? "true" : "false");
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean lista_Imprimir(){
		
		String query ="exec sp_imprimir_lista_raya";

		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);

			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	

	public int getFolio_prestamo(int folio){
		int valor = 0;
	try {
			Connexion con = new Connexion();
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery("select folio from tb_prestamo where folio_empleado = "+folio+" and status = 1");
			while(rs.next()){
				valor = rs.getInt(1);			
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return valor;
	}
		
	public String[] getFinalCorte(int folio){
		String[] valor = new String[3];
		valor[0] = "";	valor[1] = "";	valor[2] = "";
	try {
		Connexion con = new Connexion();
		Statement s = con.conexion().createStatement();
		ResultSet rs = s.executeQuery("exec sp_get_parametros_cortes "+folio);
			while(rs.next()){
				valor[0] = rs.getString(1);
				valor[1] = rs.getString(2);
				valor[2] = rs.getString(3);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return valor;
	}
	
	public boolean GuardarImportarVoucher(Obj_Importar_Voucher importar){
		String query = "exec sp_insert_voucher_importado ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, importar.getContrato());
			pstmt.setString(2, importar.getF_transaccion());
			pstmt.setString(3, importar.getH_transaccion());
			pstmt.setString(4, importar.getNo_codigo());
			pstmt.setString(5, importar.getLeyenda());
			pstmt.setFloat(6, importar.getImporte());
			pstmt.setString(7, importar.getTerminal());
			pstmt.setString(8, importar.getCuenta());
			pstmt.setString(9, importar.getAutorizacion());
			pstmt.setString(10, importar.getTipo_de_cuenta());
			pstmt.setString(11, importar.getF_abono());
			pstmt.setString(12, importar.getReferencia_1());
			pstmt.setString(13, importar.getReferencia_2());
			pstmt.setString(14, importar.getReferencia_3());
			pstmt.setFloat(15, importar.getQ6());
			pstmt.setFloat(16, importar.getImporta_cash_back());
			pstmt.setFloat(17, importar.getEci());
			pstmt.setString(18, importar.getControl_interno_comercio());
			pstmt.setString(19, importar.getLote1());
			pstmt.setString(20, importar.getLote2());
			
			pstmt.executeUpdate();
			System.out.println("Guardo");
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en Guardar SQL en la funcion Importar GuardarImportarVoucher \n en el procedimiento almacenado sp_insert_voucher_importado " + e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Guardar SQL en la funcion Importar GuardarImportarVoucher \n en el procedimiento almacenado sp_insert_voucher_importado " + ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean DeleteBancos(){
		String query = "exec sp_borrado_empleados_dif_1";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar(Obj_Nomina nomina){
		String query = "exec sp_insert_nomina ?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nomina.getNumero_listaraya());
			pstmt.setString(2,nomina.getEstablecimiento());
			pstmt.setString(3,nomina.getNomina());
			pstmt.setString(4,nomina.getPago_linea());
			pstmt.setString(5,nomina.getCheque_nomina());
			pstmt.setString(6,nomina.getLista_raya());
			pstmt.setString(7,nomina.getDiferencia());
			pstmt.setString(8,nomina.getFecha());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean TemporadaGuardar(Obj_Temporada temporada){
		String query = "exec sp_insert_temporada ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
					
			pstmt.setString(1, temporada.getDescripcion());
			pstmt.setString(2, temporada.getFecha_in());
			pstmt.setString(3, temporada.getFecha_fin());
			pstmt.setString(4, temporada.getDia());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	/*****agregando funcion para guardar numeros telefonicos*****/
	public boolean Guardar_Telefono(Obj_Directorios directorio){
		String query = "exec sp_insert_tb_direccion_telefonicos ?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1,directorio.getFolio_empleado());
			pstmt.setString(2, directorio.getNombre());
			pstmt.setString(3, directorio.getTelefono());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacc�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}

	
	//guardar nivel gerarquico
	public boolean Guardar_Nivel_G(Obj_Nivel_Jerarquico pond){
		String query = "exec sp_insert_tb_nivel_gerarquico  ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString (1, pond.getDescripcion());
			pstmt.setString (2, pond.getPuesto_principal());
			pstmt.setString (3, pond.getPuesto_dependiente());
			pstmt.setString (4, pond.isStatus()?"1":"0");
			
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	
	public boolean Guardar_Actividad(Obj_Actividad actividad){
		String query = "exec sp_insert_actividad ?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
					
			pstmt.setString(1, actividad.getActividad().toUpperCase());
			pstmt.setString(2, actividad.getDescripcion().toUpperCase());
			pstmt.setString(3, actividad.getRespuesta());
			pstmt.setString(4, actividad.getAtributos());
			pstmt.setString(5, actividad.getNivel_critico());
			pstmt.setString(6, actividad.getTemporada());
			pstmt.setInt(7, actividad.isCarga() ? 1 : 0);
			pstmt.setInt(8, actividad.getRepetir());
			
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Actividad_Nivel_Jerarquico(Obj_Actividad_Asignadas_Nivel_Jerarquico actividad, String nombre){
		String query = "exec sp_insert_actividad_nivel_jerarquico ?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
					
			pstmt.setString(1, actividad.getActividad().toUpperCase());
			pstmt.setString(2, actividad.getDescripcion().toUpperCase());
			pstmt.setString(3, actividad.getRespuesta());
			pstmt.setString(4, actividad.getAtributos());
			pstmt.setString(5, actividad.getNivel_critico());
			pstmt.setString(6, actividad.getTemporada());
			pstmt.setInt(7, actividad.isCarga() ? 1 : 0);
			pstmt.setInt(8, actividad.getRepetir());
			pstmt.setString(9, nombre);
			
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Mensaje_Personal(Obj_Mensaje_Personal MsjPersonal){
		String query = "exec sp_insert_mensaje ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString (1, MsjPersonal.getFechaInicial().trim());
			pstmt.setString (2, MsjPersonal.getFechaFin().trim());
			pstmt.setString(3, MsjPersonal.getAsunto().toUpperCase().trim());
			pstmt.setString(4, MsjPersonal.getMensaje().toUpperCase().trim());
			pstmt.setBoolean(5, (MsjPersonal.getStatus())? true: false);
			
			pstmt.executeUpdate();
		
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Permiso_Checador(Obj_Alimentacion_De_Permisos_A_Empleados Permiso, int tiene_dia_dobla){
		String query = "exec sp_insert_permiso_checador ?,?,?,?,?,?,?,?,?,"+tiene_dia_dobla;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt (1, Permiso.getFolio_empleado());
			pstmt.setInt(2, Permiso.getFolio_usuario());
			pstmt.setString(3,Permiso.getFecha());
			pstmt.setInt(4, Permiso.getTipo_de_permiso());
			pstmt.setString(5, Permiso.getMotivo().toUpperCase().trim());
			pstmt.setInt(6, (Permiso.isStatus())? 1: 0);
			pstmt.setInt(7,Permiso.getDescanso());
			pstmt.setString(8,Permiso.getTiempo_comida());
			pstmt.setInt(9, Permiso.getFolio_empleado_optener_turno());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Permiso_Checador ]   SQLException: sp_insert_permiso_checador "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Permiso_Checador ]   SQLException: sp_insert_permiso_checador "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Permiso_Checador ]   SQLException: sp_insert_permiso_checador "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Permiso_Checador ]   SQLException: sp_insert_permiso_checador "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Tabla_Nivel2(Obj_Nivel_Jerarquico pond,String[][] tabla){
		String query = "exec sp_insert_nivel_jerarquico ?,?";
		String querytabla="exec sp_insert_tabla_nivel_jerarquico ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtabla =null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmtabla=con.prepareStatement(querytabla);
			
			
			pstmt.setString (1, pond.getDescripcion());
			pstmt.setString (2, pond.getPuesto_principal());
			
			for (int i = 0; i < tabla.length; i++) {

				pstmtabla.setInt (1, pond.getFolio());
				pstmtabla.setString (2, tabla[i][0]);
				pstmtabla.setString (3, tabla[i][1]);
				
				pstmtabla.executeUpdate();
				
			}
			pstmt.executeUpdate();
		
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Empleado_Msj(Obj_Mensaje_Personal Empleado_Msj,String[] tabla){
//		String query = "exec sp_insert_nivel_jerarquico ?,?";
		String querytabla="exec sp_insert_tabla_empleado_mensaje ?,?";
		Connection con = new Connexion().conexion();
//		PreparedStatement pstmt = null;
		PreparedStatement pstmtabla =null;
		try {
			
			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(query);
			pstmtabla=con.prepareStatement(querytabla);
			
			
			for (int i = 0; i < tabla.length; i++) {

				pstmtabla.setInt (1, Empleado_Msj.getFolioMensaje());
				pstmtabla.setString (2, tabla[i]);
				
				pstmtabla.executeUpdate();
				
			}
//			pstmt.executeUpdate();
		
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean EmpleadoCuadrante(Obj_Empleados_En_Cuadrantes empleado_cuadrante){
		String query = "exec sp_insert_empleado_cuadrante ?,?";
				
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, empleado_cuadrante.getCuadrante());
			pstmt.setInt(2, empleado_cuadrante.isStatus() ? 1 : 0);

			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean EmpleadoCuadranteTabla(Obj_Empleados_En_Cuadrantes empleado_cuadrante, String[] lista){
		String querytabla = "exec sp_insert_tabla_empleado_cuadrante ?,?";
				
		Connection con = new Connexion().conexion();
		PreparedStatement pstmtTabla = null;
		try {
			con.setAutoCommit(false);
			pstmtTabla = con.prepareStatement(querytabla);
			
			for(int i=0; i<lista.length; i++){
				pstmtTabla.setString(1, empleado_cuadrante.getCuadrante().toUpperCase().trim());
				pstmtTabla.setInt(2, Integer.parseInt(lista[i]));
				pstmtTabla.execute();
			}
						
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Sesion(Obj_Usuario usuario){
		BufferedWriter bufferedWriter = null;
		String nomArchivo = System.getProperty("user.dir")+"\\Config\\users";
		try{
			File archivo = new File(nomArchivo);
			if(archivo.exists()){
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
							
				bufferedWriter.write(usuario.getFolio()+    		"\n");
				bufferedWriter.write(usuario.getNombre_completo()+	"\n");
				
			}else{
				File folder = new File(System.getProperty("user.dir")+"\\Config");
				folder.mkdirs();
				archivo.createNewFile();
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
				
				bufferedWriter.write(usuario.getFolio()+    		"\n");
				bufferedWriter.write(usuario.getNombre_completo()+	"\n");
				
			}
			
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}finally
		{
			try
			{
				if(bufferedWriter!=null)
				{
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			}catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}return true;
	}
	
	public boolean guardarAlimentacionCuadrante(Obj_Captura_Del_Cuadrante_Personal datos_cuadrante){
		String query = "exec sp_insert_alimentacion_cuadrante ?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
					
			pstmt.setString(1, datos_cuadrante.getNombre());
			pstmt.setString(2, datos_cuadrante.getPuesto());
			pstmt.setString(3, datos_cuadrante.getEstablecimiento());
			pstmt.setString(4, datos_cuadrante.getEquipo_trabajo());
			pstmt.setString(5, datos_cuadrante.getJefatura());
			pstmt.setString(6, datos_cuadrante.getFecha());
			pstmt.setString(7, datos_cuadrante.getDia());
			pstmt.setString(8, datos_cuadrante.getCuadrante());	
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}

	public boolean Guardar_Mensajes(Obj_Mensajes mensaje){
			
			String query = "insert into tb_mensajes(mensaje)values(?)";
			
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, mensaje.getMensaje().toUpperCase());
				pstmt.executeUpdate();
				con.commit();
			} catch (Exception e) {
				System.out.println("SQLException: "+e.getMessage());
				if(con != null){
					try{
						System.out.println("La transacci�n ha sido abortada");
						con.rollback();
					}catch(SQLException ex){
						System.out.println(ex.getMessage());
					}
				}
				return false;
			}finally{
				try {
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
				}
			}		
			return true;
		}
	
		
	public boolean Insert_Empleado(int folio,String t_entrada){
		
		String insert ="exec sp_insert_entosal "+folio+",?,?,?;";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		try{
			String pc_nombre = InetAddress.getLocalHost().getHostName();
			String pc_ip = InetAddress.getLocalHost().getHostAddress();
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insert);
			
			
			pstmt.setString(1, pc_nombre);
			pstmt.setString(2, pc_ip);
			pstmt.setString(3, t_entrada);
			
			pstmt.executeUpdate();
			con.commit();
			
		}catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
		}
		return true;
	}
	
	public boolean Insert_Empleado_Comida(int folio,String t_entrada){
		
		String insert ="exec sp_insert_entosal "+folio+",?,?,?,?;";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		try{
			String pc_nombre = InetAddress.getLocalHost().getHostName();
			String pc_ip = InetAddress.getLocalHost().getHostAddress();
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insert);
			
			
			pstmt.setString(1, pc_nombre);
			pstmt.setString(2, pc_ip);
			pstmt.setString(3, t_entrada);
			
			pstmt.executeUpdate();
			con.commit();
			
		}catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
		}
		return true;
	}
	
//	public boolean Guardar_Checador(Obj_Checador chec){
//		String query = "exec sp_insert_checador ?,?,?,?,?,?";
//		Connection con = new Connexion().conexion();
//		PreparedStatement pstmt = null;
//		try {
//			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1,chec.getFolio_emp());
//			pstmt.setInt(2, chec.getClave());
//			pstmt.setString(3, chec.getEntrada());
//			pstmt.setString(4, chec.getSalida());
//			pstmt.setString(5, chec.getEntrada2());
//			pstmt.setString(6, chec.getSalida2());
//		 	pstmt.executeUpdate();
//		 	con.commit();
//		 	
//		} catch (Exception e) {
//			System.out.println("SQLException: "+e.getMessage());
//			if(con != null){
//				try{
//					System.out.println("La transacci�n ha sido abortada");
//					con.rollback();
//				}catch(SQLException ex){
//					System.out.println(ex.getMessage());
//				}
//			}
//			return false;
//		}finally{
//			try {
//				con.close();
//			} catch(SQLException e){
//				e.printStackTrace();
//			}
//		}		
//		return true;
//	}
	
public boolean Guardar_Asignacion_mensajes(Obj_Asignacion_Mensajes mensj){
		
		String query ="insert into tb_asignacion_mensaje(mensaje,mensajearea,puesto,equipo,jefatura,empleado,rbpuesto,rbequipo,rbjefatura,rbempleado)values(?,?,?,?,?,?,?,?,?,?)";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, mensj.getNo_mensajes());
			pstmt.setString(2, mensj.getMensaje());
			pstmt.setString(3,mensj.getPuesto() );
			pstmt.setString(4,mensj.getEquipo() );
			pstmt.setString(5,mensj.getJefatura());
			pstmt.setString(6,mensj.getEmpleado());
			
			pstmt.setBoolean(7, mensj.getRbpuesto());
			pstmt.setBoolean(8, mensj.getRbequipo());
			pstmt.setBoolean(9, mensj.getRbjefatura());
			pstmt.setBoolean(10, mensj.getRbempleado());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}

//Guardamos el horario
public boolean Guardar_Horario(Obj_Horarios horario){
	String query = "exec sp_insert_horarios ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query);
		int i=1;
		pstmt.setString(i, horario.getNombre().trim());
		
		pstmt.setString(i+=1, "Domingo");
		pstmt.setString(i+=1, horario.getDomingo1());
		pstmt.setString(i+=1, horario.getDomingo2());
		pstmt.setString(i+=1, horario.getDomingo3());
		pstmt.setString(i+=1, horario.getDomingo4());
		pstmt.setString(i+=1, horario.getDomingo5());
		
		pstmt.setString(i+=1, "Lunes");
		pstmt.setString(i+=1, horario.getLunes1());
		pstmt.setString(i+=1, horario.getLunes2());
		pstmt.setString(i+=1, horario.getLunes3());
		pstmt.setString(i+=1, horario.getLunes4());
		pstmt.setString(i+=1, horario.getLunes5());
		
		pstmt.setString(i+=1, "Martes");
		pstmt.setString(i+=1, horario.getMartes1());
		pstmt.setString(i+=1, horario.getMartes2());
		pstmt.setString(i+=1, horario.getMartes3());
		pstmt.setString(i+=1, horario.getMartes4());
		pstmt.setString(i+=1, horario.getMartes5());
		
		pstmt.setString(i+=1, "Mi�rcoles");
		pstmt.setString(i+=1, horario.getMiercoles1());
		pstmt.setString(i+=1, horario.getMiercoles2());
		pstmt.setString(i+=1, horario.getMiercoles3());
		pstmt.setString(i+=1, horario.getMiercoles4());
		pstmt.setString(i+=1, horario.getMiercoles5());
		
		pstmt.setString(i+=1, "Jueves");
		pstmt.setString(i+=1, horario.getJueves1());
		pstmt.setString(i+=1, horario.getJueves2());
		pstmt.setString(i+=1, horario.getJueves3());
		pstmt.setString(i+=1, horario.getJueves4());
		pstmt.setString(i+=1, horario.getJueves5());
		
		pstmt.setString(i+=1, "Viernes");
		pstmt.setString(i+=1, horario.getViernes1());
		pstmt.setString(i+=1, horario.getViernes2());
		pstmt.setString(i+=1, horario.getViernes3());
		pstmt.setString(i+=1, horario.getViernes4());
		pstmt.setString(i+=1, horario.getViernes5());
		
		pstmt.setString(i+=1, "S�bado");
		pstmt.setString(i+=1, horario.getSabado1());
		pstmt.setString(i+=1, horario.getSabado2());
		pstmt.setString(i+=1, horario.getSabado3());
		pstmt.setString(i+=1, horario.getSabado4());
		pstmt.setString(i+=1, horario.getSabado5());
		
		pstmt.setInt(i+=1, horario.getDescanso());
		pstmt.setInt(i+=1, horario.getDiaDobla());
		pstmt.setInt(i+=1, horario.getDiaDobla2());
		pstmt.setInt(i+=1, horario.getDiaDobla3());
		pstmt.setInt(i+=1, horario.getRecesoDiarioExtra());
		pstmt.setInt(i+=1, horario.getHorarioDeposito());
		
		
		pstmt.executeUpdate();
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage());
		if (con != null){
			try {
				System.out.println("La transacci�n ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
			}
		} 
		return false;
	}finally{
		try {
			pstmt.close();
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}		
	return true;
}

	public boolean Insert_Empleado_Comida(int folio, String t_entrada, int tipo_salida_comer) {
		String insert ="exec sp_insert_entosal "+folio+",?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		try{
			String pc_nombre = InetAddress.getLocalHost().getHostName();
			String pc_ip = InetAddress.getLocalHost().getHostAddress();
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insert);
			
			
			pstmt.setString(1, pc_nombre);
			pstmt.setString(2, pc_ip);
			pstmt.setString(3, t_entrada);
			pstmt.setInt(4, tipo_salida_comer);
			
			pstmt.executeUpdate();
			con.commit();
			
		}catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Insert_Empleado_Comida  sp sp_insert_entosal "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Insert_Empleado_Comida  sp sp_insert_entosal "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			} 
			return false;
		}
		return true;
	}
		
		//Guardar captura fuente de sodas
		public boolean Guardar_Fuente_Sodas(Obj_Captura_Fuente_Sodas sodas){
			String query = "exec sp_insert_captura_fuente_sodas ?,?,?,?,?,?,?,?,?";
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				
				String pc_nombre =	InetAddress.getLocalHost().getHostName();
				String pc_ip = 		InetAddress.getLocalHost().getHostAddress();
				
				pstmt.setString(1, sodas.getClave().toUpperCase().trim());
				pstmt.setString(2, sodas.getEstablecimiento().toUpperCase().trim());
				pstmt.setString(3, sodas.getPuesto().toUpperCase().trim());
				pstmt.setString(4, sodas.getTicket().toUpperCase().trim());
				pstmt.setFloat(5, sodas.getImporte());
				pstmt.setString(6, sodas.getUsuario().trim());
				pstmt.setString(7, pc_nombre);
				pstmt.setString(8, pc_ip);
				pstmt.setInt(9, 1);			//status default al guardar usado para revision de auxilizr y finanazas
//				pstmt.setInt(10, 1);			//status default al guardar usado para revisicon de desarrollo humano
				
				pstmt.executeUpdate();
				con.commit();
			} catch (Exception e) {
				System.out.println("SQLException: " + e.getMessage());
				if (con != null){
					try {
						System.out.println("La transacci�n ha sido abortada");
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Guardar_Fuente_Sodas  procedimiento almacenado sp_insert_captura_fuente_sodas SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
						con.rollback();
					} catch(SQLException ex) {
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Guardar_Fuente_Sodas  procedimiento almacenado sp_insert_captura_fuente_sodas SQLException: "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				} 
				return false;
			}finally{
				try {
					pstmt.close();
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Guardar_Fuente_Sodas  procedimiento almacenado sp_insert_captura_fuente_sodas SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}		
			return true;
		}
	
		public boolean buscarBorrarPDependiente(String nombre, int folio_tabla,String establecimineto){
			String query = "exec sp_folio_puesto_dependiente '"+nombre+"', "+folio_tabla+",'"+establecimineto+"'";
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			try {
				
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				
				pstmt.executeUpdate();
			
				con.commit();
			} catch (Exception e) {
				System.out.println("SQLException: "+e.getMessage());
				if(con != null){
					try{
						System.out.println("La transacci�n ha sido abortada");
						con.rollback();
					}catch(SQLException ex){
						System.out.println(ex.getMessage());
					}
				}
				return false;
			}finally{
				try {
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
				}
			}		
			return true;
		}
		
//		borrar alimentacion por denominaciones
		public boolean Borrar_Alimentacion_Denominaciones(String folio_corte){
			String query = "exec sp_delete_folio_corte '"+folio_corte+"';";
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			
			try {

				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				pstmt.executeUpdate();
				con.commit();
				
			} catch (Exception e) {
				System.out.println("SQLException: "+e.getMessage());
				if(con != null){
					try{
						System.out.println("La transacci�n ha sido abortada");
						con.rollback();
					}catch(SQLException ex){
						System.out.println(ex.getMessage());
						}
				}
				return false;
			}finally{
				try {
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					}
			}		
			return true;
		}
		
		public boolean Guardar_Solicitud(Obj_Solicitud_De_Empleados solicitud){
			String query = "exec sp_insert_solicitud_empleado ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			try {
				
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);

				String pc_nombre = InetAddress.getLocalHost().getHostName();
				String pc_ip = InetAddress.getLocalHost().getHostAddress();
			
				int i=1;
				pstmt.setInt (i,solicitud.getFolio_empleado());
				pstmt.setString (i+=1,solicitud.getUsuario().toLowerCase().trim());
				pstmt.setInt (i+=1,solicitud.getFolio_permiso());
				pstmt.setInt (i+=1,solicitud.getFolio_solicitud());
				pstmt.setInt (i+=1,solicitud.getFolio_cambio());
				pstmt.setString (i+=1,solicitud.getCambio_a());
				pstmt.setString (i+=1,solicitud.getFecha_solicitada());
				pstmt.setInt (i+=1,solicitud.getTemp_fijo());
				pstmt.setFloat (i+=1,solicitud.getCantidad_solicitada());
				pstmt.setInt (i+=1,solicitud.getPuntualidad_y_asistencia());
				pstmt.setInt (i+=1,solicitud.getCumplimiento_de_tareas());
				pstmt.setInt (i+=1,solicitud.getDiciplina());
				pstmt.setInt (i+=1,solicitud.getRespeto_y_trato_general());
				pstmt.setString (i+=1,solicitud.getMotivo().toUpperCase());
				pstmt.setString (i+=1,pc_nombre);
				pstmt.setString (i+=1,pc_ip);
				
				pstmt.execute();
				con.commit();
			} catch (Exception e) {
				System.out.println("SQLException: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud ]   SQLException: sp_insert_solicitud_empleado "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				if (con != null){
					try {
						System.out.println("La transacci�n ha sido abortada");
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud ]   SQLException: sp_insert_solicitud_empleado "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
						con.rollback();
					} catch(SQLException ex) {
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud ]   SQLException: sp_insert_solicitud_empleado "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				} 
				return false;
			}finally{
				try {
					pstmt.close();
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud ]   SQLException: sp_insert_solicitud_empleado "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}		
			return true;
		}
		
	public boolean Guardar_Grupo_De_Vacaciones(Obj_Grupo_De_Vacaciones grupo){
		String query = "exec sp_insert_grupo_de_vacaciones ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, grupo.getFolio());
			pstmt.setString(2, grupo.getDescripcion().toUpperCase());
			pstmt.setString(3, (grupo.isStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_grupo_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_grupo_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_grupo_de_vacaciones "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_grupo_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Grupo_De_Vacaciones(Obj_Tabla_De_Vacaciones grupo_vacaciones){
//		cambiar procedimiento ( crear uno nuevo sp )
		String query = "exec sp_insert_tabla_grupos_de_vacaciones ?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);

			int i=1;
			pstmt = con.prepareStatement(query);
			pstmt.setString(i,		grupo_vacaciones.getGrupo().toUpperCase().trim());
			pstmt.setInt(i+=1, 	grupo_vacaciones.getAnios_trabajados());
			pstmt.setInt(i+=1,	grupo_vacaciones.getDias_correspondientes());
			pstmt.setInt(i+=1,	grupo_vacaciones.getPrima_vacacional());
			
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_tabla_grupos_de_vacaciones "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Remover_Grupo_De_Vacaciones(Obj_Tabla_De_Vacaciones grupo_vacaciones){

		String query = "exec sp_delete_tabla_grupos_de_vacaciones ?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);

			int i=1;
			pstmt = con.prepareStatement(query);
			pstmt.setString(i,		grupo_vacaciones.getGrupo().toUpperCase().trim());
			pstmt.setInt(i+=1, 	grupo_vacaciones.getAnios_trabajados());
			pstmt.setInt(i+=1,	grupo_vacaciones.getDias_correspondientes());
			pstmt.setInt(i+=1,	grupo_vacaciones.getPrima_vacacional());
			
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Remover_Grupo_De_Vacaciones ]   SQLException: sp_delete_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Remover_Grupo_De_Vacaciones ]   SQLException: sp_delete_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Remover_Grupo_De_Vacaciones ]   SQLException: sp_delete_tabla_grupos_de_vacaciones "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Remover_Grupo_De_Vacaciones ]   SQLException: sp_delete_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Vacaciones_Pasadas(Obj_Alimentacion_De_Vacaciones alimentacion){
		String query = "exec sp_insert_alimentacion_de_ultimas_vacaciones ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, alimentacion.getFolio_empleado());
			pstmt.setString(2, alimentacion.getFecha_inicio());
			pstmt.setString(3, alimentacion.getFecha_final());
			pstmt.setInt(4, alimentacion.getAnios_a_disfrutar());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Pasadas ]   SQLException: sp_insert_alimentacion_de_ultimas_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Pasadas ]   SQLException: sp_insert_alimentacion_de_ultimas_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Pasadas ]   SQLException: sp_insert_alimentacion_de_ultimas_vacaciones "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Vacaciones_Calculadas(Obj_Alimentacion_De_Vacaciones alimentacion){
//		cambiar procedimiento, agregar todos los campos
		String query = "exec sp_insert_alimentacion_de_vacaciones_calculadas ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
		int i=1;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt			(i, 	alimentacion.getFolio_empleado());
			pstmt.setString			(i+=1, alimentacion.getFecha_inicio());
			pstmt.setString			(i+=1, alimentacion.getFecha_final());
			pstmt.setInt			(i+=1, alimentacion.getAnios_a_disfrutar());
			pstmt.setFloat			(i+=1, alimentacion.getVacaciones());
			pstmt.setFloat			(i+=1, alimentacion.getPrima_vacacional());
			pstmt.setFloat			(i+=1, alimentacion.getInfonavit());
			pstmt.setFloat			(i+=1, alimentacion.getSueldo_semana());
			pstmt.setFloat			(i+=1, alimentacion.getCorte_de_caja());
			pstmt.setFloat			(i+=1, alimentacion.getFuente_de_sodas());
			pstmt.setFloat			(i+=1, alimentacion.getPrestamo());
			pstmt.setFloat			(i+=1, alimentacion.getPension_alimenticia());
			pstmt.setFloat          (i+=1, alimentacion.getDias_descanso_vacaciones()); 
			pstmt.setFloat			(i+=1, alimentacion.getTotal());
			pstmt.setInt			(i+=1, alimentacion.isStatus()?1:0);
			pstmt.setFloat          (i+=1, alimentacion.getVacaciones_c());
			pstmt.setFloat          (i+=1, alimentacion.getPrima_vacacional_c());
			pstmt.setFloat          (i+=1, alimentacion.getSueldo_semana_c());
			pstmt.setFloat          (i+=1, alimentacion.getGratificacion()); 
			pstmt.setInt            (i+=1, usuario.getFolio());
			
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Calculadas ]   SQLException: sp_insert_alimentacion_de_vacaciones_calculadas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Calculadas ]   SQLException: sp_insert_alimentacion_de_vacaciones_calculadas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Calculadas ]   SQLException: sp_insert_alimentacion_de_vacaciones_calculadas "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Control_Factura_xml(Obj_Proveedores fact_xml_proveedores){
//		cambiar procedimiento, agregar todos los campos
		String query = "exec sp_insert_factura_para_control_xml ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
		int i=1;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString			(i,    fact_xml_proveedores.getFolio_factura());
			pstmt.setString			(i+=1, fact_xml_proveedores.getFecha());
			pstmt.setString			(i+=1, fact_xml_proveedores.getCod_prv());
			pstmt.setString			(i+=1, fact_xml_proveedores.getProveedor());
			pstmt.setInt            (i+=1, usuario.getFolio());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Calculadas ]   SQLException: sp_insert_alimentacion_de_vacaciones_calculadas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Calculadas ]   SQLException: sp_insert_alimentacion_de_vacaciones_calculadas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Calculadas ]   SQLException: sp_insert_alimentacion_de_vacaciones_calculadas "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}

	public boolean Guardar_Asignacion_De_Cajero(Obj_Movimiento_De_Asignacion movimiento){
//		crear procedimiento almacenado con el siguiente nombre
		String query = "exec sp_insert_asignacion_de_cajeros ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			int i=1;
			
			System.out.println( movimiento.getFolio_empleado());
			System.out.println(movimiento.getAsignacion());
			System.out.println(movimiento.getEstablecimiento());
			System.out.println(movimiento.getFechaIn());
			System.out.println(movimiento.getFechaFin());
			
			pstmt.setInt(i, 	  movimiento.getFolio_empleado());
			pstmt.setString(i+=1, movimiento.getAsignacion());
			pstmt.setString(i+=1, movimiento.getEstablecimiento());
			pstmt.setString(i+=1, movimiento.getFechaIn());
			pstmt.setString(i+=1, movimiento.getFechaFin());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cajero ] Insert  SQLException: sp_insert_asignacion_de_cajeros "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cajero ] Insert  SQLException: sp_insert_asignacion_de_cajeros "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cajero ] Insert  SQLException: sp_insert_asignacion_de_cajeros "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Cliente(Obj_Clientes cliente){
		String query = "exec sp_insert_cliente ?,?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
//			// insert bitacora
//			String pc = InetAddress.getLocalHost().getHostName();
//			String ip = InetAddress.getLocalHost().getHostAddress();
//			pstmtb = con.prepareStatement(Qbitacora);
//			pstmtb.setString(1, pc);
//			pstmtb.setString(2, ip);
//			pstmtb.setInt(3, usuario.getFolio());
//			pstmtb.setString(4, "sp_insert_cliente alta "+cliente.getNombre().toUpperCase()+cliente.getAp_paterno().toUpperCase()+cliente.getAp_materno().toUpperCase());
//			pstmtb.setString(5, "Cliente Nuevo");
//			pstmtb.executeUpdate();
//			
			
//			private String telefono_cuadrante;
			int i=1;
			pstmt = con.prepareStatement(query);
			pstmt.setString(i,	 	cliente.getNombre().toUpperCase());
			pstmt.setString(i+=1,	cliente.getAp_paterno().toUpperCase());
			pstmt.setString(i+=1,	cliente.getAp_materno().toUpperCase());
			pstmt.setString(i+=1,	cliente.getDireccion().toUpperCase());
			pstmt.setString(i+=1, 	cliente.getTelefono().toUpperCase());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cliente ] Insert  SQLException: sp_insert_cliente "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Empleado ] Insert  SQLException: sp_insert_empleado "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Etapa(Obj_Etapas etapa){
		String query = "exec sp_insert_etapa ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, etapa.getFolio());
			pstmt.setString(2, etapa.getEtapa().toUpperCase().trim());
			pstmt.setString(3, etapa.getAbreviatura().toUpperCase().trim());
			pstmt.setInt(4, etapa.getStatus());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Etapa ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Etapa ] Insert  SQLException: insert_etapa "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Etapa ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Aspecto_de_la_Etapa(Obj_Aspectos_De_La_Etapa aspecto){
		String query = "exec sp_insert_aspectos_de_la_etapa ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, aspecto.getFolio());
			pstmt.setString(2, aspecto.getAspecto().toUpperCase().trim());
			pstmt.setString(3, aspecto.getAbreviatura().toUpperCase().trim());
			pstmt.setInt(4, aspecto.getStatus());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Aspecto_de_la_Etapa ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Aspecto_de_la_Etapa ] Insert  SQLException: insert_etapa "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Aspecto_de_la_Etapa ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Unidad_de_Inspeccion(Obj_Unidades_de_Inspeccion unidad_de_inspeccion){
		String query = "exec sp_insert_unidades_de_inspeccion ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, unidad_de_inspeccion.getFolio());
			pstmt.setString(2, unidad_de_inspeccion.getunidades_de_inspeccion().toUpperCase().trim());
			pstmt.setString(3, unidad_de_inspeccion.getAbreviatura().toUpperCase().trim());
			pstmt.setInt(4, unidad_de_inspeccion.getStatus());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Unidad_de_Inspeccion ] Insert  SQLException: sp_insert_unidades_de_inspeccion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Unidad_de_Inspeccion ] Insert  SQLException: sp_insert_unidades_de_inspeccion "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Unidad_de_Inspeccion ] Insert  SQLException: sp_insert_unidades_de_inspeccion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
			
			
			
			public boolean Guardar_Ticket(Obj_Abono_Clientes movimiento){
		String query = "exec sp_abono_y_ticket_de_clientes ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			int i=1;

			System.out.println( movimiento.getAsignacion());
			System.out.println(movimiento.getCajero());
			System.out.println(movimiento.getFolio_cliente());
			System.out.println(movimiento.getTicket());
			System.out.println(movimiento.getAbono());
			System.out.println(movimiento.getFecha_fin());
			
			pstmt.setString(i, movimiento.getAsignacion());
			pstmt.setString(i+=1, movimiento.getCajero());
			pstmt.setInt(i+=1, movimiento.getFolio_cliente());
			pstmt.setString(i+=1, movimiento.getTicket());
			pstmt.setDouble(i+=1, movimiento.getAbono());
			pstmt.setString(i+=1, movimiento.getFecha_fin());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ticket ] Insert  SQLException: sp_insert_asignacion_de_cajeros "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ticket ] Insert  SQLException: sp_insert_asignacion_de_cajeros "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ticket ] Insert  SQLException: sp_insert_unidades_de_inspeccion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
}