package Cat_Administracion_del_Sistema;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Obj_Administracion_del_Sistema.Obj_Asistencia_Y_Puntualidad;

@SuppressWarnings("serial")
public class Cat_Asistencia_Y_Puntualidad extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtValorAsistencia = new JTextField();
	JTextField txtValorPuntualidad = new JTextField();
	JTextField txtGafete = new JTextField();
	
	JButton btnNuevo = new JButton("Nuevo");
	JButton btnEditar = new JButton("Editar");
	JButton btnSalir = new JButton("Salir");
	JButton btnGuardar = new JButton("Guardar");
	JButton btnDeshacer = new JButton("Deshacer");
	
	public Cat_Asistencia_Y_Puntualidad(){
		this.setTitle("Asistencia  y Puntualidad");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Dollar.png"));
		int x=25, y=25, z=80;
		panel.setBorder(BorderFactory.createTitledBorder("Asistencia y Puntualidad"));
		
		panel.add(new JLabel("Asistencia:")).setBounds(x,y,z,20);		
		panel.add(txtValorAsistencia).setBounds(x+z,y,z+50,20);
		panel.add(new JLabel("Puntualidad:")).setBounds(x,y+=30,z,20);
		panel.add(txtValorPuntualidad).setBounds(x+z,y,z+50,20);
		panel.add(new JLabel("Gafete:")).setBounds(x,y+=25,z+50,20);
		panel.add(txtGafete).setBounds(x+z,y,z+50,20);
		
		panel.add(btnSalir).setBounds(x,y+=30,z-20,20);
		panel.add(btnEditar).setBounds(x+z-10,y,z,20);
		panel.add(btnGuardar).setBounds(x+z+z,y,z,20);
		
		btnSalir.addActionListener(opSalir);
		btnGuardar.addActionListener(opGuardar);
		btnEditar.addActionListener(opEditar);
		
		txtValorAsistencia.addKeyListener(validaNumericoConPunto);
		txtValorPuntualidad.addKeyListener(validaNumericoConPunto);
		obtener();
		cont.add(panel);
			
		this.setSize(305,180);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opSalir = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	};
	
	ActionListener opEditar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtValorAsistencia.setEditable(true);
			txtValorPuntualidad.setEditable(true);
			txtGafete.setEditable(true);
		}
	};
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			Obj_Asistencia_Y_Puntualidad objeto = new Obj_Asistencia_Y_Puntualidad();
			try {
				objeto = objeto.nuevo();
				
				if(objeto.getExiste() != 0){
					Obj_Asistencia_Y_Puntualidad asistencia_puntualidad = new Obj_Asistencia_Y_Puntualidad();
					
					asistencia_puntualidad.setValorAsistencia(Float.parseFloat(txtValorAsistencia.getText()));
					asistencia_puntualidad.setValorPuntualidad(Float.parseFloat(txtValorPuntualidad.getText()));
					asistencia_puntualidad.setValorGafete(Float.parseFloat(txtGafete.getText()));
					asistencia_puntualidad.actualizar(1);	
					txtValorAsistencia.setEditable(false);
					txtValorPuntualidad.setEditable(false);
					txtGafete.setEditable(false);
				}else{
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
					}else{
						Obj_Asistencia_Y_Puntualidad asistencia_puntualidad = new Obj_Asistencia_Y_Puntualidad();
						
						asistencia_puntualidad.setValorAsistencia(Float.parseFloat(txtValorAsistencia.getText()));
						asistencia_puntualidad.setValorPuntualidad(Float.parseFloat(txtValorPuntualidad.getText()));
						asistencia_puntualidad.setValorGafete(Float.parseFloat(txtGafete.getText()));
						asistencia_puntualidad.guardar();	
						txtValorAsistencia.setEditable(false);
						txtValorPuntualidad.setEditable(false);
						txtGafete.setEditable(false);
						JOptionPane.showMessageDialog(null,"El registro se guard� de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Error");
			}
		
		}
	};
	
	private String validaCampos(){
		String error="";
		
		if(txtValorAsistencia.getText().equals("")) 	error+= "     Valor de Asistencia\n";
		if(txtValorPuntualidad.getText().equals("")) 	error+= "     Valor de Puntualidad\n";
		if(txtGafete.getText().equals("")) 		error+= "     Valor de Gafete\n";		
		return error;
	}
	
	public void obtener(){
		Obj_Asistencia_Y_Puntualidad objeto = new Obj_Asistencia_Y_Puntualidad();
		try {
			objeto = objeto.nuevo();
			if(objeto.getExiste() != 0){
				Obj_Asistencia_Y_Puntualidad llenar;
				try {
					llenar = new Obj_Asistencia_Y_Puntualidad().buscar(1);
					txtValorAsistencia.setText(llenar.getValorAsistencia()+"");
					txtValorPuntualidad.setText(llenar.getValorPuntualidad()+"");
					txtGafete.setText(llenar.getValorGafete()+"");
					txtValorAsistencia.setEditable(false);
					txtValorPuntualidad.setEditable(false);
					txtGafete.setEditable(false);
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error");
		}
		
	}
	
	KeyListener validaNumericoConPunto = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
		    if(((caracter < '0') ||	
		    	(caracter > '9')) && 
		    	(caracter != '.' )){
		    	e.consume();
		    	}
		    
		   if (caracter==KeyEvent.VK_PERIOD){
			   	String texto = txtValorAsistencia.getText().toString();
			    String texto1 = txtValorPuntualidad.getText().toString();
				if (texto.indexOf(".")>0 && texto1.indexOf(".")> 0 ) e.consume();
			}
		    		    		       	
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
								
	};
	
}
