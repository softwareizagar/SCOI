package Cat_Administracion_del_Sistema;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Obj_Administracion_del_Sistema.Obj_Mantenimiento_Base_de_Datos;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Mantenimiento_Base_de_Datos extends JFrame {
          //se declara la variable global cont el contenedor de la ventana
	Container cont=getContentPane();
	      //se declara la variable global panel Y se crea el panel para agregar los componentes
	JLayeredPane panel=new JLayeredPane();
	
	JLabel lblsubmenu=new JLabel("Teclea el Nombre del Submenu:");
	JLabel lblmenu_id=new JLabel("Teclea el Numero del Menu_id:");
	JLabel lblmenu_principal =new JLabel("Teclea el Numero del Menu Principal:");
	
	JButton btn_rbd=new JButton("Reducir Log de la Base de Datos ");
	JButton btn_asubmenus=new JButton("Agregar Submenus Nuevos a Usuarios");
	JButton  btn_Guardar=new JButton("Guardar");

	JTextField txtNombreSubmenu = new Componentes().text(new JTextField(), "Nombre del Submenu, deber� tener el mismo nombre que el catalogo, sin Cat ni el guion bajo", 100, "String");
	JTextField txtMenu_id = new Componentes().text(new JTextField(), "Numero del Submenu-Menu_id este deber� tener el numero id del grupo de submenus", 30, "Int");
	JTextField txtMenu_Principal = new Componentes().text(new JTextField(), "Numero del Menu Principal-Encabezado este deber� tener el numero de menu principal contado de izquierda a derecha", 30, "Int");


	
	  //se crea el constructor de la ventana la regla es que el contructor  debe de llamarse igual que la clase
     	public Cat_Mantenimiento_Base_de_Datos (){
     		   //esto agrega un icono para que no aparezca el icono de java si no el que especifiques
     	      this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Table.png"));
     	      
     		   //se pone el nombre de que aparecera como titulo en la ventana
     		  this.setTitle("Mantenimiento a Base de Datos SCOI");
     		  
     		   //se agregan los componentes a la variable panel de jlayeredpane
     		   //se agrega el boton declarado y se leda la localizacion con el .setbounds(Xposicion,Yposicion ,Xancho, Yalto) todos
     		   //losvalores deben de ser en pixeles     		  
     		  this.panel.add(btn_rbd).setBounds(20,30,300,20);
     		  this.panel.add(btn_asubmenus).setBounds(20,60,300,20);
     		  this.panel.add(btn_Guardar).setBounds(120,320,80,20);
     		  
    		  this.panel.add(lblsubmenu).setBounds(30,150,300,20);
    		  this.panel.add(txtNombreSubmenu).setBounds(20,170,300,20);
    		  this.panel.add(lblmenu_id).setBounds(30,200,300,20);
    		  this.panel.add(txtMenu_id).setBounds(20,220,300,20);
    		  this.panel.add(lblmenu_principal).setBounds(30,250,300,20);
     		  this.panel.add(txtMenu_Principal).setBounds(20,270,300,20);
     		  
     		  
     		  txtNombreSubmenu.setEditable(false);
     		  txtMenu_id.setEditable(false);
     		  txtMenu_Principal.setEditable(false);
     		  btn_Guardar.setEnabled(false);
     		  
     		 //esto agrega el panel al contenedor 
		      this.cont.add(panel);
		     
		      //se liga el boton con la accionlistener 
		      this.btn_rbd.addActionListener(reducirlog);
		      this.btn_asubmenus.addActionListener(agregar_submenus_nuevos);
		      this.btn_Guardar.addActionListener(guardarsubmenu);	
		      
		       this.setSize(350,400); 
		      //para que no se modifique el tama�o
		      this.setResizable(false);
		      //esto te posiciona la venta al centro si no se pone estara al margen superior derecho
		       this.setLocationRelativeTo(null);
     
		      
       	 }
     	//se le agrega la accion al boton
 	 ActionListener reducirlog =new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub se le agrega la accion  lo siguiente seria crear un objeto con instancia
			//para que me devuelva cierto o falso si se hiso la accion
            //pregunta si la variable instancia y el objeto. funcion retornada es igual a verdadero o falso   
			if(new Obj_Mantenimiento_Base_de_Datos().reducir_Log()==true ){
				//este  habre una ventana de mensaje en la cual se
				 JOptionPane.showMessageDialog(null,"Se Redujo el log Satisfactoriamente","Aviso",JOptionPane.INFORMATION_MESSAGE); }
			else{JOptionPane.showMessageDialog(null,"No se Pudo Ejecutar la Reduccion","Aviso",JOptionPane.ERROR_MESSAGE);
			
			}
			
		}
	};
	
	ActionListener agregar_submenus_nuevos  =new ActionListener() {			
		public void actionPerformed(ActionEvent e) {
   		  txtNombreSubmenu.setEditable(true);
   		  txtMenu_id.setEditable(true);
   		  txtMenu_Principal.setEditable(true);
   		  btn_Guardar.setEnabled(true);
		}
	};
	ActionListener guardarsubmenu =new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
   		  txtNombreSubmenu.setEditable(false);
   		  txtMenu_id.setEditable(false);
   		  txtMenu_Principal.setEditable(false);
   		  btn_Guardar.setEnabled(false);
   		  new Obj_Mantenimiento_Base_de_Datos().agregar_submenus_nuevos(txtNombreSubmenu.getText().toLowerCase().toString(),Integer.valueOf(txtMenu_id.getText()),Integer.valueOf(txtMenu_Principal.getText()) );
   		  
   		  
			
		}
		
	};
	
	public static void main(String args[]){
		try{
			new Cat_Mantenimiento_Base_de_Datos().setVisible(true);
			
		}catch(Exception e){}
	}
 }

