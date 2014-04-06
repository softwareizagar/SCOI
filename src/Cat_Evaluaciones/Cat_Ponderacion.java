package Cat_Evaluaciones;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Conexiones_SQL.Connexion;
import Obj_Evaluaciones.Obj_Ponderacion;
import Obj_Principal.Componentes;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Ponderacion extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	DefaultTableModel modelo       = new DefaultTableModel(0,3)	{
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	
	JTable tabla = new JTable(modelo);
	JScrollPane panelScroll = new JScrollPane(tabla);
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 9, "Int");
	JTextField txtDescripcion = new Componentes().text(new JTextField(), "Descripci�n", 100, "String");
	JTextField txtValor = new Componentes().text(new JTextField(), "Valor", 20, "Double");
	
	JCheckBox chStatus = new JCheckBox("Status");
	
	JCheckBox chTodos = new JCheckBox("Todos");
	JCheckBox chLunes = new JCheckBox("Lunes");
	JCheckBox chMartes = new JCheckBox("Martes");
	JCheckBox chMiercoles = new JCheckBox("Miercoles");
	JCheckBox chJueves = new JCheckBox("Jueves");
	JCheckBox chViernes = new JCheckBox("Viernes");
	JCheckBox chSabado = new JCheckBox("Sabado");
	JCheckBox chDomingo = new JCheckBox("Domingo");
	
	
	JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	JButton btnSalir = new JButton("Salir");
	JButton btnDeshacer = new JButton("Deshacer");
	JButton btnGuardar = new JButton("Guardar");
	JButton btnEditar = new JButton("Editar");
	JButton btnNuevo = new JButton("Nuevo");
	
	JDateChooser txtCalendario = new JDateChooser();
	JDateChooser txtCalendario1 = new JDateChooser();
	
	
	public Cat_Ponderacion(){
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/ponderacion_icon&16.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Ponderaciones"));
		
		this.setTitle("Ponderaciones");
		
		int x = 15, y=30, ancho=100;
		
		this.panel.add(new JLabel("Folio:")).setBounds(5,y,ancho,20);
		this.panel.add(txtFolio).setBounds(ancho-20,y,ancho,20);
		this.panel.add(btnBuscar).setBounds(x+ancho+ancho+10,y,32,20);
		
		panel.add(chStatus).setBounds(x+43+(ancho*2),y,70,20);
		
		panel.add(new JLabel("Descripcion:")).setBounds(5,y+=30,ancho,20);
		panel.add(txtDescripcion).setBounds(ancho-20,y,ancho+ancho,20);
		panel.add(btnNuevo).setBounds(x+270,y,ancho,20);
		
		panel.add(new JLabel("Valor:")).setBounds(5,y+=30,ancho,20);
		panel.add(txtValor).setBounds(ancho-20,y,ancho+ancho,20);
		panel.add(btnEditar).setBounds(x+270,y,ancho,20);
		
		panel.add(new JLabel("Fecha:      De")).setBounds(5,y+=30,100,20);
		panel.add(txtCalendario).setBounds(ancho-20,y,ancho-8,20);
		this.txtCalendario.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		
		panel.add(new JLabel("A")).setBounds(ancho+75,y,30,20);
		panel.add(txtCalendario1).setBounds(ancho+87,y,ancho-8,20);
		this.txtCalendario1.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		
		panel.add(new JLabel("Dia: ")).setBounds(5,y+=45,100,20);
		
		panel.add(chTodos).setBounds(85,y,60,20);
		panel.add(chDomingo).setBounds(160,y,80,20);
		panel.add(chLunes).setBounds(240,y,60,20);
		panel.add(chMartes).setBounds(305,y,70,20);
		
		panel.add(chMiercoles).setBounds(85,y+=25,69,20);
		panel.add(chJueves).setBounds(160,y,70,20);
		panel.add(chViernes).setBounds(240,y,69,20);
		panel.add(chSabado).setBounds(305,y,80,20);

		panel.add(btnDeshacer).setBounds(x+ancho+60,y+=60,ancho,20);
		panel.add(btnSalir).setBounds(x-10+40,y,ancho,20);
		panel.add(btnGuardar).setBounds(x+290,y,ancho,20);
		
		panel.add(getPanelTabla()).setBounds(x+ancho+x+40+ancho+ancho+50,20,ancho+210,250);
		
		
		chStatus.setEnabled(false);
		txtDescripcion.setEditable(false);
		txtValor.setEditable(false);
		
		txtFolio.requestFocus();
		txtFolio.addKeyListener(buscar_action);
		
		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(cerrar);
		btnBuscar.addActionListener(buscar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		btnEditar.setEnabled(false);
		chTodos.addActionListener(chxTodos);
		txtFolio.requestFocus();
		cont.add(panel);
		
		agregar(tabla);
		
		this.setSize(760,330);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	private JScrollPane getPanelTabla()	{		

		tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
		tabla.getColumnModel().getColumn(0).setMinWidth(50);
		tabla.getColumnModel().getColumn(0).setMinWidth(50);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Descripcion");
		tabla.getColumnModel().getColumn(1).setMinWidth(160);
		tabla.getColumnModel().getColumn(1).setMaxWidth(160);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Valor");
		tabla.getColumnModel().getColumn(2).setMinWidth(80);
		tabla.getColumnModel().getColumn(2).setMaxWidth(80);
		
		TableCellRenderer render = new TableCellRenderer() { 
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int column) { 
				JLabel lbl = new JLabel(value == null? "": value.toString());
				if(row%2==0){
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(177,177,177));
				} 
				
				if(table.getSelectedRow() == row){
					lbl.setOpaque(true); 
					lbl.setBackground(new java.awt.Color(186,143,73));
				}
				
				switch(column){
					case 0 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
					case 1 : lbl.setHorizontalAlignment(SwingConstants.LEFT); break;
					case 2 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
				}
			return lbl; 
			} 
		}; 
		tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
		tabla.getColumnModel().getColumn(1).setCellRenderer(render); 
		tabla.getColumnModel().getColumn(2).setCellRenderer(render);
		
		Statement s;
		ResultSet rs;
		try {
			s = new Connexion().conexion().createStatement();
			rs = s.executeQuery("select tb_ponderacion.folio as [Folio],"+
					 "  tb_ponderacion.descripcion as [Descripcion], "+
					 "  tb_ponderacion.valor as [Valor] "+
					
					"  from tb_ponderacion");
			
			while (rs.next())
			{ 
				DecimalFormat decimal = new DecimalFormat("#0.00");
				
			   String [] fila = new String[3];
			   fila[0] = rs.getString(1).trim()+"  ";
			   fila[1] = "   "+rs.getString(2).trim();
			   
			  float valor = Float.parseFloat(rs.getString(3));
			   fila[2] = decimal.format(valor)+"  "; 
			   
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		 JScrollPane scrol = new JScrollPane(tabla);
		   
	    return scrol; 
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==2){
	        		int fila = tabla.getSelectedRow();
	        		int id = Integer.parseInt(modelo.getValueAt(fila,0).toString().trim());
	        
						Obj_Ponderacion ponderacion = new Obj_Ponderacion().buscar(id);
						
						txtFolio.setText(id+"");
						txtDescripcion.setText(modelo.getValueAt(fila,1).toString().trim());
						txtValor.setText(modelo.getValueAt(fila,2).toString().trim());
						
						
						if(ponderacion.isDomingo()==true){
							chDomingo.setSelected(true);
						}else{
							chDomingo.setSelected(false);
						}
						
						if(ponderacion.isLunes()==true){
							chLunes.setSelected(true);
						}else{
							chLunes.setSelected(false);
						}
						
						if(ponderacion.isMartes()==true){
							chMartes.setSelected(true);
						}else{
							chMartes.setSelected(false);
						}
						
						if(ponderacion.isMiercoles()==true){
							chMiercoles.setSelected(true);
						}else{
							chDomingo.setSelected(false);
						}
						
						if(ponderacion.isJueves()==true){
							chJueves.setSelected(true);
						}else{
							chJueves.setSelected(false);
						}
						
						if(ponderacion.isViernes()==true){
							chViernes.setSelected(true);
						}else{
							chViernes.setSelected(false);
						}
						
						if(ponderacion.isSabado()==true){
							chSabado.setSelected(true);
						}else{
							chSabado.setSelected(false);
						}
						
						
						chStatus.setSelected(ponderacion.getStatus());
						
						try {
							Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ponderacion.getFechaIn()+"");
							Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(ponderacion.getFechaFin()+"");
							txtCalendario.setDate(date);
							txtCalendario1.setDate(date1);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
											
						
						btnEditar.setEnabled(true);
					
	        	}
	        }
        });
    }
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El folio es requerido \n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}else{			
				Obj_Ponderacion pond = new Obj_Ponderacion().buscar(Integer.parseInt(txtFolio.getText()));
				
				if(pond.getFolio() == Integer.parseInt(txtFolio.getText())){
					if(JOptionPane.showConfirmDialog(null, "El registro ya existe, �desea cambiarlo?") == 0){
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}else{
							int nroFila = tabla.getSelectedRow();
							
							pond.setDescripcion(txtDescripcion.getText());
							pond.setValor(Float.parseFloat(txtValor.getText()+""));
							pond.setStatus(chStatus.isSelected());
							
							pond.setDomingo(chDomingo.isSelected());
							pond.setLunes(chLunes.isSelected());
							pond.setMartes(chMartes.isSelected());
							pond.setMiercoles(chMiercoles.isSelected());
							pond.setJueves(chJueves.isSelected());
							pond.setViernes(chViernes.isSelected());
							pond.setSabado(chSabado.isSelected());
							
							pond.setStatus(chStatus.isSelected());
							
							
							pond.setFechaIn(new SimpleDateFormat("dd/MM/yyyy").format(txtCalendario.getDate()));
							pond.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").format(txtCalendario.getDate()));
							
							pond.actualizar(Integer.parseInt(txtFolio.getText()));
							
							modelo.setValueAt(txtFolio.getText(),nroFila,0);
							modelo.setValueAt(txtDescripcion.getText(),nroFila,1);
							modelo.setValueAt(txtValor.getText(),nroFila,2);
							
							panelLimpiar();
							panelEnabledFalse();
							txtFolio.setEditable(true);
							txtFolio.requestFocus();
						}
						
						JOptionPane.showMessageDialog(null,"El registr� se actualiz� de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
					}else{
						return;
					}
				}else{
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n "+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
					}else{
						pond.setDescripcion(txtDescripcion.getText());
						pond.setValor(Float.parseFloat(txtValor.getText()+""));
						
						
						pond.setFechaIn(new SimpleDateFormat("dd/MM/yyyy").format(txtCalendario.getDate()));
						pond.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").format(txtCalendario.getDate()));
						
						pond.setStatus(chStatus.isSelected());
						
						pond.setDomingo(chDomingo.isSelected());
						pond.setLunes(chLunes.isSelected());
						pond.setMartes(chMartes.isSelected());
						pond.setMiercoles(chMiercoles.isSelected());
						pond.setJueves(chJueves.isSelected());
						pond.setViernes(chViernes.isSelected());
						pond.setSabado(chSabado.isSelected());

						pond.guardar();
						
						Object[] fila = new Object[tabla.getColumnCount()]; 
							
						fila[0]=txtFolio.getText();
						fila[1]=txtDescripcion.getText().toUpperCase();
						fila[2]=txtValor.getText();
						modelo.addRow(fila); 
						
						panelLimpiar();
						panelEnabledFalse();
						txtFolio.setEditable(true);
						txtFolio.requestFocus();
						JOptionPane.showMessageDialog(null,"El registr� se guard� de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
					}
				}
			}			
		}
	};
	
	KeyListener buscar_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e){
		}
		@Override
		public void keyReleased(KeyEvent e) {	
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscar.doClick();
			}
		}
	};
	
	KeyListener numerico_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();

		   if(((caracter < '0') ||
		        (caracter > '9')) &&
		        (caracter != KeyEvent.VK_BACK_SPACE)){
		    	e.consume(); 
		    }			
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
								
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
			Obj_Ponderacion ponderacion = new Obj_Ponderacion().buscar(Integer.parseInt(txtFolio.getText()));
			
			if(ponderacion.getFolio() != 0){
			
			txtFolio.setText(ponderacion.getFolio()+"");
			txtDescripcion.setText(ponderacion.getDescripcion()+"");
			txtValor.setText(ponderacion.getValor()+"");
			
			
			if(ponderacion.isDomingo()==true){
				chDomingo.setSelected(true);
			}else{
				chDomingo.setSelected(false);
			}
			
			if(ponderacion.isLunes()==true){
				chLunes.setSelected(true);
			}else{
				chLunes.setSelected(false);
			}
			
			if(ponderacion.isMartes()==true){
				chMartes.setSelected(true);
			}else{
				chMartes.setSelected(false);
			}
			
			if(ponderacion.isMiercoles()==true){
				chMiercoles.setSelected(true);
			}else{
				chDomingo.setSelected(false);
			}
			
			if(ponderacion.isJueves()==true){
				chJueves.setSelected(true);
			}else{
				chJueves.setSelected(false);
			}
			
			if(ponderacion.isViernes()==true){
				chViernes.setSelected(true);
			}else{
				chViernes.setSelected(false);
			}
			
			if(ponderacion.isSabado()==true){
				chSabado.setSelected(true);
			}else{
				chSabado.setSelected(false);
			}
			
			try {
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(ponderacion.getFechaIn());
				Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(ponderacion.getFechaFin());
				txtCalendario.setDate(date);
				txtCalendario1.setDate(date1);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			if(ponderacion.getStatus() == true){chStatus.setSelected(true);}
			else{chStatus.setSelected(false);}
			
			btnNuevo.setEnabled(false);
			btnEditar.setEnabled(true);
			panelEnabledFalse();
			txtFolio.setEditable(true);
			txtFolio.requestFocus();
			
			}else{
				JOptionPane.showMessageDialog(null, "El Registro no existe","Error",JOptionPane.WARNING_MESSAGE);
				return;
				}
			}
		}
	};
	
	ActionListener cerrar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
		
	};
	
	private String validaCampos(){
		String error="";
		String fechaInicio = txtCalendario.getDate()+"";
		String fechaFin = txtCalendario1.getDate()+"";
		
		if(txtDescripcion.getText().equals("")) error+= "Bono\n";
		if(txtValor.getText().equals(""))		error+= "Abreviatura\n";
		if(fechaInicio.equals("null"))          error+= "Fecha Inicio\n";
		if(fechaFin.equals("null"))			    error+= "Fecha Fin\n";
		return error;
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
		    	String texto = txtValor.getText().toString();
				if (texto.indexOf(".")>-1) e.consume();
				
			}
		    		    		       	
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
								
	};

	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Obj_Ponderacion pond = new Obj_Ponderacion().buscar_nuevo();
			if(pond.getFolio() != 0){
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(pond.getFolio()+1+"");
				txtFolio.setEditable(false);
				txtDescripcion.requestFocus();
			}else{
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(1+"");
				txtFolio.setEditable(false);
				txtDescripcion.requestFocus();
			}
		}
	};
	
	ActionListener chxTodos = new ActionListener() {
		
		public void actionPerformed(ActionEvent arg0) 
		{
			if(chTodos.isSelected())
			{
				chDomingo.setSelected(true);
				chLunes.setSelected(true);
				chMartes.setSelected(true);
				chMiercoles.setSelected(true);
				chJueves.setSelected(true);
				chViernes.setSelected(true);
				chSabado.setSelected(true);
			}else{
				
				chDomingo.setSelected(false);
				chLunes.setSelected(false);
				chMartes.setSelected(false);
				chMiercoles.setSelected(false);
				chJueves.setSelected(false);
				chViernes.setSelected(false);
				chSabado.setSelected(false);
			}
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			panelLimpiar();
			panelEnabledFalse();
			txtFolio.setEditable(true);
			txtFolio.requestFocus();
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(false);
			chStatus.setSelected(false);
			
			chTodos.setSelected(false);
			chDomingo.setSelected(false);
			chLunes.setSelected(false);
			chMartes.setSelected(false);
			chMiercoles.setSelected(false);
			chJueves.setSelected(false);
			chViernes.setSelected(false);
			chSabado.setSelected(false);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelEnabledTrue();
			txtFolio.setEditable(false);
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(true);
		}		
	};
	
	public void panelEnabledFalse(){	
		txtFolio.setEditable(false);
		txtDescripcion.setEditable(false);
		txtValor.setEditable(false);
		chStatus.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
		txtFolio.setEditable(true);
		txtDescripcion.setEditable(true);
		txtValor.setEditable(true);
		chStatus.setEnabled(true);	
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtDescripcion.setText("");
		txtValor.setText("");
		chStatus.setSelected(true);
		txtCalendario.setDate(null);
		txtCalendario1.setDate(null);
	}

}