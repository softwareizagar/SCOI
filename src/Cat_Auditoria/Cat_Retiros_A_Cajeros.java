package Cat_Auditoria;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Auditoria.Obj_Retiros_Cajeros;
import Obj_Principal.Componentes;


@SuppressWarnings("serial")
public class Cat_Retiros_A_Cajeros extends JFrame {
	
	Object[][] Matriz_pedidos_ctes ;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	Runtime R = Runtime.getRuntime();
	
	JTextField txtNombre = new Componentes().text(new JTextField(),"Nombre", 250, "String");
	JTextField txtEstablecimiento = new Componentes().text(new JTextField(),"Establecimiento", 150, "String");
	JTextField txtFolio_empleado =  new Componentes().text(new JTextField(),"Folio Empleado", 150, "String");
	JTextField txtpuesto =new Componentes().text(new JTextField(),"Puesto", 150, "String");
	JTextField txtasignacion =new Componentes().text(new JTextField(),"Asignacion", 150, "String");
	JTextField txtpc = new Componentes().text(new JTextField(),"Nombre Pc", 150, "String");

	JButton btnFoto = new JButton();
	JButton btnaviso = new JButton();
	
	Icon iconoFondo_cajero;
	ImageIcon ImagenconFondo_cajero;
	JLabel jlFondo_cajero =new JLabel();
	
	int folio_empleado =0;
    float importe_retiros_guardados =0;
    float importe_nuevo_devuelto=0;
    float valor_a_retirar_deacuerdo_al_dia =0;
    
    boolean cerrarhilo = false;
    
	public Cat_Retiros_A_Cajeros(){
		this.cont.add(panel);
		this.setSize(355,119);
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/boveda-de-dinero-en-efectivo-de-seguridad-icono-6192-32.png"));
		this.cont.add(panel);
		this.setTitle("Retiros A Cajeros");
		
		folio_empleado=new Obj_Usuario().LeerSession().getFolio();
		
		Obj_Retiros_Cajeros datosEmpleado= new Obj_Retiros_Cajeros().buscarEmpleado(folio_empleado);
		
		if(datosEmpleado.getAsignacion()== null){
            
			JOptionPane.showMessageDialog(null, "El Usuario No Esta Asignado ", "Avisa al Administrador", JOptionPane.WARNING_MESSAGE);
			btnaviso.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLUE>" +
					"		<CENTER><p> CIERRA ESTA VENTANA Y VUELVE A INTENTARLO CUANDO TE ASIGNEN O HAGAS LA PRIMER VENTA</p></CENTER></FONT></html>"); 
			panel.add(btnaviso).setBounds(1,1,350,90);
    		
		}else{
			
		this.setUndecorated(true);
		this.setOpacity(0.99f);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		btnFoto.setToolTipText("<F5> Tecla Directa");
		btnFoto.addActionListener(Buscar_Cambios);
		
		Hilo_1_Minuto();
		cargar_datos_del_empleado(folio_empleado);
		
		panel.add(btnFoto).setBounds(6,6,135,105);
		panel.add(txtFolio_empleado).setBounds(145,7,30,20);
		panel.add(txtasignacion).setBounds(275,7,70,20);
		panel.add(txtNombre).setBounds(145,27,200,20);
		panel.add(txtEstablecimiento).setBounds(145,47,200,20);
		panel.add(txtpuesto).setBounds(145,67,200,20);
		panel.add(txtpc).setBounds(145,87,200,20);
		
		txtFolio_empleado.setEditable(false);
		txtasignacion.setEditable(false);
		txtNombre.setEditable(false);
		txtEstablecimiento.setEditable(false);
		txtpuesto.setEditable(false);
		txtpc.setEditable(false);
		
         //////fondo		
		ImagenconFondo_cajero = new ImageIcon("imagen/marco_aux_caja.png");
	    iconoFondo_cajero = new ImageIcon(ImagenconFondo_cajero.getImage().getScaledInstance(355,117, Image.SCALE_DEFAULT));
	    jlFondo_cajero.setIcon(iconoFondo_cajero);
	    panel.add(jlFondo_cajero).setBounds(0,0,355,117);
             
           //  Buscar Con F5
                  getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                     KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "Actualizar");
                  getRootPane().getActionMap().put("Actualizar", new AbstractAction(){
                      public void actionPerformed(ActionEvent e)
                      {        	    btnFoto.doClick();          	    }
                  });
           // asigna el foco al JTextField fecha al arrancar la ventana
                  this.addWindowListener(new WindowAdapter() {
                          public void windowOpened( WindowEvent e ){
                        	  txtFolio_empleado.requestFocus();
                       }
                  });
		}    
		dispose();
	}

	public void cargar_datos_del_empleado(Integer folio_empleado){
		
		Obj_Retiros_Cajeros datosEmpleado= new Obj_Retiros_Cajeros().buscarEmpleado(folio_empleado);
				
 		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_cajero/cajerotmp.jpg");
  	    btnFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(120, 95, Image.SCALE_DEFAULT)));	
  	    txtFolio_empleado.setText(datosEmpleado.getFolio_empleado()+"");
  	    txtNombre.setText(datosEmpleado.getNombre()+"");
  	    txtEstablecimiento.setText(datosEmpleado.getEstablecimiento()+"");
  	    txtpuesto.setText(datosEmpleado.getPuesto()+"");
  	    txtpc.setText(datosEmpleado.getPc()+"");
  	    txtasignacion.setText(datosEmpleado.getAsignacion()+"");
  	    btnFoto.doClick();
  	    
  	    
	 String  Guardo_sesion=new Obj_Retiros_Cajeros().guardar_sesion(datosEmpleado.getEstablecimiento()+"",folio_empleado);
		  
		   if(Guardo_sesion !="Error en GuardarSQL"){
	      
		  }else{
			  JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_A_Cajeros  en la funcion Guardo_sesion \n no se pudo obtener el nombre de la pc ", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		            }
	  
	  
	}
			
	/////////CONSULTA EL IMPORTE NUEVO
   	public float Consulta_de_Importe_Nuevo(){
   		float importe_nuevo =0;
   		
	   	   String pc_nombre="";
			try {
			    	pc_nombre = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_cajero \n no se pudo obtener el nombre de la pc "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		
   		String query_importe_nvo="select  isnull(sum(a.importe),0)as importe ,a.folio_asignacion ,a.establecimiento from" +
   				                           " (SELECT   isnull(sum(liquidaciones_tickets.importe),0)as importe" +
   				                           "          ,asignaciones_cajeros.folio as folio_asignacion" +
   				                           "          ,(select nombre from establecimientos where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')))as establecimiento" +
   				                           "    FROM liquidaciones_tickets" +
   				                           "          LEFT OUTER JOIN  asignaciones_cajeros on asignaciones_cajeros.folio = liquidaciones_tickets.folio_asignacion  and asignaciones_cajeros.status='V'" +
   				                           "    WHERE  liquidaciones_tickets.afectacion='+' AND liquidaciones_tickets.forma_pago=1" +
   				                           "                    and (liquidaciones_tickets.folio_asignacion = (select folio_asignacion from cajeros where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')) and e_mail='"+folio_empleado+"'))" +
   				                           "	GROUP by asignaciones_cajeros.folio " +
   				                           " union all " +
   				                           " SELECT   isnull(sum(liquidaciones_tickets.importe),0)*-1 as importe " +
   				                           "         ,asignaciones_cajeros.folio as folio_asignacion" +
   				                           "         ,(select nombre from establecimientos where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')))as establecimiento" +
   				                           "    FROM liquidaciones_tickets" +
   				                           "         LEFT OUTER JOIN  asignaciones_cajeros on asignaciones_cajeros.folio = liquidaciones_tickets.folio_asignacion  and asignaciones_cajeros.status='V'" +
   				                           "    WHERE  liquidaciones_tickets.afectacion='-' AND liquidaciones_tickets.forma_pago=1" +
   				                           "                  and (liquidaciones_tickets.folio_asignacion = (select folio_asignacion from cajeros where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')) and e_mail='"+folio_empleado+"'))" +
   				                           "        		 group by asignaciones_cajeros.folio)a" +
   				                           "  group by a.folio_asignacion,a.establecimiento";
//   				
//   				"SELECT   isnull(sum(liquidaciones_tickets.importe),0)as importe " +
//   				                         " ,asignaciones_cajeros.folio as folio_asignacion " +
//   				                         " ,(select nombre from establecimientos where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')))as establecimiento " +
//   				                   "  FROM liquidaciones_tickets" +
//   				                   "      LEFT OUTER JOIN  asignaciones_cajeros on asignaciones_cajeros.folio = liquidaciones_tickets.folio_asignacion  and asignaciones_cajeros.status='V'" +
//   				                   "  WHERE liquidaciones_tickets.afectacion='+' AND liquidaciones_tickets.forma_pago=1" +
//   				                   "  and (liquidaciones_tickets.folio_asignacion = (select folio_asignacion from cajeros where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')) and e_mail='"+folio_empleado+"'))" +
//   				                   		" group by asignaciones_cajeros.folio";
   		
		Statement s;
		ResultSet rs2;
		
		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs2 = s.executeQuery(query_importe_nvo);
			while(rs2.next()){
				importe_nuevo = rs2.getFloat(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_a_Cajeros  en la funcion [ Consulta_de_Importe_Nuevo ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return importe_nuevo; 
	}
   	
	/////////CONSULTA EL DE LOS RETIROS YA GUARDADOS
   	public float Consulta_El_Importe__de_los_Retiros_Guardados(){
   		
   		String query_importe_retiros="select isnull(sum(importe_retiro),0)as importe_retiro from tb_retiros_a_cajeros where status_retiro_corte=1 and folio_cajero="+folio_empleado;
   		
		Statement s;
		ResultSet rs2;
		
		try {
			s = new Connexion().conexion().createStatement();
			rs2 = s.executeQuery(query_importe_retiros);
			while(rs2.next()){
				importe_retiros_guardados = rs2.getFloat(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_a_Cajeros  en la funcion [ Consulta_de_Importe_Nuevo ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return importe_retiros_guardados; 
	}
   	
	/////////CONSULTA DEL VALOR A RETIRAR DE ACUERDO AL DIA
   	public float Consulta_del_Importe_del_retiro_del_dia(){
   		
   		String query_importe_retiros_del_dia="exec sp_obtener_importe_del_retiro_del_dia";
   		
		Statement s;
		ResultSet rs2;
		
		try {
			s = new Connexion().conexion().createStatement();
			rs2 = s.executeQuery(query_importe_retiros_del_dia);
			while(rs2.next()){
				valor_a_retirar_deacuerdo_al_dia = rs2.getFloat(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_a_Cajeros  en la funcion [ Consulta_del_Importe_del_retiro_del_dia ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return valor_a_retirar_deacuerdo_al_dia; 
	}
   	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////ACTUALIZAR

ActionListener Buscar_Cambios = new ActionListener(){
public void actionPerformed(ActionEvent e){
try {

importe_retiros_guardados        = Consulta_El_Importe__de_los_Retiros_Guardados();
importe_nuevo_devuelto           = Consulta_de_Importe_Nuevo();
valor_a_retirar_deacuerdo_al_dia = Consulta_del_Importe_del_retiro_del_dia();



if(importe_nuevo_devuelto-importe_retiros_guardados >= valor_a_retirar_deacuerdo_al_dia){
	
	String establecimiento= txtEstablecimiento.getText()+"" ;
	        cerrarhilo=true;
			//   apartado para configurar el uso de la pantalla de avisos--------------------------------
					JDialog frame = new JDialog();
					String ruta= "prueba mensaje";//fila_mensaje.get(3).toString().trim();
					frame.setUndecorated(true);
					
					new Cat_Avisos_De_Pedido(frame,ruta,establecimiento,valor_a_retirar_deacuerdo_al_dia);
					frame.setVisible(true);

}

} catch (Exception e1) {
System.out.println(e1.getMessage());
JOptionPane.showMessageDialog(null, "Error en Cat_Consulta_De_Status_De_Pedidos_De_Clientes en la funcion Buscar_Cambios   SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
}
}
};
   	
////////////HILO REVISION AUTOMATICA DE PEDIDOS CADA 60 SEGUNDOS
	public void Hilo_1_Minuto() {
			segundero seg = new segundero();
			seg.start();
		    	}
		    	int reconsultar=0;
		    	public class segundero extends Thread {
		    		public void run() {
		    			while(cerrarhilo !=true){
		    					try {
		    						Thread.sleep(1000);
		    						reconsultar+=1;
		    						if(reconsultar==120)////cambiar a 60 segundos
		    						{
		    						   reconsultar=0;
		    						   btnFoto.doClick();
		    						}
		    					} catch (InterruptedException e) {
		    		                 JOptionPane.showMessageDialog(null, "Error en Cat_Hilo_1_Minuto en la funcion segundero  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		    						System.err.println("Error: "+ e.getMessage());
		    				}
		    			}
		    	}
		    }
		    	
		    	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////CATALOGO EMERGENTE DEL RETIRO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	    	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    	
	  	public class Cat_Avisos_De_Pedido extends JComponent {
		    		
		    		JPasswordField txtClaveSupervisor = new Componentes().textPassword(new JPasswordField(), "Clave", 30);
		    		JPasswordField txtClaveSupervisorconfirma = new Componentes().textPassword(new JPasswordField(), "Clave del Supervisor Confirmacion", 30);
		    		JLabel lblclave = new JLabel("Clave del Supervisor");
		    		JLabel lblretiro = new JLabel("Cantidad del Retiro");
		    		JLabel lblNombre_Supervisor= new JLabel();
		    		JLabel lblConfirmacion= new JLabel("Confirmacion Clave De Supervisor");
		    		
		    		Icon iconoFondo;
		    		ImageIcon tmpIconAuxFondo;
		    		JButton btnFoto_supervisor = new JButton();
		    		JButton btnNoExiste_Supervisor = new JButton();
		    		JButton btnError_Clave_requerida =new JButton();
		    		JButton btnError =new JButton();
		    		JButton btnValidar_Retiro= new JButton("Validar",new ImageIcon("imagen/Aplicar.png"));
		    		JButton btnEditar_retiro = new JButton("Editar Retiro",new ImageIcon("imagen/editara.png"));
		    		JButton btnImprimir = new JButton("Imprimir",new ImageIcon("imagen/Print.png"));
		    		JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
		    		
		    		JLabel fondo = new JLabel();
		    		JTextField txtfolio_Supervisor = new Componentes().text(new JTextField(),"folio_supervisor", 250, "String");
		    		JTextField txtRetiro = new Componentes().text(new JTextField(),"Cantidad del Retiro del Cajero", 30, "Double");
		    		JDialog framesalir=null;
		    		String Establecimiento="";
		    		String folio_retiro="";
		    		float importe_del_retiro_del_dia=0;
		    		
		    		public Cat_Avisos_De_Pedido(final JDialog frame,String ruta, String establecimiento,float Importe_del_Retiro_del_dia) {
		    			Establecimiento=establecimiento;
		    			framesalir=frame;
		    			importe_del_retiro_del_dia=Importe_del_Retiro_del_dia;
		    			
		    			frame.setModal(true);
		    			frame.add(lblclave).setBounds(100,10,200,20); 
		    			frame.add(txtClaveSupervisor).setBounds(80,30,140,20);
		    			frame.add(btnFoto_supervisor).setBounds(85,70,135,105);
		    			frame.add(lblNombre_Supervisor).setBounds(60,190,220,20);
		    			frame.add(lblretiro).setBounds(100,270,200,20); 
		    			frame.add(txtRetiro).setBounds(80,290,140,20);
		    			frame.add(btnValidar_Retiro).setBounds(100,330,100,20);
		    			frame.add(lblConfirmacion).setBounds(70,380,200,20);
		    			frame.add(txtClaveSupervisorconfirma).setBounds(80,400,140,20);
		    			frame.add(btnEditar_retiro).setBounds(15,440,130,20);
		    			frame.add(btnSalir).setBounds(155,440,130,20);
		    			frame.add(btnNoExiste_Supervisor).setBounds(8,245,283,345);
		    			frame.add(btnError_Clave_requerida).setBounds(8,245,283,345);
		    			frame.add(btnError).setBounds(8,470,283,120);
		    			frame.add(fondo).setBounds(0,0,300,600);
		    			
		    			
		    			tmpIconAuxFondo = new ImageIcon("imagen/retiro_cajero.png");
		                iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(300,600, Image.SCALE_DEFAULT));
		                fondo.setIcon(iconoFondo);
	
		                
		        		btnNoExiste_Supervisor.setText(	"<html> <FONT FACE="+"arial"+" SIZE=7 COLOR=RED>" +
		        										"		<CENTER><p>NO EXISTE</p></CENTER>" +
		        										"		<CENTER><p>EL SUPERVISOR</p></CENTER>" +
		        										"		<CENTER><p> O CLAVE INCORRECTA</p></CENTER></FONT>" +
		        										"</html>"); 
		                
		        		btnError_Clave_requerida.setText(	"<html> <FONT FACE="+"arial"+" SIZE=7 COLOR=BLACK>" +
								"		<CENTER><p> SE REQUIERE CLAVE DE SUPERVISOR</p></CENTER></FONT></html>"); 
		        		
		        		
		        		txtClaveSupervisor.setEditable(true);
		                txtRetiro.setVisible(false);
		                lblNombre_Supervisor.setVisible(false);
		                lblretiro.setVisible(false);
		                lblConfirmacion.setVisible(false);
		                btnNoExiste_Supervisor.setVisible(false);
		                btnError_Clave_requerida.setVisible(false);
		                btnValidar_Retiro.setVisible(false);
		                btnFoto_supervisor.setVisible(false);
		                btnEditar_retiro.setVisible(false);
		                btnImprimir.setVisible(false);
		                btnSalir.setVisible(false);
		                btnError.setVisible(false);
		                btnEditar_retiro.setEnabled(false);
		                btnImprimir.setEnabled(false);
		                
		                
		                txtClaveSupervisorconfirma.setVisible(false);
		                
		    			frame.setLayout(new BorderLayout( ));
		    			frame.getContentPane( ).add("Center",this);
		    			frame.setAlwaysOnTop( true );
		    			frame.setSize(300,600);
		    			frame.setLocationRelativeTo(null);
		    			
		    			txtRetiro.setText(importe_del_retiro_del_dia+"");
		    			
	    			    txtClaveSupervisor.addKeyListener(buscar_supervisor);
		    			txtClaveSupervisorconfirma.addKeyListener(validacion_clave_supervisor);
		    			txtRetiro.addKeyListener(pasar_a_validar_retiro);
			            btnValidar_Retiro.addActionListener(pasar_a_validar_clave_supervisor);
			            btnEditar_retiro.addActionListener(regresar_modificar_retiro);
			        	btnImprimir.addActionListener(imprimir_retiro); 
		    			btnSalir.addActionListener(salir);
		    			
		    		}
		    		
		    		KeyListener buscar_supervisor = new KeyListener() {
		    			@SuppressWarnings("deprecation")
						public void keyPressed(KeyEvent e) {	
		    				
		    				if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    					cargar_datos_del_supervisor(txtClaveSupervisor.getText()+"");
		    				}
		    			}
		    			public void keyReleased(KeyEvent e) {}
		    			public void keyTyped(KeyEvent e) {}
		            }; 
		            
		            
		    		KeyListener validacion_clave_supervisor = new KeyListener() {
		    			@SuppressWarnings("deprecation")
						public void keyPressed(KeyEvent e) {	
		    				
		    				if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    					btnError.setVisible(false);
		    					validar_clave_de_supervisor(txtClaveSupervisorconfirma.getText()+"");
		    				}
		    			}
		    			public void keyReleased(KeyEvent e) {}
		    			public void keyTyped(KeyEvent e) {}
		            };
		            
		            
		            
		    		KeyListener pasar_a_validar_retiro = new KeyListener() {
		    			public void keyPressed(KeyEvent e) {	
		    				if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    					btnValidar_Retiro.doClick();
		    				}
		    			}
		    			public void keyReleased(KeyEvent e) {}
		    			public void keyTyped(KeyEvent e) {}
		            }; 

		            
		    		@SuppressWarnings("deprecation")
					public void cargar_datos_del_supervisor(String clave){
		    			
		    			if(txtClaveSupervisor.getText().toUpperCase().equals("")){
		                    txtClaveSupervisor.setText("");
		                    txtClaveSupervisor.requestFocus();
		                    txtClaveSupervisorconfirma.setVisible(true);
		                    txtClaveSupervisorconfirma.setEditable(false);
		                    btnError_Clave_requerida.setVisible(true);
		                    btnNoExiste_Supervisor.setVisible(false);
		                    btnFoto_supervisor.setVisible(false);
		                    return;
		    			}else{
		    				
		    			
		    			Obj_Retiros_Cajeros datosSupervisor= new Obj_Retiros_Cajeros().buscarSupervisor(clave);
		    			if(datosSupervisor.getExiste_supervisor().equals("NO EXISTE")){
			    	 		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.jpg");
			    	 		btnFoto_supervisor.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(120, 95, Image.SCALE_DEFAULT)));
			    	 		lblNombre_Supervisor.setVisible(true);
		                    txtClaveSupervisor.setText("");
		                    txtClaveSupervisor.requestFocus();
		                    
			    	 		lblNombre_Supervisor.setText(	"<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=RED>" +
									"	<CENTER><p> "+datosSupervisor.getNombre_Supervisor()+"</p></CENTER></FONT></html>"); 
			    	 		
			    	 		btnNoExiste_Supervisor.setVisible(true);
			    	 		btnError_Clave_requerida.setVisible(false);
			    	 		btnFoto_supervisor.setVisible(true);
			    	 		
		    			}else{
		    			
		    	 		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_supervisor/supervisortmp.jpg");
		    	 		btnFoto_supervisor.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(120, 95, Image.SCALE_DEFAULT)));	
		    	        lblNombre_Supervisor.setText(datosSupervisor.getNombre_Supervisor()+"");
		    	        txtfolio_Supervisor.setText(datosSupervisor.getFolio_supervisor()+"");
		    	        
		    	        lblNombre_Supervisor.setVisible(true);
		    	        lblretiro.setVisible(true);
		    	        lblConfirmacion.setVisible(true);
		    	        txtClaveSupervisor.setEditable(false);
		    	        
                        txtClaveSupervisorconfirma.setEditable(false);		    	        
		    	        txtClaveSupervisorconfirma.setVisible(true);
		    	        txtRetiro.setVisible(true);
		    	        txtRetiro.setEditable(false);
		    	       
		    	        
		    	        btnNoExiste_Supervisor.setVisible(false);
		    	        btnError_Clave_requerida.setVisible(false);
		    	        btnValidar_Retiro.setVisible(true);
		    	        btnValidar_Retiro.setEnabled(true);
		    	        btnValidar_Retiro.doClick();
		    	        btnFoto_supervisor.setVisible(true);
		    	        
		    			}
		    		    }	
		    		}
		    		
		    		@SuppressWarnings("deprecation")
					public void validar_clave_de_supervisor(String clave_supervisor){
		    			if(txtClaveSupervisorconfirma.getText().toUpperCase().equals("")){
		                    txtClaveSupervisorconfirma.setText("");
		                    txtClaveSupervisorconfirma.requestFocus();
			        		btnError.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED>" +
									"		<CENTER><p> SE REQUIERE LA CLAVE DEL MISMO SUPERVISOR</p></CENTER></FONT></html>"); 
		                    btnError.setVisible(true);		                    
		    			}else{
		    				Obj_Retiros_Cajeros validar_Supervisor_Guardar_Retiro = new Obj_Retiros_Cajeros().buscarSupervisor(txtClaveSupervisor.getText());
		    				  if( validar_Supervisor_Guardar_Retiro.getClave().equals(txtClaveSupervisorconfirma.getText()))
		    						{
		    					  if(Float.valueOf(txtRetiro.getText())>10000){
		  			        		btnError.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED>" +
											"		<CENTER><p> EL MONTO MAYOR A RETIRAR DEBE DE SER DE:"+10000+"</p></CENTER></FONT></html>"); 
				                    btnError.setVisible(true);
		    						  
		    					  }else{
		    						  
		    					  folio_retiro=new Obj_Retiros_Cajeros().guardar(Establecimiento,folio_empleado,Integer.valueOf(txtfolio_Supervisor.getText()),Float.valueOf(txtRetiro.getText()))+"";
		    					  
		    					  if(folio_retiro !="Error en GuardarSQL"){
		    					  btnValidar_Retiro.setEnabled(false);
		    					  btnEditar_retiro.setEnabled(false);
		    					  txtClaveSupervisorconfirma.setEnabled(false);
		    					  btnImprimir.setEnabled(true);
		    					  btnSalir.setVisible(true);
		    					  btnSalir.setEnabled(true);
                                  btnSalir.requestFocus();
                                  
		    					 }else{
					                    txtClaveSupervisorconfirma.setText("");
					                    txtClaveSupervisorconfirma.requestFocus();
						        		btnError.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED>" +
												"		<CENTER><p>ERROR INTERNO EN EL GUARDADO AVISAR A SISTEMAS</p></CENTER></FONT></html>"); 
					                    btnError.setVisible(true);	
		    					 }
		    					  
		    					  }
		    					 
		    				   }else{
				                    txtClaveSupervisorconfirma.setText("");
				                    txtClaveSupervisorconfirma.requestFocus();
					        		btnError.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED>" +
											"		<CENTER><p> SE REQUIERE LA CLAVE DEL MISMO SUPERVISOR</p></CENTER></FONT></html>"); 
				                    btnError.setVisible(true);	
		    				        }
		    				
		    			}
		    		}
		    		
		    		ActionListener pasar_a_validar_clave_supervisor = new ActionListener(){
		    			public void actionPerformed(ActionEvent e){
		    				lblConfirmacion.setVisible(true);
		    				txtRetiro.setEditable(false);
		    				txtClaveSupervisorconfirma.setEditable(true);
		    				txtClaveSupervisorconfirma.requestFocus();
		    				btnEditar_retiro.setVisible(true);
		    				btnEditar_retiro.setEnabled(true);
		    				btnImprimir.setVisible(true);
		    				btnImprimir.setEnabled(false);
		    				btnValidar_Retiro.setEnabled(false);
		    			}
		    		};
		    		
		    		ActionListener regresar_modificar_retiro = new ActionListener(){
		    			public void actionPerformed(ActionEvent e){
		    				lblConfirmacion.setVisible(true);
		    				txtRetiro.setEditable(true);
		    				txtRetiro.requestFocus();
		    				txtClaveSupervisorconfirma.setEditable(false);
		    				txtClaveSupervisorconfirma.setText("");
		    				btnEditar_retiro.setVisible(true);
		    				btnEditar_retiro.setEnabled(false);
		    				btnSalir.setVisible(false);
		    				btnImprimir.setVisible(false);
		    				btnError.setVisible(false);
		    				btnValidar_Retiro.setEnabled(true);
		    				btnEditar_retiro.setVisible(false);
		    			}
		    		};
		    		
				  	ActionListener salir = new ActionListener(){
						public void actionPerformed(ActionEvent e){
							btnImprimir.doClick();
							framesalir.dispose();
							cerrarhilo=false;
							Hilo_1_Minuto();
						}
					};
					
					
					ActionListener imprimir_retiro = new ActionListener(){
						public void actionPerformed(ActionEvent e){
							System.out.println("retiro:"+folio_retiro);
						 Reporte_De_Retiros_Cajeros(folio_retiro);
						}
					};
					
					
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public void Reporte_De_Retiros_Cajeros(String folio_retiro) {
						
						String query_corte_caja = "exec sp_Reporte_De_Retiros_A_Cajeros '"+folio_retiro+"';";
						Statement stmt = null;
						try {
							stmt =  new Connexion().conexion().createStatement();
						    ResultSet rs = stmt.executeQuery(query_corte_caja);
							JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Retiro_A_Cajeros.jrxml");
							JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
							JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
							JasperViewer.viewReport(print, false);
						} catch (Exception e) {
							System.out.println(e.getMessage());
							JOptionPane.showMessageDialog(null, "Error En Cat_Retiros_A_Cajeros en la funcion Reporte_De_Retiros_Cajeros impresion ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						}
					 }
					
		    	}

}